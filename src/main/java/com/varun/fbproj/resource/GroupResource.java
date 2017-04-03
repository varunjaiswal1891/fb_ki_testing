package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
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
  //This resource class is called when user clicks on create group in group page 
@POST
    @Path("/create_group")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.TEXT_PLAIN})
    public String createGroup(@CookieParam("ID") String jwt,Group g) throws IOException{
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
 String myEmailID=claims.getSubject();
 System.out.println("grpname"+g.getGroup_name());
if(GroupService.createGroup(g.getGroup_name(), myEmailID,g.getGroup_privacy()))
{
return "group created";
}
return null;
}//method create group ends here
//This resource class is called when user clicks on add member in group page
 
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
//This resource class is called when user clicks on Groups button
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

//This resource class is called when admin  deletes group
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
//This resource class is called when admin removes user from group
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

//This resource class is called when user leaves group
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



//This resource class is used to add status
   @POST
   @Path("/addStatus")
   @Consumes({MediaType.APPLICATION_JSON})
   @Produces({MediaType.APPLICATION_JSON})
   public Status addStatus(@CookieParam("ID") String jwt,@CookieParam("ID_group") String group_name,Status obj)throws JsonParseException, JsonMappingException, IOException{

	System.out.println("--------------------------uuuuuuuuuuuuuuuuuuuuuuu");   
    String gname=group_name.replaceAll("%20", " ");
    System.out.println("token: "+jwt);
    System.out.println("desc: "+obj.getStatus_desc());
    Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
  // System.out.println("Expiration: " + claims.getExpiration());
 String myEmailID=claims.getSubject();
 Status status1 = new Status();
Status sobj=new Status();
sobj.setEmailID(myEmailID);
sobj.setGroup_name(gname);
sobj.setStatus_desc(obj.getStatus_desc());
sobj.setTimelineid(obj.getTimelineid());
    int atr=s1.addStatus(sobj);
    
    status1.setStatusID(atr);
     
    return status1;
    
   }     
   
   
   /* this method working good*/
   @POST
   @Path("/addComment")
   @Consumes({MediaType.APPLICATION_JSON})
   @Produces({MediaType.TEXT_PLAIN})
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
   

   
   
   @GET
   @Path("/getGroupAllStatus")
@Produces({MediaType.APPLICATION_JSON})
   public ArrayList<Status> getGroupAllStatus(@CookieParam("ID_group") String group_name,@CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException
   {
    Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
  // System.out.println("Expiration: " + claims.getExpiration());
 String myEmailID=claims.getSubject();
    /*
    String myEmailID="vishal@gmail.com";
    String group_name="ha bahi naya group after";
    */System.out.println("Inside getGroupAllStatus ="+group_name);
    String gname=group_name.replaceAll("%20", " ");
    System.out.println("new string varun = "+gname);
    ArrayList<Status> status_list= new ArrayList<Status>(); 
    //it gives mere all status
status_list.addAll(GroupService.getStatusByGroup(gname,myEmailID)); 
   
return status_list;
   }//method ends here
   
   
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
   
}//class ends
