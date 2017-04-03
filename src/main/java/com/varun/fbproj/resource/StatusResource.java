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
import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.Likes;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.CommentService;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.GroupService;
import com.varun.fbproj.service.LikeService;
import com.varun.fbproj.service.RetriveService;
import com.varun.fbproj.service.StatusService;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


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
    
    
    @POST
    @Path("/addStatus")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Status addStatus(@CookieParam("ID") String jwt,Status obj)throws JsonParseException, JsonMappingException, IOException{

   
    System.out.println("token: "+jwt);
   
   
    System.out.println("desc: "+obj.getStatus_desc());
    System.out.println("feeing: "+obj.getFeeling());
    System.out.println("timelineid: "+obj.getTimelineid());
   
    Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
  // System.out.println("Expiration: " + claims.getExpiration());
 String myEmailID=claims.getSubject();
 System.out.println("emailID: "+myEmailID);
    
 Status status = new Status();
 Status status1 = new Status();
status.setFeeling(obj.getFeeling());
status.setTimelineid(obj.getTimelineid());
    status.setStatus_desc(obj.getStatus_desc());
        status.setEmailID(myEmailID);
    int statusid=s1.addStatus1(status);
       System.out.println("post submitted properly");
   
    status1.setStatusID(statusid);
    return  status1;
   
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
    int x = l1.incrementLike(likeobj);
    if (x==1){
    return "liked";
    }
    if(x==2){
    return "unliked";
    }
     
    return "error";
       
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
    String str="home";
status_list.addAll(s1.getAllDetailsOfEachStatus(myEmailID,str,myEmailID)); 
    System.out.println("length is :"+status_list.size());
 
ArrayList<User> al_friends=new ArrayList<User>();
         System.out.println("fetching all my friends ke status"); 
al_friends=GetMyAllFriends.getMyFriends(al_friends,myEmailID);
for(int i=0;i<al_friends.size();i++)
{
String e1=al_friends.get(i).getEmailID();
status_list.addAll(s1.getAllDetailsOfEachStatus(e1,str,myEmailID));
}
for(int i=0;i<al_friends.size();i++)
{
String e1=al_friends.get(i).getEmailID();
for(int j=0;j<al_friends.size();j++)
{ 
String e2=al_friends.get(j).getEmailID();
status_list.addAll(s1.getAllDetailsOfEachStatus(e1,e2,myEmailID));
   }
   }
String str1="group";
ArrayList<Group> group=new ArrayList<Group>();
group=GroupService.getMyAllGroups(myEmailID,group);
for(int j=0;j<group.size();j++)
{
status_list.addAll(GroupService.getStatusByGroup(group.get(j).getGroup_name(),myEmailID));
}
Collections.sort(status_list,new Comparator<Status>(){
            @Override
            public int compare(Status u1,Status u2){
                return u1.getStatusID()<u2.getStatusID()?-1:1;
            }
        });
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
    String str0="home";
status_list.addAll(s1.getAllDetailsOfEachStatus(myEmailID,str0,myEmailID)); 
    
ArrayList<User> al_friends2=new ArrayList<User>();
        System.out.println("fetching all my friends ke status"); 
al_friends2=GetMyAllFriends.getMyFriends(al_friends2,myEmailID);
for(int i=0;i<al_friends2.size();i++)
{
String e1=al_friends2.get(i).getEmailID();
status_list.addAll(s1.getAllDetailsOfEachStatus(e1,myEmailID,myEmailID));
}
status_list.addAll(s1.getAllDetailsOfEachStatus(myEmailID,myEmailID,myEmailID));
Collections.sort(status_list,new Comparator<Status>(){
            @Override
            public int compare(Status u1,Status u2){
                return u1.getStatusID()<u2.getStatusID()?-1:1;
            }
        });
return status_list;
    }//method ends here
    
    @GET
    @Path("/getOtherAllStatus")
@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Status> getOtherKeAllStatus(@CookieParam("ID1") int userID, @CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException
    {
    User u1=RetriveService.getUserAllDataByUserID(userID);
   
   
    Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
   String myEmailID=claims.getSubject();
  
   
   
   
   
    ArrayList<Status> status_list= new ArrayList<Status>(); 
    //it gives mere all status
    String str1="home";
status_list.addAll(s1.getAllDetailsOfEachStatus(u1.getEmailID(),str1,myEmailID)); 
System.out.println("status_listttttttttttttttttttttttttttttttttttttttttttt"+u1.getEmailID());
ArrayList<User> al_friends1=new ArrayList<User>();
         System.out.println("fetching all my friends ke status"); 
al_friends1=GetMyAllFriends.getMyFriends(al_friends1,u1.getEmailID());
for(int i=0;i<al_friends1.size();i++)
{
String e1=al_friends1.get(i).getEmailID();
status_list.addAll(s1.getAllDetailsOfEachStatus(e1,u1.getEmailID(),myEmailID));
}
status_list.addAll(s1.getAllDetailsOfEachStatus(u1.getEmailID(),u1.getEmailID(),myEmailID));
Collections.sort(status_list,new Comparator<Status>(){
            @Override
            public int compare(Status u1,Status u2){
                return u1.getStatusID()<u2.getStatusID()?-1:1;
            }
        });
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