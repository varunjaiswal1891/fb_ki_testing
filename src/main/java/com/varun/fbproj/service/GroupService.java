package com.varun.fbproj.service;

import java.sql.PreparedStatement;

public class GroupService {

	public static boolean createGroup(String group_name,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for creating group");
	            }
	            String query = "insert into Group1(group_name,owner) values (?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,group_name);
				ps.setString(2,emailID);//it is the emailID of owner 
	            ps.executeUpdate();		
	            
	            
	            //adding owner to other table also
	            String query1 = "insert into UserGroup(group_name,emailID) values (?,?)";
	            PreparedStatement ps1 = connect.con.prepareStatement(query);
	            ps1.setString(1,group_name);
				ps1.setString(2,emailID);//it is the emailID of user being added to the group
	            ps1.executeUpdate();
	            
	            
				connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
	}//create group method ends here
	
	
	public static boolean addUserGroup(String group_name,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for adding people to the group");
	            }
	            String query = "insert into UserGroup(group_name,emailID) values (?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,group_name);
				ps.setString(2,emailID);//it is the emailID of user being added to the group
	            ps.executeUpdate();		
				connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
		
	}//method addUserGroup ends here
	
	
}//class ends
