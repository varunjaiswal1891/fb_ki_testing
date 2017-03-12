package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.IsMyFriendService;
import com.varun.fbproj.service.IsRequestAlreadyReceived;
import com.varun.fbproj.service.IsRequestAlreadySentService;
import com.varun.fbproj.service.RetriveService;
import com.varun.fbproj.service.SearchFriendService;
import com.varun.fbproj.service.SuggestedFriendService;

import java.util.*;

@WebService()
@Path("/friend")
public class FriendResource {

	@GET
    @Path("/getMyAllFriends")
	@Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> getAllMyFriend(@CookieParam("ID") String jwt
    		) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("inside get my all friends");
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
		
		ArrayList<User> al_friends=new ArrayList<User>();
         System.out.println("fetching all my friends list");
		
		return GetMyAllFriends.getMyFriends(al_friends,myEmailID);	
	
	}//findMyFriend method ends here
	
	@GET
    @Path("/searchFriend")
	@Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<User> searchFriend(String friendName)
    	        throws JsonParseException, JsonMappingException, IOException{
	//this method will return all users whose name we enter in find friend input box
		System.out.println("find my friends resource");
		String fn="tendulkar";
		//fn=friendName;
		
		ArrayList<User> al_friends=new ArrayList<User>();
        return SearchFriendService.searchFriends(al_friends, fn);
	 //we need to return list of user objects in json format	
	
	}//searchFriend method ends here
	

	@GET
    @Path("/peopleYouMay_KnowMutualFriends")
	@Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> peopleYouMayKnow(@CookieParam("ID") String jwt
    		) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
		
		ArrayList<User> al_friends=new ArrayList<User>();
		ArrayList<User> al_mutual_friends=new ArrayList<User>();
         System.out.println("fetching all my friends list");
		al_friends=GetMyAllFriends.getMyFriends(al_friends,myEmailID);
		System.out.println("here in friend resource of people u may know"+al_friends.toString());
		for(int i=0;i<al_friends.size();i++)
		{
			String e1=al_friends.get(i).getEmailID(); // e1 is a frnd
			ArrayList<User> temp=new ArrayList<User>();
					 System.out.println("fetching all my friends k frnds list");
			temp=GetMyAllFriends.getMyFriends(temp,e1);
			System.out.println("temp before="+temp);
			
			Iterator<User> iter = temp.iterator();
			/*for(int j=0;j<temp.size();j++){
			 *    if(temp.get(j).getEmailID()==myEmailID){
			 *       temp.remove(j);
			 * }
			 */
			
			
			/***here we are removing the self entry i.e frnds ki frnd list me mera bhi naam rhega wo hatao****/
			while (iter.hasNext()) 
			{
			    User user = iter.next();
			    if(user.getEmailID().equals(myEmailID))
			    {
			        //Use iterator to remove this User object.
			        iter.remove();
			    }
			}					
			System.out.println("temp after="+temp);
			
			
			for(int j=0;j<temp.size();j++)
			{
				String e2=temp.get(j).getEmailID();
				System.out.println("e2="+e2);
				/** if frnd ka frnd is not my frnd then add it to list of mutualfrnds**/
				if(!IsMyFriendService.isMyFriend(myEmailID, e2))
				{
					System.out.println("yes add to people you may know");
					User u1=new User();
					u1=RetriveService.getUserAllData(e2);
					al_mutual_friends.add(u1);
				}			
			}//for loop j wala end
			
		}//for loop i wala end
		
		 for(int j=0;j<al_mutual_friends.size();j++)
			{
				String e2=al_mutual_friends.get(j).getEmailID();  // e2 will contain the email id from which some may belong to  request already sent or received or people u mayknow
				System.out.println("e2="+e2);    
             if(IsRequestAlreadySentService.isRequestAlreadySent(myEmailID,e2)){
				
				System.out.println("already sent request to "+e2+" so dont suggest this to me");
				//al_friends.remove(j);
				Iterator<User> iter = al_mutual_friends.iterator();
				while (iter.hasNext()) 
				{
				    User user = iter.next();
				    if(user.getEmailID().equals(e2))
				    {
				        //Use iterator to remove this User object.
				        iter.remove();
				    }
				}
				 
			 	
			 } //  IsRequestAlreadySentService.isRequestAlreadySent ends
             else{
             	if(IsRequestAlreadyReceived.isRequestAlreadyReceived(myEmailID,e2)){
						
						System.out.println("already received  request from"+e2+" so dont suggest this to me");
						//al_friends.remove(j);
						Iterator<User> iter = al_mutual_friends.iterator();
						while (iter.hasNext()) 
						{
						    User user = iter.next();
						    if(user.getEmailID().equals(e2))
						    {
						        //Use iterator to remove this User object.
						        iter.remove();
						    }
						}
						 
             }
             	else
					{
					  System.out.println(al_mutual_friends.get(j).getEmailID()+"not a frnd so add it again");}
					}
		    
			} 
		    
		    
		
		
		System.out.println("list ="+ al_mutual_friends);
		return al_mutual_friends;	
	
	}//people you may know method ends here
	

	
	
	@GET
    @Path("/suggestedFriends")
	@Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> getSuggestedFriends(@CookieParam("ID") String jwt
    		) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
		
		ArrayList<User> al_friends=new ArrayList<User>();
         System.out.println("IN RESOURCE:::fetching all my suggested friends list");
         for(User u:al_friends){
        	 System.out.println(u.getEmailID());
         }
         al_friends=SuggestedFriendService.getSuggestedFriends(al_friends, myEmailID);	
		
		return al_friends;	
	
	}//suggestedFriend method ends here

	
	
	@GET
    @Path("/myFriendOrNot")
	@Produces({MediaType.APPLICATION_JSON})
    public static User MyFriendorNot(@CookieParam("ID") String jwt,
    		@CookieParam("ID1") int userID
    		) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("inside my friend or not");
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
		User u1=RetriveService.getUserAllDataByUserID(userID);
		String other_emailID=u1.getEmailID();
		if(IsMyFriendService.isMyFriend(myEmailID, other_emailID))
		{
			u1.setMob_no("1");//yes already my friend
			
		}
		else
		{
			u1.setMob_no("0");
		}
		return u1;
	
	}//findMyFriend method ends here
	
	
	
}//class ends here