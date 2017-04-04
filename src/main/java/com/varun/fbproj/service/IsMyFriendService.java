package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.varun.fbproj.model.User;
//This fuction is used to check if a particular person is my friend or not
public class IsMyFriendService {

	public static boolean isMyFriend(String emailID1,String emailID2)
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            int i=0;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection in isMyFriendService ");
	            }
	           // "select * from UserFriends where myEmailID="varun@gmail.com" and status="Accepted""
				PreparedStatement prepStatement = connect.con.prepareStatement("select * from UserFriends "
						+ "where myEmailID = ? and friendEmailID=? and status=?" );
				prepStatement.setString(1,emailID1);
				prepStatement.setString(2,emailID2);
				prepStatement.setString(3,"Accepted");
				
				
				ResultSet result = prepStatement.executeQuery();
				
				if (result.next()) {
					System.out.println("yes he is my friend");
					i=1;
					
				}	//if ends	
				connect.stop();
				if(i==1)
					return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return false;
	}
	
}//class ends here
