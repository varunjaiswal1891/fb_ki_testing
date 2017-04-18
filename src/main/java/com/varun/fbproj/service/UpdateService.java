package com.varun.fbproj.service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.varun.fbproj.model.User;

public class UpdateService {
//This service is used to update user profile in timeline page
	public static boolean UpdateUserService(User u1)
	{
		System.out.println("lllllllll+"+u1.getGraduateSchool());
		 int count=0;
			StringBuilder sb= new StringBuilder("UPDATE User SET ");
			if(u1.getMob_no().length()>0)
			{	
				sb.append("mob_no='"+u1.getMob_no()+ "',"); 
		        count++;
			}
			if(u1.getCollege().length()>0)
			{	sb.append("college='"+u1.getCollege()+ "',");  count++; }
			if(u1.getPlaceOfWork().length()>0)
			{	sb.append("placeOfWork='"+u1.getPlaceOfWork()+ "',");  count++;  }
			if(u1.getHometown().length()>0)
			{	sb.append("hometown='"+u1.getHometown()+ "',"); count++;}
			if(u1.getCityOfWork().length()>0)
			{	sb.append("cityOfWork='"+u1.getCityOfWork()+ "',"); count++;}
			if(u1.getHighschool().length()>0)
			{	sb.append("highschool='"+u1.getHighschool()+ "',"); count++;}
			if(u1.getGraduateSchool().length()>0)
			{	sb.append("graduateSchool='"+u1.getGraduateSchool()+ "',"); count++;}
			
			sb.append("where emailID='"+u1.getEmailID()+"'");
		    if(count>0){
			   int index= sb.lastIndexOf(",");
		       sb.deleteCharAt(index);
		    }
			String str= sb.toString();
			System.out.println(str);

        try 
        {
        	  DBAccess connect = new DBAccess();
              boolean check=false;
              while(check==false)
              {
              	check=connect.start();
              	System.out.println("trying connection in update");
              }
           
              if(count>0)
                  connect.con.createStatement().executeUpdate(str);  
              
			check=connect.stop();
            return true;
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return false;
    }//update method ends here

	public static String updatePassword(String emailID, String cpassword,
			String npassword) throws SQLException  {
		 DBAccess connect = new DBAccess();
	        boolean check=false;
	        while(check==false)
	        {
	        	check=connect.start();
	        	System.out.println("trying connection for Event service");
	        }
	        
	    String query1="select password from User where emailID=?";
	    PreparedStatement ps1 = connect.con.prepareStatement(query1);  
	    ps1.setString(1,emailID);
		ResultSet rs=ps1.executeQuery();
		while(rs.next()){
			String pass=rs.getString(1);
			if(pass.equals(cpassword))
			{
				String query = "UPDATE User SET password=? where emailID=?";
				ps1 = connect.con.prepareStatement(query);
				ps1.setString(1,npassword);
				ps1.setString(2, emailID);
				ps1.executeUpdate();
			}
			else {
				connect.stop();
				return null;
			}
			
		}
	       //String query = "UPDATE Event SET summary=? where eID=? and userID=?";
	      
	       
				
			
	        connect.stop();
		return "changed";
	}
	
	
}//class ends here
