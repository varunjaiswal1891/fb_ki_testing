package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.User;

public class RetriveService {
	
	
	//this method is to get ALL user data on personal info update page
	public static User getUserAllDataByUserID(int userID)
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
			prepStatement.setInt(1,userID);

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
	
	
	
	//this method is to get ALL user data on personal info update page
	public static User getUserAllData(String emailID)
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
					.prepareStatement("select * from User where emailID = ? ");
			prepStatement.setString(1,emailID);

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
	
	
	public static Group getGroupAllData(String emailID)
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
					.prepareStatement("select * from UserGroup where emailID = ? ");
			prepStatement.setString(1,emailID);

			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					Group g1=new Group();
					g1.setGroup_name(result.getString(1));
					g1.setEmailID(result.getString(2));
					connect.stop();
						return g1;				
					
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