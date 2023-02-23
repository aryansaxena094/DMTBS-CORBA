package Client;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import ServerSide.Server;
import ServerSide.ServerHelper;

public class Client {
  public static void intro()
  {
    System.out.println("***********************************************************************************************************");
    System.out.println("***********************************************************************************************************");
    System.out.println("******************************** Distributed Movie Ticketing Service(DMTS) ********************************");
    System.out.println("***********************************************************************************************************");
    System.out.println("***********************************************************************************************************");
    
  }
  public static void conclusion(){
    System.out.println("*******************************************************************************************************************");
    System.out.println("*******************************************************************************************************************");
    System.out.println("******************************************** Thank you for using DMTS! ********************************************");
    System.out.println("*******************************************************************************************************************");
    System.out.println("*******************************************************************************************************************");
  }
  public static void adminmenu(String ID){
    System.out.println("\r\nHey Admin: "+ID);
    System.out.println("Please select your actions: ");
    System.out.println("1. Add Movie Slots");
    System.out.println("2. Remove Mobie Slots");
    System.out.println("3. List Movie Shows Availability");
    System.out.println("4. Add Another Admin");
    System.out.println("5. Use Different ID");
    System.out.println("6. EXIT \r\n");
  }
  public static void customermenu(String ID){
    System.out.println("\r\nHey Customer: "+ID);
    System.out.println("1. Book Movie Ticket");
    System.out.println("2. Get Booking Schedule");
    System.out.println("3. Cancel Movie Tickets");
    System.out.println("4. Use Different ID");
    System.out.println("5. Exchange Movie Ticket");
    System.out.println("6. EXIT \r\n");
  }
  
  public static void main(String[] args) {
    intro();
    Scanner sc = new Scanner(System.in);
    String ID = "";
    
    try {
      ORB orb = ORB.init(args, null);
      
      org.omg.CORBA.Object ATWref = orb.resolve_initial_references("NameService");
      NamingContextExt ref1 = NamingContextExtHelper.narrow(ATWref);
      Server servATW = ServerHelper.narrow(ref1.resolve_str("ATW"));
      
      org.omg.CORBA.Object VERref = orb.resolve_initial_references("NameService");
      NamingContextExt ref2 = NamingContextExtHelper.narrow(VERref);
      Server servVER = ServerHelper.narrow(ref2.resolve_str("VER"));
      
      org.omg.CORBA.Object OUTref = orb.resolve_initial_references("NameService");
      NamingContextExt ref3 = NamingContextExtHelper.narrow(OUTref);
      Server servOUT = ServerHelper.narrow(ref3.resolve_str("OUT"));
      
      System.out.println("Hey Client?!");
      
      //lessgo
      program:while(true){
        String verification = "This entered ID is invalid";
        
        while(verification.equalsIgnoreCase("This entered ID is invalid")){
          System.out.println("Please Enter your ID: ");
          ID = sc.nextLine();
          
          if(ID.length() > 3)
          {
            if(ID.substring(0,3).equalsIgnoreCase("ATW"))
            { 
              //verification for ATWATER
              verification = servATW.verifyID(ID);
            }
            else if(ID.substring(0,3).equalsIgnoreCase("VER"))
            {
              //verification for VERDUN
              verification = servVER.verifyID(ID);
            }
            else if(ID.substring(0,3).equalsIgnoreCase("OUT")) 
            { 
              //verification for OUTREMONT
              verification = servOUT.verifyID(ID);
            }
          }
          else
          {
            clientlogwrtier("ID Verification", ID, false);
            verification = "invalid";
          }
          System.out.println(verification);
        }
        
        clientlogwrtier("ID Verification", ID, true);
        
        user:while(true){
          
          //variables (most used)
          String movieID = "";
          String movieName = "";
          
          if(verification.equalsIgnoreCase("Welcome Admin!")){
            adminmenu(ID);
            int menuinp = Integer.parseInt(sc.nextLine());
            //admin menu
            switch(menuinp){
              case 1:{
                //addingtotheserver
                //general variables
                String whichadmin = ID.substring(0,3);
                int bookingcapacity = 0;
                
                while(true)
                {
                  
                  String movieidverification = "";
                  while(!movieidverification.equalsIgnoreCase("Valid")){
                    System.out.println(movieidverification);
                    System.out.println("MovieID: ");
                    movieID = sc.nextLine();
                    if(whichadmin.equalsIgnoreCase("ATW")){
                      movieidverification = servATW.verifyMovieID(movieID);
                    }
                    else if(whichadmin.equalsIgnoreCase("VER")){
                      movieidverification = servVER.verifyMovieID(movieID);
                    }
                    else if(whichadmin.equalsIgnoreCase("OUT")){
                      movieidverification = servOUT.verifyMovieID(movieID);
                    }
                  }
                  
                  if(movieID.substring(0,3).equals(whichadmin)){
                    break;
                  }
                  System.out.println("You are not allowed to access this server!");
                }
                clientlogwrtier("MovieID verification", movieID, true);
                
                System.out.println("Enter Name of the Movie: ");
                movieName = sc.nextLine();
                
                System.out.println("Enter the Capacity you wish to add: ");
                bookingcapacity = Integer.parseInt(sc.nextLine());
                
                //accessing RMI
                
                if(whichadmin.equalsIgnoreCase("ATW"))
                {
                  //adding to atwater
                  System.out.println("\n\r"+servATW.addMovieSlots(movieID,movieName,bookingcapacity));
                  clientlogwrtier("Slot Creation: accessing ATW", movieName+":"+movieID+" with "+bookingcapacity, true);
                }
                else if(whichadmin.equalsIgnoreCase("VER"))
                {
                  System.out.println("\n\r"+servVER.addMovieSlots(movieID,movieName,bookingcapacity));
                  clientlogwrtier("Slot Creation: accessing VER", movieName+":"+movieID+" with "+bookingcapacity, true);
                  //adding to verdun
                }
                else if(whichadmin.equalsIgnoreCase("OUT")){
                  System.out.println("\n\r"+servOUT.addMovieSlots(movieID,movieName,bookingcapacity));
                  clientlogwrtier("Slot Creation: accessing OUT", movieName+":"+movieID+" with "+bookingcapacity, true);
                  //adding to outremont
                }
              }
              break;
              
              case 2:{
                //deleting from the server
                //general variables
                String whichadmin = ID.substring(0,3);
                
                while(true)
                {   
                  String movieidverification = "";
                  while(!movieidverification.equalsIgnoreCase("Valid")){
                    System.out.println(movieidverification);
                    System.out.println("MovieID: ");
                    movieID = sc.nextLine();
                    if(whichadmin.equalsIgnoreCase("ATW")){
                      movieidverification = servATW.verifyMovieID(movieID);
                    }
                    else if(whichadmin.equalsIgnoreCase("VER")){
                      movieidverification = servVER.verifyMovieID(movieID);
                    }
                    else if(whichadmin.equalsIgnoreCase("OUT")){
                      movieidverification = servOUT.verifyMovieID(movieID);
                    }
                  }
                  
                  if(movieID.substring(0,3).equals(whichadmin)){
                    break;
                  }
                  System.out.println("You are not allowed to access this server!");
                }
                
                clientlogwrtier("MovieID verification", movieID, true);
                
                System.out.println("Enter Name of the Movie: ");
                movieName = sc.nextLine();
                
                if(whichadmin.equalsIgnoreCase("ATW")){
                  clientlogwrtier("Slot Deletion: accessing ATW", movieName+":"+movieID, true);
                  System.out.println("\n\r"+servATW.removeMovieSlots(movieID,movieName));
                }
                else if(whichadmin.equalsIgnoreCase("VER")){
                  clientlogwrtier("Slot Deletion: accessing VER", movieName+":"+movieID, true);
                  System.out.println("\n\r"+servVER.removeMovieSlots(movieID,movieName));
                  //removing from verdun
                }
                else if(whichadmin.equalsIgnoreCase("OUT")){
                  clientlogwrtier("Slot Deletion: accessing OUT", movieName+":"+movieID, true);
                  System.out.println("\n\r"+servOUT.removeMovieSlots(movieID,movieName));
                  //removing from outremont
                }
              }
              break;
              
              case 3:{
                //identifying admin
                String whichadmin = ID.substring(0,3);
                
                
                System.out.println("Enter the Movie Name of which you wish to see the shows for: ");
                movieName = sc.nextLine();
                
                if(whichadmin.equalsIgnoreCase("ATW")){
                  System.out.println("\n\r"+servATW.listMovieShows(movieName));
                  clientlogwrtier("Accessing movie slots: ATW", movieName, true);
                  //getting from atwater
                }
                else if(whichadmin.equalsIgnoreCase("VER")){
                  System.out.println("\n\r"+servVER.listMovieShows(movieName));
                  clientlogwrtier("Accessing movie slots: VER", movieName, true);
                  //getting from verdun
                }
                else if(whichadmin.equalsIgnoreCase("OUT")){
                  System.out.println("\n\r"+servOUT.listMovieShows(movieName));
                  clientlogwrtier("Accessing movie slots: OUT", movieName, true);
                  //getting from outremont
                }
              }
              break;
              
              case 4:{
                
                String whichadmin = ID.substring(0,3);
                
                System.out.println("Enter the Admin ID you wish to your server: "+whichadmin);
                String admintobeadded = sc.nextLine();
                
                //adding admin to the respective servers
                if(whichadmin.equalsIgnoreCase("ATW")){
                  
                  System.out.println("\n\r"+servATW.addadmin(admintobeadded));
                  clientlogwrtier("Adding Admin: ATW", admintobeadded, true);
                  //getting from atwater
                }
                else if(whichadmin.equalsIgnoreCase("VER")){
                  System.out.println("\n\r"+servVER.addadmin(admintobeadded));
                  clientlogwrtier("Adding Admin: VER", admintobeadded, true);
                  //getting from verdun
                }
                else if(whichadmin.equalsIgnoreCase("OUT")){
                  System.out.println("\n\r"+servOUT.addadmin(admintobeadded));
                  clientlogwrtier("Adding Admin: OUT", admintobeadded, true);
                  //getting from outremont
                }
                
              }
              break;
              
              case 5:{
                clientlogwrtier("Switching ID","from: "+ID, true);
                break user;
              }
              
              case 6:{
                clientlogwrtier("Terminate","ID: "+ID, true);
                break program;
              }
              default: break;
            }
          }
          
          else if(verification.equalsIgnoreCase("Welcome Customer!"))
          {//customer menu
            
            customermenu(ID);
            int menuinp = Integer.parseInt(sc.nextLine());
            // String whichcustomer = ID.substring(0,3);
            
            switch(menuinp){
              case 1:{
                int ticketstobebooked = 0;
                
                
                System.out.println("Name of the Movie: ");
                movieName = sc.nextLine();
                
                // System.out.println("NOTE: YOU CAN ONLY BOOK 3 TICKETS IN SERVERS OTHER THAN YOUR OWN: "+whichcustomer);
                
                //Getting Movie ID
                String movieidverification = "";
                while(!movieidverification.equalsIgnoreCase("Valid")){
                  System.out.println(movieidverification);
                  System.out.println("MovieID: ");
                  movieID = sc.nextLine();
                  if(movieID.substring(0, 3).equalsIgnoreCase("ATW")){
                    movieidverification = servATW.verifyMovieID(movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("VER")){
                    movieidverification = servVER.verifyMovieID(movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("OUT")){
                    movieidverification = servOUT.verifyMovieID(movieID);
                  }
                }
                
                clientlogwrtier("MovieID verification", movieID, true);
                
                
                String whichservermovie = movieID.substring(0,3);
                
                System.out.println("Enter the nubmer of tickets to be booked: ");
                ticketstobebooked = Integer.parseInt(sc.nextLine());
                
                if(whichservermovie.equalsIgnoreCase("ATW")){
                  
                  clientlogwrtier("Slot Booking: accessing ATW", "CustomerID: "+ID+" : "+ movieName+":"+movieID+" with "+ticketstobebooked, true);
                  
                  System.out.println(servATW.bookMovieTicket(ID,movieID,movieName,ticketstobebooked));
                }
                else if(whichservermovie.equalsIgnoreCase("VER")){
                  
                  clientlogwrtier("Slot Booking: accessing VER", "CustomerID: "+ID+" : "+ movieName+":"+movieID+" with "+ticketstobebooked, true);
                  
                  System.out.println(servVER.bookMovieTicket(ID,movieID,movieName,ticketstobebooked));
                }
                else if(whichservermovie.equalsIgnoreCase("OUT")){
                  
                  clientlogwrtier("Slot Booking: accessing OUT", "CustomerID: "+ID+" : "+ movieName+":"+movieID+" with "+ticketstobebooked, true);
                  
                  System.out.println(servOUT.bookMovieTicket(ID,movieID,movieName,ticketstobebooked));
                }
              }
              break;
              case 2:{
                
                System.out.println("ATWATER: "+servATW.getBookingSchedule(ID));
                
                clientlogwrtier("Fetching booking schedule: ATW", "CustomerID: "+ID, true);
                
                System.out.println("VERDUN: "+servVER.getBookingSchedule(ID));
                
                clientlogwrtier("Fetching booking schedule: VER", "CustomerID: "+ID, true);
                
                System.out.println("OUTREMONT: "+servOUT.getBookingSchedule(ID));
                
                clientlogwrtier("Fetching booking schedule: OUT", "CustomerID: "+ID, true);
              }
              break;
              case 3:{
                //CANCELLING THE MOVIE TICKET
                int Numberoftickets = 0;
                
                System.out.println("If you wish to cancel a movie ticket, please enter - ");
                
                System.out.println("Name of the Movie booked");
                movieName = sc.nextLine();
                
                String movieidverification = "";
                
                while(!movieidverification.equalsIgnoreCase("Valid")){
                  System.out.println(movieidverification);
                  System.out.println("MovieID: ");
                  movieID = sc.nextLine();
                  if(movieID.substring(0, 3).equalsIgnoreCase("ATW")){
                    movieidverification = servATW.verifyMovieID(movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("VER")){
                    movieidverification = servVER.verifyMovieID(movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("OUT")){
                    movieidverification = servOUT.verifyMovieID(movieID);
                  }
                }
                
                clientlogwrtier("MovieID verification", movieID, true);
                
                System.out.println("Number of tickets you wish to cancel");
                Numberoftickets = Integer.parseInt(sc.nextLine());
                
                if(movieID.substring(0, 3).equalsIgnoreCase("ATW")){
                  System.out.println(servATW.cancelMovieTickets(ID, movieID, movieName, Numberoftickets));
                  
                  clientlogwrtier("Accessing ATW: Ticket cancellation", "CustomerID: "+ID+" : "+movieName+":"+movieID+":"+Numberoftickets, true);
                  
                }
                else if(movieID.substring(0, 3).equalsIgnoreCase("VER")){
                  System.out.println(servVER.cancelMovieTickets(ID, movieID, movieName, Numberoftickets));    
                  
                  clientlogwrtier("Accessing VER: Ticket cancellation", "CustomerID: "+ID+" : "+movieName+":"+movieID+":"+Numberoftickets, true);
                }
                else if(movieID.substring(0, 3).equalsIgnoreCase("OUT")){
                  System.out.println(servOUT.cancelMovieTickets(ID, movieID, movieName, Numberoftickets));    
                  
                  clientlogwrtier("Accessing OUT: Ticket cancellation", "CustomerID: "+ID+" : "+movieName+":"+movieID+":"+Numberoftickets, true);
                }
                
              }
              break;
              case 4:{
                clientlogwrtier("Switching ID","from: "+ID, true);
                break user;
              }
              case 5: {
                //Exchanging Ticket
                //Needed: String customerID, String movieID, String movieName , String new_movieID, String new_movieName, int numberOfTickets
                movieName = "";
                movieID = "";
                String new_movieID = "";
                String new_movieName = "";
                int numberOfTickets = 0;
                //Taking INPUT from the user
                
                //Name of the movie
                System.out.println("Name of the Movie booked");
                movieName = sc.nextLine();
                String movieidverification = "";
                //movieID
                while(!movieidverification.equalsIgnoreCase("Valid")){
                  System.out.println(movieidverification);
                  System.out.println("MovieID: ");
                  movieID = sc.nextLine();
                  if(movieID.substring(0, 3).equalsIgnoreCase("ATW")){
                    movieidverification = servATW.verifyMovieID(movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("VER")){
                    movieidverification = servVER.verifyMovieID(movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("OUT")){
                    movieidverification = servOUT.verifyMovieID(movieID);
                  }
                }
                clientlogwrtier("MovieID verification", movieID, true);
                
                //Getting the new movie name
                System.out.println("Enter the name of the movie you wish to exchange your ticket with: ");
                new_movieName = sc.nextLine();
                

                movieidverification = "";
                //New Movie ID
                while(!movieidverification.equalsIgnoreCase("Valid")){
                  System.out.println(movieidverification);
                  System.out.println("Enter the new movieID you wish to exchange your tickets with: ");
                  new_movieID = sc.nextLine();
                  if(movieID.substring(0, 3).equalsIgnoreCase("ATW")){
                    movieidverification = servATW.verifyMovieID(new_movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("VER")){
                    movieidverification = servVER.verifyMovieID(new_movieID);
                  }
                  else if(movieID.substring(0, 3).equalsIgnoreCase("OUT")){
                    movieidverification = servOUT.verifyMovieID(new_movieID);
                  }
                }

                //getting the number of tickets
                System.out.println("Enter the number of tickets you wish to book: ");
                numberOfTickets = Integer.parseInt(sc.nextLine());
                
                //sending request
                if(movieID.substring(0, 3).equalsIgnoreCase("ATW")){
                  System.out.println(servATW.exchangeTickets(ID, movieID, movieName, new_movieID, new_movieName, numberOfTickets));
                  clientlogwrtier("Accessing ATW: Ticket exchange", "CustomerID: "+ID+" : "+movieName+":"+movieID+": EXCHANGING : "+new_movieName+":"+new_movieID+":"+numberOfTickets, true);
                }
                else if(movieID.substring(0, 3).equalsIgnoreCase("VER")){
                  System.out.println(servVER.exchangeTickets(ID, movieID, movieName, new_movieID, new_movieName, numberOfTickets));    
                  clientlogwrtier("Accessing VER: Ticket exchange", "CustomerID: "+ID+" : "+movieName+":"+movieID+": EXCHANGING : "+new_movieName+":"+new_movieID+":"+numberOfTickets, true);
                }
                else if(movieID.substring(0, 3).equalsIgnoreCase("OUT")){
                  System.out.println(servOUT.exchangeTickets(ID, movieID, movieName, new_movieID, new_movieName, numberOfTickets));    
                  clientlogwrtier("Accessing OUT: Ticket exchange", "CustomerID: "+ID+" : "+movieName+":"+movieID+": EXCHANGING : "+new_movieName+":"+new_movieID+":"+numberOfTickets, true);
                }
              }
              break;
              case 6: {
                clientlogwrtier("Terminate","ID: "+ID, true);
                break program;
              }
              default:
            }
          }       
        }   
      }
      //end code
      //till here
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally{
      conclusion();
      sc.close();
    } 
  }

  public static void clientlogwrtier(String requesttype, String ID, boolean status){
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
    Date date = new Date();
    String dateStr = dateFormat.format(date);
    String timeStr = timeFormat.format(date);
    String logFilePath = "/Users/aryansaxena/Desktop/DSD/DSDA2/CORBADMTBS/logs/ClientSide"+dateStr+".txt";
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
  
}
