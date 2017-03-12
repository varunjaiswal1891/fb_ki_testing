package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.varun.fbproj.model.User;

public class SearchFriendService {

	//this method searches for all users in database with given fname or lname
	public static ArrayList<User> searchFriends(ArrayList<User> al_friends,String searchName)
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            
	            String[] splited = searchName.split("\\s+");
	            
	            for(int i=0;i<splited.length;i++)
	            {
	            
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where fname like ? or lname like ?");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ splited[i] +"%");
	            	prepStatement.setString(2,"%"+ splited[i] +"%");
									
					ResultSet result = prepStatement.executeQuery();
					
						while (result.next()) {
						String e1=result.getString("emailID");	
						User u_obj=new User();
						u_obj=RetriveService.getUserAllData(e1);
						/*u_obj.setFname(result.getString("fname"));
						u_obj.setLname(result.getString("lname"));
						u_obj.setUserID(result.getInt("userID"));
						u_obj.setEmailID(result.getString("emailID"));*/
						al_friends.add(u_obj);
						
					}
				  
					
	            }//for ends here
	            

					
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		return al_friends;
	}//method ends here
	public static ArrayList<User> searchFriends(String emailID,String searchName)
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            
	            String[] splited = searchName.split("\\s+");
	            
	            for(int i=0;i<splited.length;i++)
	            {
	            
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where fname like ? or lname like ?");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ splited[i] +"%");
	            	prepStatement.setString(2,"%"+ splited[i] +"%");
									
					ResultSet result = prepStatement.executeQuery();
					ArrayList<User> u1 = new ArrayList<User>();
						while (result.next()) {
						String e1=result.getString("emailID");	
						User u_obj=new User();
						u_obj=RetriveService.getUserAllData(e1);
						if(IsMyFriendService.isMyFriend(emailID, e1))
							{u_obj.setMob_no("1");}
						else
							{u_obj.setMob_no("0");}
						if(!u_obj.getEmailID().equals(emailID))
						{u1.add(u_obj);}
						
					}
						return u1;
				  
					
	            }//for ends here
	            

					
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
return null;
		
		
	}//method ends here
	
	
}//class ends here
