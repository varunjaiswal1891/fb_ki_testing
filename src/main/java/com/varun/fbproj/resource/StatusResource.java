package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.varun.fbproj.model.Comment;
import com.varun.fbproj.model.Likes;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.CommentService;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.LikeService;
import com.varun.fbproj.service.RetriveService;
import com.varun.fbproj.service.StatusService;

import java.io.*;
import java.util.ArrayList;


@WebService()
@Path("/status")
public class StatusResource {
	
	private StatusService s1;
	private CommentService c1;
    private LikeService l1;
   
   
    public StatusResource() {
    	s1=new StatusService();
    	c1=new CommentService();
    	l1=new LikeService();
    
	}
    
    
    /* this method working good*/
    @POST
    @Path("/addStatus")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.TEXT_PLAIN})
    public String addStatus(@CookieParam("ID") String jwt,String statusdesc)throws JsonParseException, JsonMappingException, IOException{

    	
    	System.out.println("token: "+jwt);
    	System.out.println("desc: "+statusdesc);
    	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			   // System.out.println("Expiration: " + claims.getExpiration());
			  String myEmailID=claims.getSubject();
			  
			  Status status = new Status();
			  
    	status.setStatus_desc(statusdesc);
       	status.setEmailID(myEmailID);
    	if(s1.addStatus(status)){
    	    System.out.println("post submitted properly");
    		return "You posted";
    	}
    		return "status not posted";
    } // end of addStatus
    
    
    /* this method working good*/
    @POST
    @Path("/addComment")
    @Consumes({MediaType.APPLICATION_JSON})
    public String addComment(@CookieParam("ID") String jwt,Comment cmt)throws JsonParseException, JsonMappingException, IOException{
    	
    	System.out.println("inside addComment");
    	
    	
    	 Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
    	
    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa"+cmt.getComment_desc());
    	
    	cmt.setComment_desc(cmt.getComment_desc());
    	cmt.setEmailID(myEmailID);
    	if(c1.addComment(cmt)){
    	   System.out.println("upto resource comment added successfully");
    		return "comment posted";
    	}
    	
    	return "comment NOT posted";
     }//addComment ends here
    
    
    /* this method working good*/
    @POST
    @Path("/incrementLike")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public String incrementLike(@CookieParam("ID") String jwt,Status statusobj)throws JsonParseException, JsonMappingException, IOException{
    	 System.out.println("inside increment like resource");
    	 
    	 
    	 Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
  	
			    Likes likeobj = new Likes();
    	 
			    System.out.println("status id in resource: "+statusobj.getStatusID());
    	 int sid=statusobj.getStatusID();
     	 likeobj.setEmailID(myEmailID);
    	 likeobj.setStatusID(sid);
     	 if(l1.incrementLike(likeobj)==1){
    		 return "like incremented";
    	 }
    	 else 
    	     return "like not updated";
    }
    

    @POST
    @Path("/decrementLike")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public String decrementLike(@CookieParam("ID") String jwt,Status statusobj)throws JsonParseException, JsonMappingException, IOException{
    	 System.out.println("inside increment like resource");
    	 
    	 
    	 Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
  	
			    Likes likeobj = new Likes();
    	 
			    System.out.println("status id in resource: "+statusobj.getStatusID());
    	 int sid=statusobj.getStatusID();
     	 likeobj.setEmailID(myEmailID);
    	 likeobj.setStatusID(sid);
     	 if(l1.decrementLike(likeobj)==1){
    		 return "like decremented";
    	 }
    	 else 
    	     return "like not decremented";
    }

    
    
    
    //this method gives all mere and my frineds k status to be shown on userhome page
    @GET
    @Path("/getAllStatus")
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Status> getALLStatusByUser(@CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException
    {
    	System.out.println("Inside getALLStatusByUser ");
    	System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
    	
    	ArrayList<Status> status_list= new ArrayList<Status>(); 
    	//it gives mere all status
		status_list.addAll(s1.getAllDetailsOfEachStatus(myEmailID)); 
    	System.out.println("length is :"+status_list.size());
		
		 
		 ArrayList<User> al_friends=new ArrayList<User>();
         System.out.println("fetching all my friends ke status");		
		al_friends=GetMyAllFriends.getMyFriends(al_friends,myEmailID);
		for(int i=0;i<al_friends.size();i++)
		{
			String e1=al_friends.get(i).getEmailID();
			status_list.addAll(s1.getAllDetailsOfEachStatus(e1));
			
		}
			//System.out.println("likes = "+status_list.get(0).getLikesCount());	 
		return status_list;
		   
    }//getALLStatusByUser ends here
    
    @GET
    @Path("/getMyStatus")
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Status> getALLMyStatus(@CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException
    {
    	System.out.println("Inside getALLMyStatus ");
    	System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    String myEmailID=claims.getSubject();
    	
    	ArrayList<Status> status_list= new ArrayList<Status>(); 
    	//it gives mere all status
		status_list.addAll(s1.getAllDetailsOfEachStatus(myEmailID)); 
    
		return status_list;
    }//method ends here
    
    @GET
    @Path("/getOtherAllStatus")
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Status> getOtherKeAllStatus(@CookieParam("ID1") int userID) throws JsonParseException, JsonMappingException, IOException
    {
    	User u1=RetriveService.getUserAllDataByUserID(userID);
    	
    	ArrayList<Status> status_list= new ArrayList<Status>(); 
    	//it gives mere all status
		status_list.addAll(s1.getAllDetailsOfEachStatus(u1.getEmailID())); 
    
		return status_list;
    }
    
    @POST
    @Path("/getAllLikeName")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public  ArrayList<User> GetAllLikeName(Status obj) throws JsonParseException, JsonMappingException, IOException
    {
    	System.out.println("inside likehover");
    	int id=obj.getStatusID();
    	System.out.println("statusid"+id);
    	
		return s1.getStatusLikesName(id);
    } 
    
    

}//Ststus resource class ends here
