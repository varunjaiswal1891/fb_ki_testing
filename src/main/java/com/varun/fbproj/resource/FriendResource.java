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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.varun.fbproj.model.Suggest;
import com.varun.fbproj.model.SuggestUser;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.IsMyFriendService;
import com.varun.fbproj.service.IsRequestAlreadyReceived;
import com.varun.fbproj.service.IsRequestAlreadySentService;
import com.varun.fbproj.service.RetriveService;
import com.varun.fbproj.service.SearchFriendService;
import com.varun.fbproj.service.SuggestedFriendService;
import com.varun.fbproj.service.Suggestservice;
import com.varun.fbproj.service.getfriendemailidservice;

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
         
         System.out.println("Umesh Rao");
		
		return GetMyAllFriends.getMyFriends(al_friends,myEmailID);	
	
	}//findMyFriend method ends here
	
	@GET
    @Path("/getMyAllFriendsuggest")
	@Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> getAllMyFriendsuggest(@CookieParam("ID") String jwt,@CookieParam("ID_group") String group_name 
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
		
		return GetMyAllFriends.getMyFriendsuggestion(al_friends,myEmailID,group_name);	
	
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
	            temp=GetMyAllFriends.getMyFriends(temp,e1,myEmailID);
	            System.out.println("temp before="+temp);
	            
	            
	            
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
	        Collections.sort(al_mutual_friends,new Comparator<User>(){
	            @Override
	            public int compare(User u1,User u2){
	                return u1.getUserID()<u2.getUserID()?-1:1;
	            }
	        });
	        ArrayList<User> mutual_friends_new = new ArrayList<User>();
	        if(al_mutual_friends.size()>0){
	            int val=al_mutual_friends.get(0).getUserID();
	            int val1;
	            mutual_friends_new.add(al_mutual_friends.get(0));
	            for(int i=1;i<al_mutual_friends.size();i++){
	                val1=al_mutual_friends.get(i).getUserID();
	                if(val1!=val){
	                    mutual_friends_new.add(al_mutual_friends.get(i));
	                    val=al_mutual_friends.get(i).getUserID();
	                }
	            }
	        }
	        return mutual_friends_new;    
	    
	    }//people you may know method ends here
	    
	
	
	@POST
    @Path("/suggestedFriends")
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> getSuggestedFriends(Suggest sobj) throws JsonParseException, JsonMappingException, IOException{
	
		
		
		         return Suggestservice.getSuggestedFriends(sobj);	
		
			
	
	}//suggestedFriend method ends here
	
	
	@POST
    @Path("/retrivesuggestedFriends")
	@Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<SuggestUser> retriveSuggestedFriends(String jwt) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("aakhri padav");
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
		
		         return Suggestservice.retriveSuggestedFriends(myEmailID);	
		
			
	
	}//suggestedFriend method ends here
	
	
	
	
	
	
	
	
	
	@POST
    @Path("/confirmsuggestion")
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public static String confirmfriendsuggestion(Suggest sobj) throws JsonParseException, JsonMappingException, IOException{
	
		
		
		          if(Suggestservice.confirmSuggestedFriends(sobj))
		        	  return "confirmed_suggestion";
		          else
		        	  return "confirmed_suggestion_unsuccessful";
		
			
	
	}
	
	@POST
    @Path("/getfriendemail")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public static String getFriendEmail(User u) throws JsonParseException, JsonMappingException, IOException{
	
		int uid=u.getUserID();
			    String myEmailID=getfriendemailidservice.getfriendemailid(uid);
		return myEmailID;
	
	}//findMyFriend method ends here
	
	

	
	@POST
    @Path("/getmyemail")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
    public static String getEmail(String jwt) throws JsonParseException, JsonMappingException, IOException{
	
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
		return myEmailID;
	
	}//findMyFriend method ends here
	
	
	
	
	
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
	
	
	
	
	@GET
    @Path("/count_of_MutualFriends")
	@Produces({MediaType.APPLICATION_JSON})
    public static ArrayList<User> countyourMutualFriends(@CookieParam("ID") String jwt,@CookieParam("ID5") int userID
    		) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
	//System.out.println(email+"------------------");
	String email=RetriveService.emailIDfromuID(userID);
	ArrayList<User> al_friends=new ArrayList<User>();
		ArrayList<User> al_mutual_friends=new ArrayList<User>();
       //  System.out.println("fetching all my friends list");
		al_friends=GetMyAllFriends.getMyFriends(al_friends,myEmailID);// al_friends containg your friend list
		al_mutual_friends=GetMyAllFriends.getMyFriends(al_mutual_friends,email);

		 for(int j=0;j<al_mutual_friends.size();j++)
	       { 
	    	   int flag=0;
	    	   int uid=al_mutual_friends.get(j).getUserID();
	    	   for(int k=0;k<al_friends.size();k++)
	    	   {
	    		   if(uid==al_friends.get(k).getUserID())
	    		   {
	    			flag=1;
	    			break;
	    			   
	    		   }
	    	   }
	    	   if(flag==0)
	    		   al_mutual_friends.remove(j);
	       }	    
		    
		
		
	//	System.out.println("list ="+ al_mutual_friends);
		return al_mutual_friends;	
	
	}//count_of_MutualFriends
	
	
	
	
	
}//class ends here