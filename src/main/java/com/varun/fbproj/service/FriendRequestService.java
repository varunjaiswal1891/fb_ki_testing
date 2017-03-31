package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.varun.fbproj.model.SuggestUser;
import com.varun.fbproj.model.User;

public class FriendRequestService {

	public static boolean addFriendRequest(String myEmailID,String friendEmailID)
	{
		//this method sends friend request to friendEmailID
		System.out.println("myEmailID="+myEmailID);
		System.out.println("friendEmailID="+friendEmailID);
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for adding friends");
	            }
	            
	            
	            String query = "insert into UserFriends(myEmailID,friendEmailID,status) values (?,?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,myEmailID);
				ps.setString(2,friendEmailID);
				ps.setString(3,"Request Sent");
	            
	            ps.executeUpdate();
	            String queryReceived= "insert into UserFriends(myEmailID,friendEmailID,status) values (?,?,?)"; 
	            PreparedStatement psreceived= connect.con.prepareStatement(queryReceived);
	            psreceived.setString(1,friendEmailID);
	            psreceived.setString(2,myEmailID);
				
				psreceived.setString(3,"Request Received");
				 psreceived.executeUpdate();
	            connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return false;
		
	}//method ends here
	public static boolean addFriendSuggestion(SuggestUser su)
	{
		//this method sends friend request to friendEmailID
		System.out.println("firstEmailID="+su.getFirstemail());
		System.out.println("secondEmailID="+su.getSecondemail());
		System.out.println("thirdEmailID="+su.getSuggestemail());
		
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for adding friends");
	            }
	            String query = "insert into UserFriends(myEmailID,friendEmailID,status) values (?,?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,su.getSecondemail());
				ps.setString(2,su.getSuggestemail());
				ps.setString(3,"Request Sent");
	            
	            ps.executeUpdate();
	            String queryReceived= "insert into UserFriends(myEmailID,friendEmailID,status) values (?,?,?)"; 
	            PreparedStatement psreceived= connect.con.prepareStatement(queryReceived);
	            psreceived.setString(1,su.getSuggestemail());
	            psreceived.setString(2,su.getSecondemail());
				
				psreceived.setString(3,"Request Received");
				 psreceived.executeUpdate();
	            
	            
	            String query1 = "delete from FriendSuggestion where firstp=? and secondp=? and suggestedp=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,su.getFirstemail());
				ps1.setString(2,su.getSecondemail());
				ps1.setString(3,su.getSuggestemail());
	            
	            ps1.executeUpdate();
	            
	            connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return false;
		
	}//method ends here
	
	
	public static void removeFriendSuggestion(String myEmailID,String friendEmailID)
	{
		//this method removes friend suggested 
	}
	
	public static void updateOldRequest(String myEmailID,String friendEmailID)
	{
		//this method removes friend suggested 
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for updating friend request");
	            }
	            
	            String query = "UPDATE UserFriends SET status=? where myEmailID=? and friendEmailID=?";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,"Accepted");
				ps.setString(2,friendEmailID);
				ps.setString(3,myEmailID);	
				ps.executeUpdate();
				
	            connect.stop();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	}//method ends
	public static boolean confirmFriendRequest(String myEmailID,String friendEmailID)
	{
		//this method confirms friend request came to me 
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for confriming friend request");
	            }
	            
	           // String query1 = "insert into UserFriends(myEmailID,friendEmailID,status) values (?,?,?)";
	            String query1= "update UserFriends set status=? where myEmailID=? and friendEmailID=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,"Accepted");
	            ps1.setString(2,myEmailID);
				ps1.setString(3,friendEmailID);
	            ps1.executeUpdate();	
	            connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return false;
		
	} //method ends here
	
	
	public static boolean deleteFriendRequest(String myEmailID,String friendEmailID)
	{
		//this method deletes friend request came to me 
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for deleting request");
	            }
	          
	            
	            String sql = "DELETE FROM UserFriends WHERE myEmailID=? and friendEmailID=?";
	            
	            PreparedStatement statement = connect.con.prepareStatement(sql);
	            statement.setString(1,friendEmailID);
	            statement.setString(2,myEmailID);
	             
	            int rowsDeleted = statement.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("friend Request was deleted successfully!");
	                return true;
	            }
	            

			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return false;
		
	}
	
	public static boolean removeFriendRequest(String myEmailID,String suggestEmailID)
	{
		//this method removes a friend suggestion that came to me
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for removing request");
	            	
	            }
	          
	            System.out.println("My email id: "+myEmailID);
            	System.out.println("Suggest email id: "+suggestEmailID);
            	
	            String sql = "DELETE FROM FriendSuggestion WHERE secondp=? and suggestedp=?";
	            
	            PreparedStatement statement = connect.con.prepareStatement(sql);
	            statement.setString(1,myEmailID);
	            statement.setString(2,suggestEmailID);
	             
	            int rowsDeleted = statement.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("suggest Request was removed successfully!");
	                return true;
	            }
	            

			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return false;
		
	}
	
	public static ArrayList<User> getAllFriendRequest(String myEmailID,ArrayList<User> al_requests)
	{
		//this method returns all friend requests came to me 
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection in getAllFriendrequest");
	            }
	       
				PreparedStatement prepStatement = connect.con.prepareStatement("select * from UserFriends "
						+ "where friendEmailID = ? and status=?");
				prepStatement.setString(1,myEmailID);
				prepStatement.setString(2,"Request Sent");
				
				ResultSet result = prepStatement.executeQuery();
				
					while (result.next()) {
				
					String e1=result.getString("myEmailID");	
					User u_obj=new User();	
					u_obj=RetriveService.getUserAllData(e1);
					System.out.println("Request Came from :"+u_obj.getEmailID());
					//adding user objects of my friends to arraylist
					al_requests.add(u_obj);
					
				
			  }	//if ends	
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return al_requests;
	}
	
}//class ends here