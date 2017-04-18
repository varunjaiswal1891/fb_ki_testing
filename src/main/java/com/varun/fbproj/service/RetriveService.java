package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
					System.out.println();
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
					u.setGraduateSchool(result.getString(14));
					connect.stop();
						return u;
						
				
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
					u.setGraduateSchool(result.getString(14));
					
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
	
	
	public static Group getGroupAllData(String group_name)
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
					.prepareStatement("select * from Group1 where group_name = ? ");
			prepStatement.setString(1,group_name);

			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					Group g1=new Group();
					g1.setGroup_name(result.getString(1));
					g1.setOwner(result.getString(2));
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
	
	
	public static int uidfromEmailID(String myEmailID) {

		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection");
            }
            PreparedStatement prepStatement = connect.con
					.prepareStatement("select userID from User where emailID = ? ");
			prepStatement.setString(1,myEmailID);
			
			
			ResultSet result = prepStatement.executeQuery();
			int id=-1;
			if(result!=null){
			if(result.next())
		    id =result.getInt(1);
			  connect.stop();
			}
			else {
				connect.stop();
				return 0;
			}
            connect.stop();
            return id;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public static String emailIDfromuID(int userID) {
		String email="";
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            PreparedStatement prepStatement = connect.con
						.prepareStatement("select emailID from User where userID = ? ");
				prepStatement.setInt(1,userID);
				
				
				ResultSet result = prepStatement.executeQuery();
			
				if(result!=null){
				if(result.next())
			    email =result.getString(1);
				  connect.stop();
				}
				else {
					connect.stop();
					return "";
				}
	            connect.stop();
	            
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return email;
	}



	public static ArrayList<User> getAllUserDetail() {
	  ArrayList<User> ais= new ArrayList<User>();
	  try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection");
            }
            PreparedStatement prepStatement = connect.con
					.prepareStatement("select emailID,password from User");
			
			
			ResultSet result = prepStatement.executeQuery();
		
			while(result.next()){
				User obj= new User();
			obj.setEmailID(result.getString("emailID"));
			obj.setPassword(result.getString(2));
				ais.add(obj);
			}
			
            connect.stop();
            
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ais;
	}

	
	
	
}//class ends here