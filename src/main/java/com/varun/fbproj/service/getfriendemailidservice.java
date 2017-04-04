package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.User;

public class getfriendemailidservice {
	
	
	//this method is to get ALL user data on personal info update page
	public static String getfriendemailid(int uid)
	{
		
		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection");
            }
			PreparedStatement prepStatement = connect.con
					.prepareStatement("select * from User where userID = ? ");
			prepStatement.setInt(1,uid);

			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
				
					return result.getString("emailID");
					
									}
			}
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;
        
    }//method ends here
	
	
	
	
}//class ends here