package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.varun.fbproj.model.User;

public class GetMyAllFriends {

	public static ArrayList<User> getMyFriends(ArrayList<User> al_friends,String myEmailID) //here myEmailID is fetched from jwt token
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	           
				PreparedStatement prepStatement = connect.con.prepareStatement("select friendEmailID from UserFriends "
						+ "where myEmailID = ? and status=?");
				prepStatement.setString(1,myEmailID);
				prepStatement.setString(2,"Accepted");
				
				ResultSet result = prepStatement.executeQuery();
				
					while (result.next()) {
				
					String e1=result.getString("friendEmailID");	
					User u_obj=new User();	
					u_obj=RetriveService.getUserAllData(e1);				
					
					al_friends.add(u_obj);
					
				}
			  	
					connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}

		System.out.println("Friend list="+al_friends);
		return al_friends;
	}
	
	public static ArrayList<User> getMyFriendsuggestion(ArrayList<User> al_friends,String myEmailID,String group_name) //here myEmailID is fetched from jwt token
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            
	            ArrayList<String> al_friends1=new ArrayList<String>();
	           
	            PreparedStatement prepStatement1 = connect.con.prepareStatement("select emailID from UserGroup "
						+ "where group_name = ? ");
				prepStatement1.setString(1,group_name);
				ResultSet result1 = prepStatement1.executeQuery();					
					while(result1.next())
					{
						String e1=result1.getString("emailID");
						al_friends1.add(e1);
					}
					
				PreparedStatement prepStatement = connect.con.prepareStatement("select friendEmailID from UserFriends "
						+ "where myEmailID = ? and status=?");
				prepStatement.setString(1,myEmailID);
				prepStatement.setString(2,"Accepted");
				
				ResultSet result = prepStatement.executeQuery();
					
					while (result.next()) {
				
					String e1=result.getString("friendEmailID");
					if(al_friends1.contains(e1))
					{
						//do nothing
					}
					else{
					User u_obj=new User();	
					u_obj=RetriveService.getUserAllData(e1);				
					//adding user objects of my friends to arraylist
					al_friends.add(u_obj);
					}
				}
					
						
						
			  	
			} catch (Exception e) {
				e.printStackTrace();
			}

		System.out.println("Friend list="+al_friends);
		return al_friends;
	}
	//This function is used to get friendlist of a particular person
	public static ArrayList<User> getMyFriends(ArrayList<User> al_friends, String myEmailID,
			String myEmailID1) {
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	           
				PreparedStatement prepStatement = connect.con.prepareStatement("select friendEmailID from UserFriends "
						+ "where myEmailID = ? and status=? and friendEmailID <> ? ");
				prepStatement.setString(1,myEmailID);
				prepStatement.setString(2,"Accepted");
				prepStatement.setString(3,myEmailID1);
				
				ResultSet result = prepStatement.executeQuery();
				
					while (result.next()) {
				
					String e1=result.getString("friendEmailID");	
					User u_obj=new User();	
					u_obj=RetriveService.getUserAllData(e1);				
					//adding user objects of my friends to arraylist
					al_friends.add(u_obj);
					
				}
			  	
			} catch (Exception e) {
				e.printStackTrace();
			}

		System.out.println("Friend list="+al_friends);
		return al_friends;
	}
	
	
}//class ends here
