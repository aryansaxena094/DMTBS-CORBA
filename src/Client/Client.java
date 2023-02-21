package Client;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import ServerSide.Server;
import ServerSide.ServerHelper;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
          try {
            ORB orbATW = ORB.init(args, null);
            org.omg.CORBA.Object ref = orbATW.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(ref);
            Server atwaterserv = ServerHelper.narrow(ncRef.resolve_str("ATW"));
            Server verdunserv = ServerHelper.narrow(ncRef.resolve_str("VER"));
            Server outserv = ServerHelper.narrow(ncRef.resolve_str("OUT"));

            System.out.println("Hey Client?!");




          } catch (Exception e) {
            e.printStackTrace();
          }
    }
}
