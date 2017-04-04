package com.varun.fbproj.service;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

//this function is used to get emailid of a person
public class getEmailId {
	public String getemailId(String token)
    {
  	  String email="";
  	  try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            
	            PreparedStatement prepStatement = connect.con.prepareStatement("select emailId from User_token where token_value=?");
	            prepStatement.setString(1,token);
	            ResultSet result = prepStatement.executeQuery();
	            if(result.next())
	            {
	            	email=result.getString("emailId");
	            }
	            return email;
  	  	}
  	  catch (Exception e) {
				e.printStackTrace();
			}
  	  return email;
}
}
