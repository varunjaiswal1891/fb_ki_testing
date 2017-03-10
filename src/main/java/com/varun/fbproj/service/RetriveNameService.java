package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.User;

public class RetriveNameService {
	//this method is to get ALL user data on personal info update page
	public static User getUserAllData(String emailID)
	{
		
		
		//System.out.println("dkmsdlkmsmksdmcksdmcksdmcsdmc");
		
		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection");
            }
			PreparedStatement prepStatement = connect.con
					.prepareStatement("select * from User where emailID = ? ");
			prepStatement.setString(1,emailID);
			
			//System.out.println("email id is: "+emailID);
			
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					User u = new User();
					u.setUserID(result.getInt(1));
					u.setEmailID(result.getString(2));
					u.setFname(result.getString(4));
					u.setLname(result.getString(5));
					u.setDate(result.getString(6));
					u.setMob_no(result.getString(8));		
					u.setCollege(result.getString(9));
					u.setPlaceOfWork(result.getString(10));
					u.setHometown(result.getString(11));
					u.setCityOfWork(result.getString(12));
					u.setHighschool(result.getString(13));
					
					//System.out.println("inside --------------------------------------- service: "+u.getFname());
					
					connect.stop();
						return u;
						//System.out.println("YES");
					
					
					
					
				}
			}
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;
        
    }//method ends here
	
}