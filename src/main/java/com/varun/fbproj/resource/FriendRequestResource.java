package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.varun.fbproj.model.SuggestUser;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.FriendRequestService;


@WebService()
@Path("/friendRequest")
public class FriendRequestResource {
	
	@POST
    @Path("/addFriend")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public static String addFriendRequest(@CookieParam("ID") String jwt,String friendEmailID
    		) throws JsonParseException, JsonMappingException, IOException{
		System.out.println("inside add friend");
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			  
			  String myEmailID=claims.getSubject();
		if(FriendRequestService.addFriendRequest(myEmailID, friendEmailID))
		{
			return "Request sent";
		}
		return "Request not sent";
		
	}//method ends here
	
	@POST
    @Path("/addFriendSuggestion")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public static String addFriendRequest(SuggestUser su
    		) throws JsonParseException, JsonMappingException, IOException{
		
		if(FriendRequestService.addFriendSuggestion(su))
		{
			return "Request sent";
		}
		return "Request not sent";
		
	}//method ends here
	
	
	@DELETE
    @Path("/removeFriend")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public static void removeFriendSuggested(@CookieParam("ID") String jwt,String friendEmailID
    		) throws JsonParseException, JsonMappingException, IOException{
		
		
		
		
	}//method ends here
	
	@POST
    @Path("/confirmRequest")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public static String confirmFriendRequest(@CookieParam("ID") String jwt,String friendEmailID
    		) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
		
			  String myEmailID=claims.getSubject();
		
			  FriendRequestService.updateOldRequest(myEmailID, friendEmailID);
			  if(FriendRequestService.confirmFriendRequest(myEmailID, friendEmailID))
				{
					return "Request accepted";
				}
				return "Request rejected";
		
	}//method ends here
	
	@DELETE
    @Path("/deleteRequest")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public static String deleteFriendRequest(@CookieParam("ID") String jwt,String friendEmailID
    		) throws JsonParseException, JsonMappingException, IOException{
		System.out.println("inside delete");
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
		
		if(FriendRequestService.deleteFriendRequest(myEmailID, friendEmailID))
		{
			return "Request deleted successfully";
		}
		return "Request deletion failed";
	}//method ends here
	
	//Removes friend suggestion from the list
	@DELETE
    @Path("/removeRequest")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public static String removeFriendRequest(@CookieParam("ID") String jwt,String suggestEmailID
    		) throws JsonParseException, JsonMappingException, IOException{
		System.out.println("*INSIDE REMOVE*");
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			  String myEmailID=claims.getSubject();
		
		if(FriendRequestService.removeFriendRequest(myEmailID, suggestEmailID))
		{
			return "Request REMOVED successfully";
		}
		return "Request REMOVAL failed";
	}//method ends here
	
	@GET
    @Path("/giveMyRequests")
	@Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> getMyALLRequests(@CookieParam("ID") String jwt
    		) throws JsonParseException, JsonMappingException, IOException{
		//it returns all friend requests which came to me
		ArrayList<User> al_requestList=new ArrayList<User>();	
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			  String myEmailID=claims.getSubject();	
		al_requestList=FriendRequestService.getAllFriendRequest(myEmailID, al_requestList);
		
		
		
		return al_requestList;
	}//method ends here
	
	

}//class ends