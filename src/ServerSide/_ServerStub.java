package ServerSide;


/**
* ServerSide/_ServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Server.idl
* Tuesday, February 21, 2023 3:59:04 o'clock PM EST
*/

public class _ServerStub extends org.omg.CORBA.portable.ObjectImpl implements ServerSide.Server
{

  public String verifyID (String ID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("verifyID", true);
                $out.write_string (ID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return verifyID (ID        );
            } finally {
                _releaseReply ($in);
            }
  } // verifyID

  public String verifyMovieID (String movieID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("verifyMovieID", true);
                $out.write_string (movieID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return verifyMovieID (movieID        );
            } finally {
                _releaseReply ($in);
            }
  } // verifyMovieID

  public String addMovieSlots (String movieID, String movieName, int bookingcapacity)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addMovieSlots", true);
                $out.write_string (movieID);
                $out.write_string (movieName);
                $out.write_long (bookingcapacity);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addMovieSlots (movieID, movieName, bookingcapacity        );
            } finally {
                _releaseReply ($in);
            }
  } // addMovieSlots

  public String removeMovieSlots (String movieID, String movieName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeMovieSlots", true);
                $out.write_string (movieID);
                $out.write_string (movieName);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return removeMovieSlots (movieID, movieName        );
            } finally {
                _releaseReply ($in);
            }
  } // removeMovieSlots

  public String listMovieShows (String movieName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("listMovieShows", true);
                $out.write_string (movieName);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return listMovieShows (movieName        );
            } finally {
                _releaseReply ($in);
            }
  } // listMovieShows

  public String addadmin (String adminID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addadmin", true);
                $out.write_string (adminID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addadmin (adminID        );
            } finally {
                _releaseReply ($in);
            }
  } // addadmin

  public String bookMovieTicket (String CustomerID, String movieID, String movieName, int Numberoftickets)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("bookMovieTicket", true);
                $out.write_string (CustomerID);
                $out.write_string (movieID);
                $out.write_string (movieName);
                $out.write_long (Numberoftickets);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return bookMovieTicket (CustomerID, movieID, movieName, Numberoftickets        );
            } finally {
                _releaseReply ($in);
            }
  } // bookMovieTicket

  public String getBookingSchedule (String CustomerID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getBookingSchedule", true);
                $out.write_string (CustomerID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getBookingSchedule (CustomerID        );
            } finally {
                _releaseReply ($in);
            }
  } // getBookingSchedule

  public String cancelMovieTickets (String CustomerID, String movieID, String movieName, int Numberoftickets)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("cancelMovieTickets", true);
                $out.write_string (CustomerID);
                $out.write_string (movieID);
                $out.write_string (movieName);
                $out.write_long (Numberoftickets);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return cancelMovieTickets (CustomerID, movieID, movieName, Numberoftickets        );
            } finally {
                _releaseReply ($in);
            }
  } // cancelMovieTickets

  public void serverlogwriter (String requesttype, String ID, boolean status)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("serverlogwriter", true);
                $out.write_string (requesttype);
                $out.write_string (ID);
                $out.write_boolean (status);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                serverlogwriter (requesttype, ID, status        );
            } finally {
                _releaseReply ($in);
            }
  } // serverlogwriter

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ServerSide/Server:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ServerStub
