package Servers;

import org.omg.CORBA.ORB;

import ServerSide.ServerPOA;

public class ATWImpl extends ServerPOA{

    private ORB orb;
     
    public void setOrb(ORB a){
        orb = a;
    }

    @Override
    public String verifyMovieID(String movieID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyMovieID'");
    }

    @Override
    public String addMovieSlots(String movieID, String movieName, int bookingcapacity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMovieSlots'");
    }

    @Override
    public String removeMovieSlots(String movieID, String movieName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeMovieSlots'");
    }

    @Override
    public String listMovieShows(String movieName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listMovieShows'");
    }

    @Override
    public String addadmin(String adminID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addadmin'");
    }

    @Override
    public String bookMovieTicket(String CustomerID, String movieID, String movieName, int Numberoftickets) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bookMovieTicket'");
    }

    @Override
    public String getBookingSchedule(String CustomerID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingSchedule'");
    }

    @Override
    public String cancelMovieTickets(String CustomerID, String movieID, String movieName, int Numberoftickets) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelMovieTickets'");
    }
    
}