package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.User;

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
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
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

	
	public static ArrayList<String> getMyAllGroups(String emailID,ArrayList<String> al_groups)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for getting all my groups");
	            }
	            String query = "select group_name from UserGroup where emailID=?";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,emailID);
	            
	              ResultSet result = ps.executeQuery();
				
					while (result.next()) {
				
					String e1=result.getString("group_name");
					System.out.println("e1="+e1);
					al_groups.add(e1);
					/*Group g1=new Group();
					g1=RetriveService.getGroupAllData(e1);
					al_groups.add(g1);*/
					
				}
	             	
				connect.stop();
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		System.out.println("Group list="+al_groups);
		return al_groups;
		
	}//method addUserGroup ends here
	
	
}//class ends