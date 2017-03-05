package com.varun.fbproj.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.User;
import com.varun.fbproj.service.GroupService;

@WebService()
@Path("/group")
public class GroupResource {
	
	@POST
    @Path("/create_group")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public String createGroup(@CookieParam("ID") String jwt,String grpName) throws IOException{
	
		System.out.println("jwt="+ jwt);
		Claims claims = Jwts.parser()         
			       .setSigningKey("secret".getBytes("UTF-8"))
			       .parseClaimsJws(jwt).getBody();
			    System.out.println("Subject: " + claims.getSubject());
			  String myEmailID=claims.getSubject();
		if(GroupService.createGroup(grpName, myEmailID))
		{
			return "group created";
		}
		
		return "group not created";
	}//method create group ends here
	
	@POST
    @Path("/addUser_group")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String addUserToGroup(Group g1) throws JsonParseException, JsonMappingException, IOException{
	
		String group_name=g1.getGroup_name();
		String emailID=g1.getEmailID();
		if(GroupService.addUserGroup(group_name, emailID))
		{
			return "user added in group";
		}
		
		return "user NOT added in group";
	}//method add user group ends here
	
	

}//class ends
