package Servers;

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

    public static void main(String[] args) {
       try {
        ORB orb = ORB.init(args, null);

        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

        rootpoa.the_POAManager().activate();

        ATWImpl serv1 = new ATWImpl();
        serv1.setOrb(orb);
        
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(serv1);
        Server href = ServerHelper.narrow(ref);

        org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");

        NamingContext ncRef = NamingContextExtHelper.narrow(objref);

        NameComponent path[] = ((NamingContextExtOperations) ncRef).to_name("OUT");
        
        ncRef.rebind(path, href);

        System.out.println(servername + " server is up and running!");

        orb.run();

       } catch (Exception e) {
        e.printStackTrace();
       } 
    }
    
}
