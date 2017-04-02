package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.varun.fbproj.model.Comment;
import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.Likes;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;
import com.varun.fbproj.model.UserGroup;
import com.varun.fbproj.service.CommentService;
import com.varun.fbproj.service.GroupService;
import com.varun.fbproj.service.LikeService;
import com.varun.fbproj.service.StatusService;
import com.varun.fbproj.service.UserImageService;

@WebService()
@Path("/group")
public class GroupResource {
	
	
	private StatusService s1;
	private CommentService c1;
    private LikeService l1;
   
   
    public GroupResource() {
    	s1=new StatusService();
    	c1=new CommentService();
    	l1=new LikeService();
    
	}
	
	//creates a new group by calling groupService 
	@POST
    @Path("/create_group")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String createGroup(@CookieParam("ID") String jwt,Group grp) throws IOException{
	
		System.out.println("inside group");
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			  String myEmailID=claims.getSubject();
		//String myEmailID="umesh@gmail.com";
		Group grp1=new Group();
		grp1.setGroup_name(grp.getGroup_name());
		grp1.setGroup_privacy(grp.getGroup_privacy());
		grp1.setOwner(myEmailID);
		System.out.println(grp1.getGroup_privacy());
		if(GroupService.createGroup(grp1, myEmailID))
		{
			return "group created";
		}
		
		return null;
	}//method create group ends here
	
	
	//adding a new user to the group
	@POST
    @Path("/addUser_group")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String addUserToGroup(UserGroup g1,@CookieParam("ID_group") String group_name) throws JsonParseException, JsonMappingException, IOException{
	
		String gname=group_name.replaceAll("%20"," ");
		g1.setGroup_name(gname);
		String emailID=g1.getEmailID();
		System.out.println("email here="+emailID);
		System.out.println("group name here="+gname);
		if(GroupService.addUserGroup(gname, emailID))
		{
			return "user added in group";
		}
		
		return "user NOT added in group";
	}//method add user group ends here
	

	//shows the group list of which the user is a part
	@GET
    @Path("/show_my_groups")
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Group> showMyAllGroups(@CookieParam("ID") String jwt) throws IOException{
	
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			  String myEmailID=claims.getSubject();
			  ArrayList<Group> al_groups=new ArrayList<Group>();
			  al_groups=GroupService.getMyAllGroups(myEmailID, al_groups);
			  System.out.println("Group list1="+al_groups);
		return al_groups;
		
	}//show my groups ends here


	//deleting the group using groupService class
	@DELETE
    @Path("/delete_group")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public String deleteGroup(@CookieParam("ID") String jwt,@CookieParam("ID_group") String group_name) throws IOException{
	//deletes the specified group if he is the owner of that group otherwise false
		System.out.println("jwt="+ jwt);
		String gname=group_name.replaceAll("%20", " ");
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
	    System.out.println("Subject: " + claims.getSubject());
	    String myEmailID=claims.getSubject();
		if(GroupService.deleteGroup(gname, myEmailID))
		{
			return "Group deleted";
		}
		return  null;
	}// delete a group ends here
	
	
	//deleting a user from the group
	@DELETE
    @Path("/delete_user_from_group")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String deleteUserByOwner1Group(@CookieParam("ID") String jwt,UserGroup ug1) throws IOException{
	//deletes the specified group if he is the owner of that group otherwise false
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
	    System.out.println("Subject: " + claims.getSubject());
	    String owner=claims.getSubject();
		if(GroupService.deleteUserByOwnerGroup(ug1.getGroup_name(),owner,ug1.getEmailID()))
		{
			return "user deleted from group";
		}
		return  "Not authorized to delete user";
	}// delete a group ends here
	

	//the user himself wants to leave the group
	@DELETE
    @Path("/leave_group")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public String deleteUserInGroup(@CookieParam("ID") String jwt,String group_name) throws IOException{
	//deletes member whether owner or other person
		String gname=group_name.replaceAll("%20", " ");
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
	    System.out.println("Subject: " + claims.getSubject());
	    String emailID=claims.getSubject();
		if(GroupService.deleteUserInGroup(gname,emailID))
		{
			return "user left group";
		}
		return  null;
	}// delete a group ends here
	
	
	
	
	//A user adding status to a particular group of which he is a part
	    @POST
	    @Path("/addStatus")
	   // @Consumes({MediaType.TEXT_PLAIN})
	    @Produces({MediaType.TEXT_PLAIN})
	    public String addStatus(@CookieParam("ID") String jwt,String status_desc,@CookieParam("ID_group") String group_name)throws JsonParseException, JsonMappingException, IOException{

	    	String gname=group_name.replaceAll("%20", " ");
	    	System.out.println("token: "+jwt);
	    	//System.out.println("desc: "+status.getStatus_desc());
	    	Claims claims = Jwts.parser()         
				       .setSigningKey("secret".getBytes("UTF-8"))
				       .parseClaimsJws(jwt).getBody();
				    System.out.println("Subject: " + claims.getSubject());
				   // System.out.println("Expiration: " + claims.getExpiration());
				  String myEmailID=claims.getSubject();
			
		 Status sobj=new Status();
		 sobj.setEmailID(myEmailID);
		 sobj.setGroup_name(gname);
		 sobj.setStatus_desc(status_desc);
	    	if(s1.addStatus(sobj)){
	    	    System.out.println("post submitted properly in group");
	    		return "You posted in group";
	    	}
	    		return "status not posted";
	    } // end of addStatus
	    
	    
	    //adding a comment to a particular post in a group
	    @POST
	    @Path("/addComment")
	    @Consumes({MediaType.APPLICATION_JSON})
	    public String addComment(@CookieParam("ID") String jwt,@CookieParam("ID_group") String group_name,Comment cobj)throws JsonParseException, JsonMappingException, IOException{
	    	
	    	System.out.println("inside addComment");
	    	String gname=group_name.replaceAll("%20", " ");
	    	
	    	 Claims claims = Jwts.parser()         
				       .setSigningKey("secret".getBytes("UTF-8"))
				       .parseClaimsJws(jwt).getBody();
				    System.out.println("Subject: " + claims.getSubject());
				    String myEmailID=claims.getSubject();
				
					 cobj.setEmailID(myEmailID);
					 cobj.setGroup_name(gname);
					 //cobj.setComment_desc(comment_desc);
					 
	    	if(c1.addComment(cobj)){
	    	   System.out.println("upto resource comment added successfully");
	    		return "comment posted";
	    	}
	    	
	    	return "comment NOT posted";
	     }//addComment ends here
	    
	    
	    //a user likes a post in the group
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
	    }//method ends here


	    //get all status of the particular requested group
	    @GET
	    @Path("/getGroupAllStatus")
		@Produces({MediaType.APPLICATION_JSON})
	    public ArrayList<Status> getGroupAllStatus(@CookieParam("ID_group") String group_name) throws JsonParseException, JsonMappingException, IOException
	    {
	    	System.out.println("Inside getGroupAllStatus ="+group_name);
	    	String gname=group_name.replaceAll("%20", " ");
	    	System.out.println("new string varun = "+gname);
	    	ArrayList<Status> status_list= new ArrayList<Status>(); 
	    	//it gives mere all status
			status_list.addAll(GroupService.getStatusByGroup(gname)); 
	    
			return status_list;
	    }//method ends here
	    
	
	    //get a list of members of the group
	    @GET
	    @Path("/getGroupMembers")
		@Produces({MediaType.APPLICATION_JSON})
	    public ArrayList<User> getMembersOfGroup(@CookieParam("ID_group") String group_name) throws JsonParseException, JsonMappingException, IOException
	    {
	    	String gname=group_name.replaceAll("%20", " ");
	    	System.out.println("Inside getGroupMembers ");
	    	ArrayList<User> user_list= new ArrayList<User>(); 
	    	user_list=GroupService.getGroupMembers(gname);
			return user_list;
			
	    }//method ends here
	    
	    
	    
	    //Requesting the owner of the group
	    @GET
	    @Path("/getGroupAdmin")
		@Produces({MediaType.APPLICATION_JSON})
	    public User getAdminOfGroup(@CookieParam("ID_group") String group_name) throws JsonParseException, JsonMappingException, IOException
	    {
	    	String gname=group_name.replaceAll("%20", " ");
	    	User uobj=new User();
	    	uobj=GroupService.getGroupAdmin(gname);
			return uobj;
			
	    }//method ends here
	    
	    @POST
		@Path("/uploadPic")
		@Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.MULTIPART_FORM_DATA)
		public Response updateProfilePic(
				
		        @FormDataParam("file") InputStream fileInputStream,
		        @FormDataParam("file") FormDataContentDisposition fileFormDataContentDisposition,@CookieParam("ID_group") String token,@CookieParam("ID") String group_name) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException, URISyntaxException {
		    // local variables
			
		    String fileName = null;
		    String uploadFilePath=null;
		    System.out.print("backend");
		    System.out.print(token);
		    System.out.println("jwt="+ token);
			Claims claims = Jwts.parser()         
				       .setSigningKey("secret".getBytes("UTF-8"))
				       .parseClaimsJws(token).getBody();
				    System.out.println("Subject: " + claims.getSubject());
				    System.out.println("Expiration: " + claims.getExpiration());
				  String emailID=claims.getSubject();
		    //System.out.print(userId);
		    fileName = fileFormDataContentDisposition.getFileName();
		    uploadFilePath=new GroupService().uploadPic(fileInputStream, fileName,group_name,emailID);
		    URI url = new URI("http://localhost:8080/DemoFB/groupPage.html");
		    return Response.temporaryRedirect(url).build();
		

		}
}
	
	
