package Servers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextExtOperations;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ServerSide.Server;
import ServerSide.ServerHelper;

public class OUTserv {
    public static String servername = "Outremont";
    
    //ATWATER PORTS
    static int ATW_ALONP = 4003;
    static int ATW_DATA = 4004;
    
    //OUTREMONT PORTS
    static int OUT_ALONP = 4005;
    static int OUT_DATA = 4006;
    
    //VERDUN PORTS
    static int VER_ALONP = 4007;
    static int VER_DATA = 4008;
    
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            
            rootpoa.the_POAManager().activate();
            
            OUTImpl serv2 = new OUTImpl();
            serv2.setOrb(orb);
            
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(serv2);
            Server href = ServerHelper.narrow(ref);
            
            org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
            
            NamingContext ncRef = NamingContextExtHelper.narrow(objref);
            
            NameComponent path[] = ((NamingContextExtOperations) ncRef).to_name("OUT");
            
            ncRef.rebind(path, href);
            
            System.out.println(servername + " server is up and running!");
            
            new Thread(() -> {orb.run();}).start();
            
            try (//UDP Server OPEN PORT ALWAYS OPEN
            DatagramSocket Socket = new DatagramSocket(OUT_ALONP)) 
            {
                byte[] receivedata = new byte[1024];
                //Keeping UDP Port Open for Recieving
                while(true){
                    DatagramPacket receivePacket = new DatagramPacket(receivedata, receivedata.length);
                    Socket.receive(receivePacket);
                    String received = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                    //sample recieved: LATWAVATAR
                    if(received.charAt(0)=='L' || received.charAt(0)=='l'){
                        String curmoviename = received.substring(4);
                        String requestfromserver = received.substring(1,4);
                        serv2.listMovieServertoServer(curmoviename,requestfromserver);
                    }
                    if(received.charAt(0)=='B' || received.charAt(0)=='b'){
                        serv2.bookingservertoserver(received);
                    }
                }
            } 

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
}
