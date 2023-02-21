package Servers;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ServerSide.Server;
import ServerSide.ServerHelper;

public class Calserv {
    public static void main(String[] args) {
       try {
        ORB orb = ORB.init(args, null);

        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

        rootpoa.the_POAManager().activate();

        Atwater serv1 = new Atwater();
        serv1.setOrb(orb);
        
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(serv1);
        Server href = ServerHelper.narrow(ref);

        org.omg.CORBA.Object objref = orb.resolve_initial_references("Naming Service");
        













       } catch (Exception e) {
        // TODO: handle exception
       } 
    }
    
}
