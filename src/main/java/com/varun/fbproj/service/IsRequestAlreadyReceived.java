package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class IsRequestAlreadyReceived {
    public static boolean isRequestAlreadyReceived(String emailID1,String emailID2)//This function is used to check if you have received friend request by a particular person

    {
    try {
    	int i=0;
  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	System.out.println("trying connection in isRequestAlreadyreceived for id's"+emailID1+" "+emailID2);
        }
       
			PreparedStatement prepStatement = connect.con.prepareStatement("select * from UserFriends "
					+ "where myEmailID = ? and friendEmailID=? and status=?" );
			prepStatement.setString(1,emailID1);
			prepStatement.setString(2,emailID2);
			prepStatement.setString(3,"Request Received");
			
			
			ResultSet result = prepStatement.executeQuery();
			
			if (result.next()) {
				System.out.println("yes I had already sent the request for id's"+emailID1+" "+emailID2);
				
				return true;
				
			}	//if ends
			connect.stop();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	return false;
	}
}
