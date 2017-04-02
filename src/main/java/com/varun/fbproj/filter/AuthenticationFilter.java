package com.varun.fbproj.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.security.Key;

import javax.ws.rs.CookieParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
//import org.eclipse.core.resources.ResourcesPlugin;




import java.util.*;
@Provider
public class AuthenticationFilter  implements ContainerRequestFilter{

	
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

		/*IProject project=ResourcesPlugin.getWorkspace().getRoot().getProject("/home/varun/git/fb_ki_testing");
		//IProject project = root.getProject(currentProjectName);
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/	
		
		/*IResource dfile = ResourcesPlugin.getWorkspace().getRoot().getProject("/home/varun/git/fb_ki_testing");
		try {
			dfile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		/*for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()){
		    try {
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				
				e.printStackTrace();
			}
		}*/
		
		// Get the HTTP Authorization header from the request
		String s1=requestContext.getUriInfo().getPath();
		System.out.println("s1="+s1);
        if(!s1.contains("signup")&&!s1.contains("login"))
        {
        	Map<String, Cookie> cookies  = requestContext.getCookies();
            Cookie cookie = cookies.get("ID");
            String token = cookie.getValue();       	
        	System.out.println("NOT signup URL");
        	System.out.println("token varun="+token);
            try {
     
                // Validate the token
         	Claims claims = Jwts.parser()         
     			       .setSigningKey("secret".getBytes("UTF-8"))
     			       .parseClaimsJws(token).getBody();
     			 System.out.println("Subject: " + claims.getSubject());
     			 System.out.println("Expiration: " + claims.getExpiration());
                 System.out.println("#### valid token : ");
     
            } catch (Exception e) {
                System.out.println("#### invalid token");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        	
        }//outer if ends
       
        
            

	}//method ends here
	
	
}//class ends here
