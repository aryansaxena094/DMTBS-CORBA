package ServerSide;


/**
* ServerSide/ServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Server.idl
* Monday, February 20, 2023 7:38:22 o'clock PM EST
*/

public abstract class ServerPOA extends org.omg.PortableServer.Servant
 implements ServerSide.ServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("verifyMovieID", new java.lang.Integer (0));
    _methods.put ("addMovieSlots", new java.lang.Integer (1));
    _methods.put ("removeMovieSlots", new java.lang.Integer (2));
    _methods.put ("listMovieShows", new java.lang.Integer (3));
    _methods.put ("addadmin", new java.lang.Integer (4));
    _methods.put ("bookMovieTicket", new java.lang.Integer (5));
    _methods.put ("getBookingSchedule", new java.lang.Integer (6));
    _methods.put ("cancelMovieTickets", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ServerSide/Server/verifyMovieID
       {
         String movieID = in.read_string ();
         String $result = null;
         $result = this.verifyMovieID (movieID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // ServerSide/Server/addMovieSlots
       {
         String movieID = in.read_string ();
         String movieName = in.read_string ();
         int bookingcapacity = in.read_long ();
         String $result = null;
         $result = this.addMovieSlots (movieID, movieName, bookingcapacity);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // ServerSide/Server/removeMovieSlots
       {
         String movieID = in.read_string ();
         String movieName = in.read_string ();
         String $result = null;
         $result = this.removeMovieSlots (movieID, movieName);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // ServerSide/Server/listMovieShows
       {
         String movieName = in.read_string ();
         String $result = null;
         $result = this.listMovieShows (movieName);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // ServerSide/Server/addadmin
       {
         String adminID = in.read_string ();
         String $result = null;
         $result = this.addadmin (adminID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // ServerSide/Server/bookMovieTicket
       {
         String CustomerID = in.read_string ();
         String movieID = in.read_string ();
         String movieName = in.read_string ();
         int Numberoftickets = in.read_long ();
         String $result = null;
         $result = this.bookMovieTicket (CustomerID, movieID, movieName, Numberoftickets);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // ServerSide/Server/getBookingSchedule
       {
         String CustomerID = in.read_string ();
         String $result = null;
         $result = this.getBookingSchedule (CustomerID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // ServerSide/Server/cancelMovieTickets
       {
         String CustomerID = in.read_string ();
         String movieID = in.read_string ();
         String movieName = in.read_string ();
         int Numberoftickets = in.read_long ();
         String $result = null;
         $result = this.cancelMovieTickets (CustomerID, movieID, movieName, Numberoftickets);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ServerSide/Server:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Server _this() 
  {
    return ServerHelper.narrow(
    super._this_object());
  }

  public Server _this(org.omg.CORBA.ORB orb) 
  {
    return ServerHelper.narrow(
    super._this_object(orb));
  }


} // class ServerPOA