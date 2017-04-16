package com.varun.fbproj.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.omg.CORBA.Current;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mysql.fabric.xmlrpc.base.Data;
import com.varun.fbproj.model.Event;
import com.varun.fbproj.model.EventReview;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.EventService;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.GroupService;
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
// update Event	
	@POST
	@Path("/update_event") // event is updated
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	public String updateEvent(@CookieParam("ID") String jwt,Event eobj,@CookieParam("ID2") int eID) throws    UnsupportedEncodingException{
	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			  String myEmailID=claims.getSubject();
			  //  user id from user mail id
			 
		eobj.setuID(RetriveService.uidfromEmailID(myEmailID));
		eobj.seteID(eID);
		
		String s="null";
	
		
		s=EventService.updateEvent(eobj);
		return s;
	}	
	
	
	// get all events created by you
	@GET
	@Path("/eventSummary")
	@Produces({MediaType.TEXT_PLAIN})
    public static  String  getEventSummary(@CookieParam("ID2") int eID){
		String summary=null;
		summary=EventService.getEventSummary(eID);
		return summary;
	}
	
	
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
	
	
	
	  @POST
	    @Path("/addReview")
	    @Consumes({MediaType.TEXT_PLAIN})
	    @Produces({MediaType.TEXT_PLAIN})
	    public String addStatus(@CookieParam("ID") String jwt,String status_desc,@CookieParam("ID2") int eID)throws JsonParseException, JsonMappingException, IOException{

	    
	    	System.out.println("token: "+jwt);
	    	Claims claims = Jwts.parser()         
				       .setSigningKey("secret".getBytes("UTF-8"))
				       .parseClaimsJws(jwt).getBody();
				    System.out.println("Subject: " + claims.getSubject());
				   // System.out.println("Expiration: " + claims.getExpiration());
				  String myEmailID=claims.getSubject();
              int userID=RetriveService.uidfromEmailID(myEmailID);
            String s= EventService.insertReview(eID,userID,status_desc);  
	    		return s;
	    } // end of addStatus
	
	  
	  @POST
	    @Path("/addSummary")
	    @Consumes({MediaType.TEXT_PLAIN})
	    @Produces({MediaType.TEXT_PLAIN})
	    public String addSummary(@CookieParam("ID") String jwt,String status_desc,@CookieParam("ID2") int eID)throws JsonParseException, JsonMappingException, IOException{

	    
	    	System.out.println("token: "+jwt);
	    	Claims claims = Jwts.parser()         
				       .setSigningKey("secret".getBytes("UTF-8"))
				       .parseClaimsJws(jwt).getBody();
				    System.out.println("Subject: " + claims.getSubject());
				   // System.out.println("Expiration: " + claims.getExpiration());
				  String myEmailID=claims.getSubject();
            int userID=RetriveService.uidfromEmailID(myEmailID);
          String s= EventService.insertSummary(eID,userID,status_desc);  
	    		return s;
	    } // end of addStatus  
	
	
	
	@GET
	@Path("/yourevent") // geting event details for particular event
	@Produces({MediaType.APPLICATION_JSON})
	public static Event getYourEvent(@CookieParam("ID2") int eID)
	{
		Event eobj= new Event();
		eobj=EventService.getYourEventDetail(eID);
		return eobj;
	}

	// checking admin or not
	@GET
	@Path("/adminornot")
	@Produces({MediaType.TEXT_PLAIN})
	public static int  adminorNot(@CookieParam("ID") String jwt,@CookieParam("ID2") int eID)throws JsonParseException, JsonMappingException, IOException{

	    
    	System.out.println("token: "+jwt);
    	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
        int userID=RetriveService.uidfromEmailID(myEmailID);
        int s= EventService.IsadminorNot(eID,userID);
        System.out.println("your userID is"+userID+" email ID is "+myEmailID);
        
    		return s;
    } // end of function
	
	
	
	
	@GET
	@Path("/youreventreviewlist") // geting event review details for particular event
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<EventReview> getYourEventReview(@CookieParam("ID2") int eID)
	{
		ArrayList<EventReview> erl= new ArrayList<EventReview>();
		erl= EventService.getEventReview(eID); 
		
		return erl;
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
	

	
	
	
	@GET
	@Path("/invitemaybe") // your maybe going in that event
	@Produces({MediaType.TEXT_PLAIN})
	public static String eventmaybebyfriend(@CookieParam("ID") String jwt,@CookieParam("ID11") int eID)throws    UnsupportedEncodingException
	{	Claims claims = Jwts.parser()         
    .setSigningKey("secret".getBytes("UTF-8"))
    .parseClaimsJws(jwt).getBody();
 System.out.println("Subject: " + claims.getSubject());
String myEmailID=claims.getSubject();
int userID=RetriveService.uidfromEmailID(myEmailID);
		EventService.userMayBeComingforEvent(eID, userID);  
	return "not sure for coming in event";
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

	
	@GET
	@Path("/userListFor_may_be_coming_in_Event") // list of user that is come on particular event
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<User> eventUserDetailforMayBeComing(@CookieParam("ID2") int eID){
		ArrayList<User> u= new ArrayList<User>();
	    System.out.println("eid is +"+eID); 
		u=EventService.AlluserDetail1(eID);
		return u;
	}
	
	
	@GET
	@Path("/userListFor_not_coming_in_Event") // list of user that is come on particular event
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<User> eventUserDetailfornotComing(@CookieParam("ID2") int eID){
		ArrayList<User> u= new ArrayList<User>();
	    System.out.println("eid is +"+eID); 
		u=EventService.AlluserDetail2(eID);
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
@Path("/retriveyourpastevent") // your past event is come where you had gone
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<Event> getPastEvent(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();
	
	 ArrayList<Event> eal=new ArrayList<Event>();
	 int userID=RetriveService.uidfromEmailID(myEmailID);
	 //System.out.println("userid is "+userID);
	 eal=EventService.retriveInvitaionList2(userID);
	 //System.out.println("getEventList end");
	 return eal;
}



@GET
@Path("/retriveincomingInvitaionlist") // your incomin event is come where you are going
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

@GET
@Path("/retriveincomingInvitaionlist1") // your incomin event is come where you are going
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<Event> getYourEvent(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();
	
	 ArrayList<Event> eal=new ArrayList<Event>();
	 int userID=RetriveService.uidfromEmailID(myEmailID);
	 //System.out.println("userid is "+userID);
	 eal=EventService.retriveInvitaionList3(userID);
	 //System.out.println("getEventList end");
	 return eal;
}


@GET
@Path("/MonthBirthdayList") // geting all user birthdaylisy of this month
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<User> getAllBirthdayList()
{
	
	ArrayList<User> blist= new ArrayList<User>();
	blist=EventService.getBirthdayList();
	return blist;
}


@GET
@Path("/TodayBirthdayList")
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<User> getTodayBirthdayList()
{
	
	ArrayList<User> blist= new ArrayList<User>();
	blist=EventService.getTodayBirthdayList();
	return blist;
}
@GET
@Path("/todayYourBirthday") // check your birthday is/not today ?
@Produces({MediaType.TEXT_PLAIN})
public static boolean isyourBirthday(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		    System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();
	 ArrayList<Event> eal=new ArrayList<Event>();
	 int userID=RetriveService.uidfromEmailID(myEmailID);
	ArrayList<User> blist= new ArrayList<User>();
	blist=EventService.getTodayBirthdayList();
	for(int i=0;i<blist.size();i++){
		if(userID==blist.get(i).getUserID())
			return true;
	 }

	
	return false;
}


@GET
@Path("/friendBirthdayList")  //get your friend birthday list of this month
@Produces({MediaType.APPLICATION_JSON})
public static ArrayList<User> getFriendBirthdayList(@CookieParam("ID") String jwt)throws    UnsupportedEncodingException
{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		  //  System.out.println("Subject: " + claims.getSubject());
	  String myEmailID=claims.getSubject();

	ArrayList<User> u1= new ArrayList<User>();
    	u1=GetMyAllFriends.getMyFriends(u1,myEmailID);
    
	ArrayList<User> blist= new ArrayList<User>();
	ArrayList<User> blist1=new ArrayList<User>();
	blist=EventService.getBirthdayList();
	
	int flag;
	for(int i=0;i<blist.size();i++){
		
		int id= blist.get(i).getUserID();
		 flag=0;
		for(int j=0;j<u1.size();j++)
		{
			if(id==u1.get(j).getUserID()){
			         flag=1;
			         break;
			}
		}
		if(flag==1){
			blist1.add(blist.get(i));
		}
	}
	
	return blist1;
}




@GET
@Path("/create_birthday_event") // event is created IF YOUR BIRTHDAY is today
@Produces({MediaType.TEXT_PLAIN})
public String createBirthdayEvent(@CookieParam("ID") String jwt) throws    UnsupportedEncodingException{
Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		  String myEmailID=claims.getSubject();
		User u1= new User();
		 u1= RetriveService.getUserAllData(myEmailID);
		 Event eobj=new Event();
		 eobj.setEname("Birthday_party");
		 eobj.setuID(u1.getUserID());
		 eobj.setEventType("Private");
		 eobj.setHname(u1.getFname());
		 eobj.setLocation(u1.getHometown());
		 eobj.setTime("5:00 PM");
		 
		java.util.Date date = new java.util.Date();
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 
		 
		 
			eobj.setEvent_date(sdf.format(date));
			eobj.setEvent_end_date(sdf.format(date));
		
		 String s=EventService.createNewEvent(eobj);
			return s;
}

@GET
@Path("/eventnotification") // event notification when event is deleted
@Produces({MediaType.APPLICATION_JSON})
public ArrayList<Event> EventNotification(@CookieParam("ID") String jwt) throws    UnsupportedEncodingException{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		  String myEmailID=claims.getSubject();
		  int id= RetriveService.uidfromEmailID(myEmailID);
	ArrayList<Event> e1= new ArrayList<Event>();
	e1=EventService.getEventNotification(id);
	
return e1;
}


@GET
@Path("/eventupdatenotification") // event notification when event is deleted
@Produces({MediaType.APPLICATION_JSON})
public ArrayList<Event> EventupdateNotification(@CookieParam("ID") String jwt) throws    UnsupportedEncodingException{
	Claims claims = Jwts.parser()         
		       .setSigningKey("secret".getBytes("UTF-8"))
		       .parseClaimsJws(jwt).getBody();
		  String myEmailID=claims.getSubject();
		  int id= RetriveService.uidfromEmailID(myEmailID);
	ArrayList<Event> e1= new ArrayList<Event>();
	e1=EventService.getEventupdateNotification(id);
	
return e1;
}




}