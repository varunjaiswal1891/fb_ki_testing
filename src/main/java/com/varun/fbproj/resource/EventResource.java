package com.varun.fbproj.resource;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.varun.fbproj.model.Event;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.EventService;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.RetriveService;
@WebService()
@Path("/event")
public class EventResource {

	@PUT
	@Path("/create_event") // event is created 
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	public String createEvent(@CookieParam("ID") String jwt,Event eobj) throws    UnsupportedEncodingException{
	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			  String myEmailID=claims.getSubject();
			  // retrive userid from user mailid
			 
		eobj.setuID(RetriveService.uidfromEmailID(myEmailID));
		
		String s="null";
	
		if(eobj.getuID()>0)
		s=EventService.createNewEvent(eobj);
		return s;
	}
	// get all events created by you
	@GET
	@Path("/retrive_event")
	@Produces({MediaType.APPLICATION_JSON})
    public static  ArrayList<Event>  getAllEvent(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException{
		ArrayList<Event> e1= new ArrayList<Event>();
	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
		  String myEmailID=claims.getSubject();
			 int id=RetriveService.uidfromEmailID(myEmailID);
			 System.out.println("we are in retrive event"+id);
			  e1=EventService.retriveAllEvent(id);
		return e1;
	}
	
	@GET
	@Path("/yourevent")
	@Produces({MediaType.APPLICATION_JSON})
	public static Event getYourEvent(@CookieParam("ID2") int eID)
	{
		Event eobj= new Event();
		eobj=EventService.getYourEventDetail(eID);
		return eobj;
	}
	@GET
	@Path("/inviteapproved")   // user approved for coming in particular event
	@Produces({MediaType.TEXT_PLAIN})
	public static String eventAprrovedbyfriend(@CookieParam("ID") String jwt,@CookieParam("ID4") int eID)throws    UnsupportedEncodingException
	{	Claims claims = Jwts.parser()         
    .setSigningKey("secret".getBytes("UTF-8"))
    .parseClaimsJws(jwt).getBody();
 System.out.println("Subject: " + claims.getSubject());
String myEmailID=claims.getSubject();
int userID=RetriveService.uidfromEmailID(myEmailID);
		EventService.userResponseforEvent(eID, userID);  
	return "udate the event response";
	}
	
	@GET
	@Path("/inviterejected") // user rejected for particular event
	@Produces({MediaType.TEXT_PLAIN})
	public static String eventRejectedbyfriend(@CookieParam("ID") String jwt,@CookieParam("ID4") int eID)throws    UnsupportedEncodingException
	{	Claims claims = Jwts.parser()         
    .setSigningKey("secret".getBytes("UTF-8"))
    .parseClaimsJws(jwt).getBody();
 System.out.println("Subject: " + claims.getSubject());
String myEmailID=claims.getSubject();
int userID=RetriveService.uidfromEmailID(myEmailID);
		EventService.userRejectforEvent(eID, userID);  
	return "reject the event";
	}
	
	@POST
	@Path("/requestforinvite") // user request for inviting member for particulat event
	@Produces({MediaType.TEXT_PLAIN})
	public static String requestForEvent(@CookieParam("ID3") int id,@CookieParam("ID2") int eID)throws    UnsupportedEncodingException
	{
		
		  String info=EventService.inviteUserforEvent(eID, id);
		  System.out.println("we are in request for event method"+info); 
		  return info;
	}
	
	@GET
	@Path("/userListForEvent") // list of user that is come on particular event
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<User> eventUserDetail(@CookieParam("ID2") int eID){
		ArrayList<User> u= new ArrayList<User>();
	    System.out.println("eid is +"+eID); 
		u=EventService.AlluserDetail(eID);
		return u;
	}
	
	
	@DELETE
	@Path("/deleteEvent")  // event is deleted
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	public static String eventDelete(Event eobj)
	{
		int eid=eobj.geteID();
		String str=EventService.eventDelete(eid);
		return str;
	}
	
@GET
@Path("/friendlistwhichisnotinvited")// list of friend that is not invited
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<User> friendlist(@CookieParam("ID") String jwt,@CookieParam("ID2") int eID)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();
	 ArrayList<User> u1= new ArrayList<User>();
	
	u1=GetMyAllFriends.getMyFriends(u1,myEmailID);
	u1=EventService.getFriendthatisNotinvited(u1,eID);
	return u1;
}
		
@GET
@Path("/retriveEventInvitaionlist")
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<Event> getEventList(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();
	 ArrayList<Event> eal=new ArrayList<Event>();
	 int userID=RetriveService.uidfromEmailID(myEmailID);
	 System.out.println("userid is "+userID);
	 eal=EventService.retriveInvitaionList(userID);
	 System.out.println("getEventList end");
	 return eal;
}




@GET
@Path("/retriveincomingInvitaionlist")
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<Event> getEvent(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();
	 ArrayList<Event> eal=new ArrayList<Event>();
	 int userID=RetriveService.uidfromEmailID(myEmailID);
	 //System.out.println("userid is "+userID);
	 eal=EventService.retriveInvitaionList1(userID);
	 //System.out.println("getEventList end");
	 return eal;
}
	
}
