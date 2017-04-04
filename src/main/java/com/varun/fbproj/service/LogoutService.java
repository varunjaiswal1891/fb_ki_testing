package com.varun.fbproj.service;

import java.sql.PreparedStatement;

public class LogoutService {
	
	public static boolean logoutUserService(String emailID)
	{
		
		
		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection for logout");
            }
            /*delete from User_token where emailID="shubh@yahoo.com"*/
            
            String sql = "DELETE FROM User_token WHERE emailID=?";
            
            PreparedStatement statement = connect.con.prepareStatement(sql);
            statement.setString(1,emailID);
             
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
                return true;
            }
         

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return false;
        
    }//method ends here

}
