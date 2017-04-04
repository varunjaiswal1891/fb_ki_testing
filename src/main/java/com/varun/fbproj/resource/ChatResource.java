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
import com.varun.fbproj.service.ChatService;
import com.varun.fbproj.service.GroupService;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import com.varun.fbproj.model.Chat;

@WebService()
@Path("/chat")
public class ChatResource {

@POST
@Path("/fetchAll")//Calls the service for fetching up all the messages
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public ArrayList<Chat> fetchAll(@CookieParam("ID") String jwt,Chat c) throws JsonParseException, JsonMappingException, IOException
{
ArrayList<Chat> ch = new ArrayList<Chat>();
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
   System.out.println("Expiration: " + claims.getExpiration());
 String emailID=claims.getSubject();
 c.setSenderEmail(emailID);
 ch=ChatService.getAll(c);
return ch;
}
@POST
@Path("/addNew")//calls the service for adding up a new message and returns up all the messages.
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<Chat> addNew(@CookieParam("ID") String jwt,Chat c) throws JsonParseException, JsonMappingException, IOException
{
System.out.println("Inside chat resource");
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
   System.out.println("Expiration: " + claims.getExpiration());
   System.out.println("adding new msg  :"+c.getMessage());
 String emailID=claims.getSubject();
 c.setSenderEmail(emailID);
// c.setSenderEmail("jeetu@gmail.com");
 c.setStatus("NEW");
 Date date=java.util.Calendar.getInstance().getTime();  
 System.out.println(date);
 c.setTime(date.toString());
 if(ChatService.postMsg(c))
 {
 return ChatService.getAll(c);
 }
return null;
}
@POST
@Path("/fetchNew")//fetches up a new message
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public ArrayList<Chat> fetchNew(@CookieParam("ID") String jwt,Chat c) throws JsonParseException, JsonMappingException, IOException
{
ArrayList<Chat> ch = new ArrayList<Chat>();
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
   System.out.println("Expiration: " + claims.getExpiration());
 String emailID=claims.getSubject();
 c.setReceiverEmail(emailID);
 ch=ChatService.fetchNew(c);
return ch;
}
@GET
@Path("/getMyAllConversations")//gets all the conversation by the logged in person.
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<Chat> fetchConversations(@CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException
{
System.out.println("inside recent");
ArrayList<Chat> ch = new ArrayList<Chat>();
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject of recent: " + claims.getSubject());
   System.out.println("Expiration of recent: " + claims.getExpiration());
 String emailID=claims.getSubject();
 ch=ChatService.fetchAllConversations(emailID);
return ch;
}
@GET
@Path("/deletemessage1")//deletes all the messages.
@Produces(MediaType.TEXT_PLAIN)
public String deleteConversations(@CookieParam("ID") String jwt,@CookieParam("FID") String friendID) throws JsonParseException, JsonMappingException, IOException
{
   friendID=friendID.replaceAll("%40", "@");
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject of recent: " + claims.getSubject());
   System.out.println("Expiration of recent: " + claims.getExpiration());
 String emailID=claims.getSubject();
 System.out.println("myemailID is"+emailID+" "+"friendemailidis "+friendID);
 String s=ChatService.deleteyourAllMessage(emailID,friendID);
 return s;
}
@POST
    @Path("/deleteonly1")//deletes a single message.
@Consumes({MediaType.TEXT_PLAIN})
@Produces({MediaType.TEXT_PLAIN})
    public String deleteSinglemessage(@CookieParam("ID") String jwt,int chatID,@CookieParam("FID") String friendID) throws IOException{
 friendID=friendID.replaceAll("%40", "@");
System.out.println("jwt="+ jwt);
Claims claims = Jwts.parser()         
      .setSigningKey("secret".getBytes("UTF-8"))
      .parseClaimsJws(jwt).getBody();
   System.out.println("Subject: " + claims.getSubject());
 String myEmailID=claims.getSubject();

 String s=ChatService.deleteyouroneMessage(myEmailID,friendID,chatID);
return s;
}//method create group ends here
}//class ends here
