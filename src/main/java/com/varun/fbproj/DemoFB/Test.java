package com.varun.fbproj.DemoFB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.varun.fbproj.service.DBAccess;

public class Test {

	public static void main(String[] args) {
		 try 
	        {
			 Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/facebook_database","root","diksha1994");
	            
				
	            PreparedStatement prepStatement = con.prepareStatement("select emailID from User");
				
				ResultSet result = prepStatement.executeQuery();
				while(result.next()) {
							String email=result.getString(1);
							
							//System.out.println("YES");
	            //new File("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+email+"/images/").mkdirs();
	            new File("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+email+"/images/").mkdirs();
	            
	        }
	        }
	        catch (Exception e) 
	        {
	            System.out.println(e.getMessage());
	        }

	}

}
