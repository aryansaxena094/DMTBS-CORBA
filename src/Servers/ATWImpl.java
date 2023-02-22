package Servers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.ORB;

public class ATWImpl extends ServerSide.ServerPOA{
    
    //Required DS
    private static String ServerName = "Atwater";
    private static ArrayList<String> admin = new ArrayList<String>();
    private static HashMap<String, HashMap<String, Integer>> movies = new HashMap<>();
    private static HashMap<String, HashMap<String, Integer>> customer = new HashMap<>();
    private static HashMap<String, ArrayList<String>> foreigncustomer = new HashMap<String, ArrayList<String>>();
    
    //ORB for CORBA
    private ORB orb;
    
    ATWImpl(){
        admin.add("ATWA9499");
    }
    
    //setting orb for this class
    public void setOrb(ORB a){
        orb = a;
    }
    
    //Ports that will be needed for udp
    //ATWATER PORTS
    static int ATW_ALONP = 4003;
    static int ATW_DATA = 4004; 
    
    //OUTREMONT PORTS
    static int OUT_ALONP = 4005;
    static int OUT_DATA = 4006;
    
    //VERDUN PORTS
    static int VER_ALONP = 4007;
    static int VER_DATA = 4008;
    
    
    
    //addadmin
    @Override
    public String verifyMovieID(String movieID) {
        if (movieID.length() != 10) {
            serverlogwriter("VERIFY MOVIE ID", "Invalid movie ID: Must be 10 characters long", false);
            return "Invalid movie ID: Must be 10 characters long";
        }
        
        char session = movieID.charAt(3);
        if (session != 'M' && session != 'A' && session != 'E') {
            serverlogwriter("VERIFY MOVIE ID", "Invalid movie ID: Fourth character must be M (morning), A (afternoon), or E (evening)", false);
            return "Invalid movie ID: Fourth character must be M (morning), A (afternoon), or E (evening)";
        }
        
        int day = Integer.parseInt(movieID.substring(4, 6));
        int month = Integer.parseInt(movieID.substring(6, 8));
        int year = Integer.parseInt("20"+movieID.substring(8,10));
        
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            serverlogwriter("VERIFY MOVIE ID", "Invalid movie ID: Invalid date format", false);
            return "Invalid movie ID: Invalid date format";
        }
        
        String datestring = String.format("%02d%02d%04d", day, month, year);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date date1;
        try {
            date1 = dateFormat.parse(datestring);
            Date currentdate = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentdate);
            calendar.add(Calendar.DATE, 7);
            Date oneWeekFromNow = (Date) calendar.getTime();
            
            if (date1.after(currentdate) && date1.before(oneWeekFromNow)) {
                serverlogwriter("VERIFY MOVIE ID", "Valid movie ID: " + movieID, true);
                return "Valid";
            }
            else
            {
                serverlogwriter("VERIFY MOVIE ID", "You can only access tickets for dates within the next 7 days from today.", false);
                return "You can only access tickets for dates within the next 7 days from today.";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "invalid";
    }
    
    @Override
    public String addMovieSlots(String movieID, String movieName, int bookingcapacity) {
        boolean movieExists = movies.containsKey(movieName);
        if (movieExists) {
            HashMap<String, Integer> movieCapacity = movies.get(movieName);
            boolean movieIDExists = movieCapacity.containsKey(movieID);
            if (movieIDExists) {
                movieCapacity.merge(movieID, bookingcapacity, Integer::sum);
            } else {
                movieCapacity.put(movieID, bookingcapacity);
            }
            movies.put(movieName, movieCapacity);
        } else {
            HashMap<String, Integer> movieCapacity = new HashMap<>();
            movieCapacity.put(movieID, bookingcapacity);
            movies.put(movieName, movieCapacity);
        }
        serverlogwriter("ADDING MOVIE SLOT: ", movieName + " : " + movieID + " with " + bookingcapacity + " capacity", true);
        return movieName+" with MovieID "+ movieID +" has been created with "+bookingcapacity + " capacity";
    }
    
    @Override
    public String removeMovieSlots(String movieID, String movieName) {
        boolean movieexists = movies.containsKey(movieName);
        if(movieexists==true){
            boolean movieIDexists = (movies.get(movieName)).containsKey(movieID);
            if(movieIDexists==true){
                (movies.get(movieName)).remove(movieID);
                serverlogwriter("DELETING MOVIE SLOT:", movieName + " : " + movieID, true);
                return movieName+" with MovieID "+movieID +" has been removed";
            } else {
                serverlogwriter("DELETING MOVIE SLOT:", "Movie ID not found for movie name: " + movieName, false);
                return "Movie ID not found for movie name: " + movieName;
            }
        } else {
            serverlogwriter("DELETING MOVIE SLOT:", "Movie Name not found for movie name: " + movieName, false);
            return "Movie name not found: " + movieName;
        }
    }
    
    @Override
    public String listMovieShows(String movieName) {
        ArrayList<String> listallshows = new ArrayList<String>();
        Map<String, Integer> tempMap = new HashMap<>(movies.getOrDefault(movieName,new HashMap<>()));
        String output = "";
        //for own server
        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            listallshows.add(entry.getKey() + " with " + entry.getValue() + " capacity");
        }
        //from here
        
        String sendingrequest = "L"+"ATW"+movieName;
        byte[] senddata = new byte[1024];
        senddata = sendingrequest.getBytes();
        //checking other servers
        try {
            InetAddress ip = InetAddress.getLocalHost();
            DatagramSocket sendingrequesttoserv1 = new DatagramSocket();
            byte[] receivedataserv1 = new byte[1024];
            DatagramPacket sendreqtorserv1 = new DatagramPacket(senddata, senddata.length, ip, OUT_ALONP);
            sendingrequesttoserv1.send(sendreqtorserv1);
            DatagramSocket gettingdatafromserv1 = new DatagramSocket(ATW_DATA);
            DatagramPacket packetfromserv1 = null;
            String recvdata = "";
            while(recvdata.isEmpty()){
                packetfromserv1 = new DatagramPacket(receivedataserv1, receivedataserv1.length);
                gettingdatafromserv1.receive(packetfromserv1);
                recvdata = new String(packetfromserv1.getData(), 0, packetfromserv1.getLength(), StandardCharsets.UTF_8);
            }
            gettingdatafromserv1.close();
            output = output + " "+ recvdata;
            sendingrequesttoserv1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            InetAddress ip = InetAddress.getLocalHost();
            DatagramSocket sendingrequesttoserv2 = new DatagramSocket();
            byte[] receivedataserv2 = new byte[1024];
            DatagramPacket sendreqtorserv2 = new DatagramPacket(senddata, senddata.length, ip, VER_ALONP);
            sendingrequesttoserv2.send(sendreqtorserv2);
            DatagramSocket gettingdatafromserv2 = new DatagramSocket(ATW_DATA);
            DatagramPacket packetfromserv2 = null;
            String recvdata = "";
            while(recvdata.isEmpty()){
                packetfromserv2 = new DatagramPacket(receivedataserv2, receivedataserv2.length);
                gettingdatafromserv2.receive(packetfromserv2);
                recvdata = new String(packetfromserv2.getData(), 0, packetfromserv2.getLength(), StandardCharsets.UTF_8);
            }
            gettingdatafromserv2.close();
            output = output + " "+ recvdata;
            sendingrequesttoserv2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //till here
        serverlogwriter("LISTING SHOWS:", movieName, true);
        return ServerName + " : " + listallshows.toString() + " " + output;   
    }
    
    @Override
    public String addadmin(String adminID) {
        if(adminID.length() < 8 || adminID.charAt(3)!='A'  && adminID.charAt(3)!='a')
        {
            serverlogwriter("addadmin", adminID, false);
            return "This entered ID is invalid";
        }
        
        if(!adminID.substring(0,3).equalsIgnoreCase("ATW")){
            serverlogwriter("addadmin", adminID, false);
            return "This admin cannot be created in "+ServerName;
        }
        
        
        if(admin.contains(adminID)){
            serverlogwriter("addadmin", adminID, false);
            return "Admin already exists!";
        }
        
        admin.add(adminID);
        serverlogwriter("addadmin", adminID, true);
        return "Admin has been successfully added";}
        
        @Override
        public String bookMovieTicket(String CustomerID, String movieID, String movieName, int Numberoftickets) {
            if(!movies.containsKey(movieName))
            {
                if(!movies.get(movieName).containsKey(movieID)){
                    serverlogwriter("Booking movie ticket:", "Customer:"+CustomerID + " : " + movieName + " : " + movieID, false);
                    return "MovieID does not exist!";
                }
                return "Movie Name does not exist!";
            }
            int totaltickets = (movies.get(movieName)).get(movieID);
            
            if(Numberoftickets>totaltickets){
                serverlogwriter("Booking movie ticket:", "Customer:"+CustomerID + " : " + movieName + " : " + movieID, false);
                return "Lesser slots availabe, you can only book "+totaltickets+" for this show";
            }
            
            if(foreigncustomer.containsKey(CustomerID)){
                ArrayList<String> bookedbyfc = foreigncustomer.get(CustomerID);
                if(bookedbyfc.size()>=3){
                    return "Foreign Customers "+CustomerID+ "are only allowed to book 3 tickets this server: "+ServerName;
                }
                else
                {
                    if(!bookedbyfc.contains(movieName+":"+movieID)){
                        bookedbyfc.add(movieName+movieID);
                    }
                }
            }
            else
            {
                ArrayList<String> toadd = new ArrayList<>();
                toadd.add(movieName+":"+movieID);
                foreigncustomer.put(CustomerID, toadd);
                
            }
            if (!customer.containsKey(CustomerID)) {
                customer.put(CustomerID, new HashMap<>());
            }
            
            totaltickets = totaltickets - Numberoftickets;
            HashMap<String, Integer> moviesbookedbycustomer = customer.get(CustomerID);
            moviesbookedbycustomer.put(movieName+":"+movieID, moviesbookedbycustomer.getOrDefault(movieID, 0) + Numberoftickets);
            (movies.get(movieName)).put(movieID, totaltickets);
            serverlogwriter("Booking movie ticket:", "Customer:"+CustomerID + " : " + movieName + " : " + movieID, true);
            return Numberoftickets+ " Tickets have been booked to "+movieID+" show of "+movieName;
        }
        
        @Override
        public String getBookingSchedule(String CustomerID) {
            if(!customer.containsKey(CustomerID))
            {
                serverlogwriter("GETTING BOOKING SCHEDULE:", "No shows booked in "+ServerName, false);
                return "No shows booked in "+ServerName;
            }
            HashMap<String, Integer> customerBookings = customer.get(CustomerID);
            ArrayList<String> allbookedshows = new ArrayList<>();
            for (Map.Entry<String, Integer> booking : customerBookings.entrySet()) {
                allbookedshows.add(booking.getValue() + " tickets for " + booking.getKey());
            }
            serverlogwriter("GETTING BOOKING SCHEDULE FOR:", CustomerID, true);
            return allbookedshows.toString();
        }
        
        @Override
        public String cancelMovieTickets(String CustomerID, String movieID, String movieName, int Numberoftickets) {
            if (!customer.containsKey(CustomerID)) {
                serverlogwriter("CANCEL MOVIE TICKET", "Customer ID not found "+ CustomerID,false);
                return "Customer ID not found.";
            }
            
            HashMap<String, Integer> customerBookings = customer.get(CustomerID);
            
            if (!customerBookings.containsKey(movieName+":"+movieID)) {
                serverlogwriter("CANCEL MOVIE TICKET", "No tickets found for the movie with ID " + movieID,false);
                return "No tickets found for the movie with ID " + movieID;
            }
            
            if (customerBookings.get(movieName+":"+movieID) < Numberoftickets) {
                serverlogwriter("CANCEL MOVIE TICKET", "Only " + customerBookings.get(movieID) + " tickets found for the movie with ID " + movieID,false);
                return "Only " + customerBookings.get(movieID) + " tickets found for the movie with ID " + movieID;
            }
            
            if (!movies.containsKey(movieName)) {
                serverlogwriter("CANCEL MOVIE TICKET", "Movie with name " + movieName + " not found.",false);
                return "Movie with name " + movieName + " not found.";
            }
            
            HashMap<String, Integer> movieBookings = movies.get(movieName);
            if (!movieBookings.containsKey(movieID)) {
                serverlogwriter("CANCEL MOVIE TICKET", "Movie with ID " + movieID + " not found.",false);
                return "Movie with ID " + movieID + " not found.";
            }
            
            customerBookings.put(movieName+":"+movieID, customerBookings.get(movieName+":"+movieID) - Numberoftickets);
            movieBookings.put(movieID, movieBookings.get(movieID) + Numberoftickets);
            
            serverlogwriter("INFO", Numberoftickets + " tickets has been cancelled for " + movieName + " with MovieID " + movieID,true);
            return Numberoftickets + " tickets has been cancelled for " + movieName + " with MovieID " + movieID;
        }
        
        @Override
        public void serverlogwriter(String requesttype, String ID, boolean status){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
            Date date = new Date();
            String dateStr = dateFormat.format(date);
            String timeStr = timeFormat.format(date);
            String logFilePath = "/Users/aryansaxena/Desktop/DSD/DSDA2/CORBADMTBS/logs/Atwater/ATW.txt";
            File logFile = new File(logFilePath);
            String logMessage = "DATE: "+ dateStr + " | " + "TIME: " + timeStr + " | " + "REQUEST TYPE: " + requesttype + " | " + ID + " | " + "STATUS: "+(status ? "success" : "failure");
            try {
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(logFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(
                "_________________________________________________________________________________________"
                +"\n"+
                logMessage
                +"\n"+
                "_________________________________________________________________________________________");
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Error writing to log file: " + e.getMessage());
            }
        }
        
        @Override
        public String verifyID(String ID) {
            if(ID.length() < 8 || ID.charAt(3)!='A' &&  ID.charAt(3)!='C' && ID.charAt(3)!='a' && ID.charAt(3)!='c')
            {
                serverlogwriter("VERIFY ID", "Invalid ID "+ ID, false);
                return "This entered ID is invalid";
            }
            
            if(ID.charAt(3)=='A' || ID.charAt(3)=='a'){
                //admincheck
                if(admin.contains(ID)){
                    //checking if admin exists in db
                    serverlogwriter("VERIFY ID", "Welcome Admin with ID "+ ID, true);
                    return "Welcome Admin!";
                }
                else {
                    serverlogwriter("VERIFY ID", "Admin with ID "+ ID +" not found", false);
                    return "This entered ID is invalid";
                }
            }
            
            if(ID.charAt(3)=='C' || ID.charAt(3)=='c'){
                //admincheck
                serverlogwriter("VERIFY ID", "Welcome Customer with ID "+ ID, true);
                return "Welcome Customer!";
            }
            serverlogwriter("VERIFY ID", "Invalid ID "+ ID, false);
            return "This entered ID is invalid";
        }
        
        public void listMovieServertoServer (String movieName, String serverrequest){
            String output = "";
            ArrayList<String> listallshows = new ArrayList<String>();
            Map<String, Integer> tempMap = new HashMap<>(movies.getOrDefault(movieName,new HashMap<>()));
            if(!tempMap.isEmpty())
            {
                for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
                    listallshows.add(entry.getKey() + " with " + entry.getValue() + " capacity");
                }
            }
            output =  ServerName +" : "+ listallshows.toString();
            boolean sendPacketStatus = false;
            try {
                byte[] senddata = new byte[1024];
                senddata = output.getBytes();
                InetAddress ip = InetAddress.getLocalHost();
                DatagramSocket toserv = new DatagramSocket();
                if(serverrequest.equalsIgnoreCase("OUT"))
                {
                    DatagramPacket sendpacket = new DatagramPacket(senddata, senddata.length, ip, OUT_DATA);
                    toserv.send(sendpacket);
                    sendPacketStatus = true;
                }
                else if(serverrequest.equalsIgnoreCase("VER"))
                {
                    DatagramPacket sendpacket = new DatagramPacket(senddata, senddata.length, ip, VER_DATA);
                    toserv.send(sendpacket);
                    sendPacketStatus = true;
                }
                toserv.close();
            } catch (Exception e) {
                sendPacketStatus = false;
            }
            finally{
                serverlogwriter("SEND PACKET SERVER TO SERVER(UDP)", output, sendPacketStatus);
            }
        }
        
        
    }
    