package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.Status;
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

	
	public static ArrayList<Group> getMyAllGroups(String emailID,ArrayList<Group> al_groups)
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
					//al_groups.add(e1);
					Group g1=new Group();
					g1=RetriveService.getGroupAllData(e1);
					al_groups.add(g1);
					
				}
	             	
				connect.stop();
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		System.out.println("Group list="+al_groups);
		return al_groups;
		
	}//method addUserGroup ends here

	
	
	public static boolean deleteGroup(String group_name,String myEmailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for deleting group");
	            }
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(e1.equals(myEmailID))  //he is the owner so delete group
				 {
					 
					 String query3="delete from UserGroup where group_name=?";
			            PreparedStatement ps3 = connect.con.prepareStatement(query3);
			            ps3.setString(1,group_name);
			            int y= ps3.executeUpdate();
					 
					 if(y>0)
					 {
					 
					 String query2="delete from Group1 where group_name=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,group_name);
			            int x=  ps2.executeUpdate();
			            if(x>0)
			            {
			            	System.out.println(" one Group deleted successfully ");
			            	return true;
			            }
					 } 
				 }
				 else
				 {
					 System.out.println("You are not admin ... so cannot delete group");
					 return false;
				 }
				
			}//while ends
	            
	            		
				connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
		  }
		return false;
		
	}//method deleteGroup ends here


	
	public static boolean deleteUserFromGroup(String group_name,String owner,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for deleting people from the group");
	            }
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(e1.equals(owner))  //he is the owner so delete the user
				 {
					 
					 String query2="delete from UserGroup where emailID=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,emailID);
			            int x= ps2.executeUpdate();
			            if(x>0)
			            {
			            	System.out.println(" one user deleted  from successfully ");
			            	return true;
			            }
				 }
				 else
				 {
					 System.out.println("You are not admin ... so cannot delete group");
					 return false;
				 }
				
			}//while ends
	            
	            		
				connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
		  }
		return false;
		
	}//method delete user from Group ends here
	
	
	 public  static ArrayList<Status> getStatusByGroup(String group_name){
    
		 String result;
		 DBAccess db= new DBAccess();
	boolean check=false;
	try{
	  while(check!=true){
		  System.out.println("trying connection in getStatusByUser");
		 check= db.start();
	  }
	  String sql="select * from status where group_name= ?";
	 
	  PreparedStatement pstmnt=db.con.prepareStatement(sql);
	  pstmnt.setString(1,group_name); // user_id is the one sent in paramater
	  ResultSet rs= pstmnt.executeQuery();
	  ArrayList<Status> status_list= new ArrayList<Status>();
	  if(rs!=null){
		  
		  while (rs.next()) {
				Status status_obj = new Status();
				status_obj.setStatusID(rs.getInt(1));
				status_obj.setStatus_desc(rs.getString(2));
				status_obj.setCreated(String.valueOf(rs.getTimestamp(3)));
				status_obj.setEmailID(rs.getString(4));
				status_obj.setFlag(rs.getInt(5));
				status_obj.setGroup_name(rs.getString(6));
				status_list.add(status_obj);	
			}
	  }
	  else{
		  System.out.println("resultset empty");
	  }
	  db.stop();
	  return status_list;
	}
	catch(Exception e){
		
	}
	
	 return  null;
 }//method getStatusByGroup ends here

	
public  static ArrayList<User> getGroupMembers(String group_name){
		    
		 String result;
		 DBAccess db= new DBAccess();
    	boolean check=false;
	  try{
	      while(check!=true){
		  System.out.println("trying connection in get all members of group");
		  check= db.start();
	  }
	  String sql="select emailID from UserGroup where group_name= ?";
	 
	  PreparedStatement pstmnt=db.con.prepareStatement(sql);
	  pstmnt.setString(1,group_name); 
	  ResultSet rs= pstmnt.executeQuery();
	  ArrayList<User> user_list= new ArrayList<User>();
	  if(rs!=null){
		  
		  while (rs.next()) {
			  User u1=new User();
			  String e1=rs.getString("emailID");
			  u1=RetriveService.getUserAllData(e1);
			  user_list.add(u1);
		
			}
	  }
	  else{
		  System.out.println("resultset empty");
	  }
	  db.stop();
	  return user_list;
	}
	catch(Exception e){
		
	}
	
	 return  null;
 }//method get all members of Group ends here

	
	
}//class ends