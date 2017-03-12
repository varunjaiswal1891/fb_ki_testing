package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.Key;
import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.*;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.LoginService;
import com.varun.fbproj.service.LogoutService;
import com.varun.fbproj.service.RetriveNameService;
import com.varun.fbproj.service.RetriveService;
import com.varun.fbproj.service.SearchFriendService;
import com.varun.fbproj.service.SignUpService;
import com.varun.fbproj.service.TokenService;
import com.varun.fbproj.service.UpdateService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
@WebService()
@Path("/user")
public class UserResource {
	
	private SignUpService s1;

	public UserResource() {
		s1 = new SignUpService();
	}
	
	@POST
    @Path("/signup")
	@WebMethod(operationName = "insert")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String addUser(User user) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("Inside sign up resource");
		System.out.println("my dob = "+user.getDate());
		//String output = "POST:Jersey say : " + msg;
		user.setEmailID(user.getEmailID());
		user.setPassword(user.getPassword());
		user.setFname(user.getFname());
		user.setLname(user.getLname());
		user.setDate(user.getDate());
		
		if(s1.checkEmailAlreadyUsed(user.getEmailID()))
		{
			if(s1.addUserService(user))
			{
				
	           String token= createJWT(user.getEmailID());
	           System.out.println("jwt is == "+ token); 
	           //TokenService.set_token(token, user);
	           return token; 
	           // Return the token on the response
	            //return Response.ok(token).build();
				
				
			}

		}
		return null;
	}//adduser method ends here
	
	
	// method to construct a JWT
	private String createJWT(String emailID) {
	 
		String jwt="";
		try {
			jwt = Jwts.builder()
					  .setSubject(emailID)
					  .setExpiration(new Date(1300819380))
					  .claim("shubham", "varun Token Man")
					  .signWith(
					    SignatureAlgorithm.HS256,
					    "secret".getBytes("UTF-8")
					  )
					  .compact();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		return jwt;
	}
	
	
	@POST
    @Path("/login")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String userLogin(User user_obj) throws JsonParseException, JsonMappingException, IOException{
	
		if(LoginService.loginUserService(user_obj))
		{
			System.out.println("retrun id is "+user_obj.getUserID());
			String token= createJWT(user_obj.getEmailID());
	           System.out.println("jwt is == "+ token); 
	           //TokenService.set_token(token, user_obj);
	           return token;
		}
		
		return null;

	}//loginuser method ends here
	
	

	
	
	

	@PUT
    @Path("/updateAllData")
	@WebMethod(operationName = "update")
	@Consumes({MediaType.APPLICATION_JSON})
    public User updateUser(User user,@CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException{
	
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			    System.out.println("Expiration: " + claims.getExpiration());
			  String emailID=claims.getSubject();
		User user1 = new User();
		user1.setEmailID(emailID);
		

		user1.setMob_no(user.getMob_no());
		user1.setCollege(user.getCollege());
		user1.setPlaceOfWork(user.getPlaceOfWork());
		user1.setHometown(user.getHometown());
		user1.setCityOfWork(user.getCityOfWork());
		user1.setHighschool(user.getHighschool());
		user1.setDate(user.getDate());
				if(UpdateService.UpdateUserService(user1))
		{
			System.out.println(user1.getCollege());
			System.out.println(user1.getHighschool());
			return user1;
		}
		return user;
       
	}//Updateuser method ends here
	
	
	
	
    @GET
    @Path("/retrive")
    @Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
    public User retrive(@CookieParam("ID") String jwt) throws JsonParseException, JsonMappingException, IOException 
    {
    	
    	System.out.println("Inside retrive method ");
    	System.out.println("jwt string ="+ jwt);
    	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
    	System.out.println("Subject: " + claims.getSubject());	
		String emailID=claims.getSubject();		
		User u1= RetriveService.getUserAllData(emailID);
	
		return u1;
	 
    }//retrive method ends here
    
    
    @GET
    @Path("/retrive_other")
    @Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
    public User retrive_friendData(@CookieParam("ID1") int userID) throws JsonParseException, JsonMappingException, IOException 
    {
    	
    	System.out.println("friend user id ="+ userID);
    	/*System.out.println("jwt string other="+ femail);
    	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(femail).getBody();
    	System.out.println("Subject: " + claims.getSubject());	
		String emailID=claims.getSubject();		*/
    	User u1=RetriveService.getUserAllDataByUserID(userID);
		
	
		return u1;
	 
    }//retrive other method ends here
    
    
    
    
	
    @GET
    @Path("/search")
    @Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<User> SearchPeople(@CookieParam("ID") String jwt,@CookieParam("key") String key) throws JsonParseException, JsonMappingException, IOException 
    {
    	
    	System.out.println("Inside search method ");
    	System.out.println("jwt string ="+ jwt);
    	Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
    	System.out.println("Subject: " + claims.getSubject());	
		String emailID=claims.getSubject();	
		
		ArrayList<User> u1 = new ArrayList<User>();
		System.out.println("searching "+key);
		if(!key.isEmpty())u1=SearchFriendService.searchFriends(emailID, key);
		else return null;
		return u1;
	 
    }// method ends here
    
    
    @GET
    @Path("/retrivename/{email}")
    @Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
    public User retrivename(@PathParam ("email") String myEmailID) throws JsonParseException, JsonMappingException, IOException 
    {
    	
    	System.out.println("Inside retrive name method ");
    		
		User u1= RetriveNameService.getUserAllData(myEmailID);
	
		return u1;
	 
    }

	
}//class ends here