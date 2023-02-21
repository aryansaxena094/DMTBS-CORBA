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
            org.omg.CORBA.Object ATWRef = orbATW.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(ATWRef);
            Server atwaterserv = ServerHelper.narrow(ncRef.resolve_str("ATW"));
            System.out.println("Hello From the system");







          } catch (Exception e) {
            e.printStackTrace();
          }
    }
}
