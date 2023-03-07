# DMTBS-CORBA
## Aryan Saxena
### Distributed Systems and Design

The system functions with different servers as different theaters; namely: Atwater, Verdun, and Outremont. 
It implements unique admin and customer IDs for identification. 
It maintains logs on both client side and the server side. 
It logs all the function calls from the customer or the admin, and what functions took place in the server and if it succeeded or not. 
Administrators have been given the ability to add, remove and list the movie shows also from other servers using UDP/IP. 
Customers have been given the ability to book, cancel and view the show for which tickets they have booked for. 
System additionally also limits the customer from booking three movies from other servers than their own local server (based on the prefix). 
This system successfully provides an outline of an implementation of a movie ticket management system.

