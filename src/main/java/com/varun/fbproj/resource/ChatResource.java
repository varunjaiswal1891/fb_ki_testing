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
import com.varun.fbproj.service.ChatService;
import com.varun.fbproj.service.CommentService;
import com.varun.fbproj.service.GetMyAllFriends;
import com.varun.fbproj.service.LikeService;
import com.varun.fbproj.service.RetriveService;
import com.varun.fbproj.service.StatusService;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import com.varun.fbproj.model.Chat;

@WebService()
@Path("/chat")
public class ChatResource {

	@POST
	@Path("/fetchAll")
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
	@Path("/addNew")
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
	@Path("/fetchNew")
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
	@Path("/getMyAllConversations")
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
	
	
}//class ends here
