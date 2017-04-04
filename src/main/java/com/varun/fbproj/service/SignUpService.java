package com.varun.fbproj.service;

import java.io.File;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.varun.fbproj.model.User;
public class SignUpService {
	
	public boolean checkEmailAlreadyUsed(String email1)
	{
		
        try 
        {
            DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection");
            }
           
            PreparedStatement prepStatement = connect.con.prepareStatement("select userID from User where emailID = ? ");
			prepStatement.setString(1,email1);
			ResultSet result = prepStatement.executeQuery();
			
			if (!result.next())
			{     //if result.next() returns false then there are no rows.
                     System.out.println("No records found");
                     return true;
             }
			else
			{
				System.out.println("Record found");
				return false;
			}
			
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
		
		return false;
	}//method ends here
	

	public boolean addUserService(User u1)
	{
		
        try 
        {
            DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection");
            }
            String query = "insert into User(emailID,password,fname,lname,dob) values (?,?,?,?,?)";
            PreparedStatement ps = connect.con.prepareStatement(query);
           
			ps.setString(1,u1.getEmailID());
			ps.setString(2,u1.getPassword());
			ps.setString(3, u1.getFname());
			ps.setString(4, u1.getLname());
			ps.setString(5, u1.getDate());
            ps.executeUpdate();
            
            PreparedStatement prepStatement = connect.con.prepareStatement("select userID from User where emailID = ? ");
			prepStatement.setString(1,u1.getEmailID());
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				if(result.next()){
						u1.setUserID(result.getInt(1));
						System.out.println("SignUp success");
						
						//System.out.println("YES");
            check=connect.stop();
            //new File("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+u1.getEmailID()+"/images/").mkdirs();
            //new File("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+u1.getEmailID()+"/groups/").mkdirs();
        //    new File("/home/varun/src/main/webapp/users/"+u1.getEmailID()+"/images/").mkdirs();
            
            System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuggggg");
           // String file = new File("git").getAbsolutePath();
            //System.out.println(file+"/main/webapp/users/"+u1.getEmailID()+"/images/");
           //System.out.println(new File(file+"/main/webapp/users/"+u1.getEmailID()+"/images/").mkdir());
           
           Properties prop=new Properties();
           String propFileName = "config.property";
           InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
           prop.load(inputStream);
           String userName = prop.getProperty("DataFilePath");
           new File(userName+u1.getEmailID()+"/images/").mkdirs();
           
           
           

            return true;
        }}
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return false;
    }//method ends here
	
}//class ends here