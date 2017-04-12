package com.varun.fbproj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.UploadContext;

import com.varun.fbproj.model.User;
import com.varun.fbproj.resource.Pathuse;
public class SignUpService {
	
	public boolean checkEmailAlreadyUsed(String email1)
	{
		//checking if email already exists.
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
		/*creating a account for user and creating a creating a unique user id for him as well.
		creates a folder for user to save pictures uploaded by him.
		*/
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
           
          /* Properties prop=new Properties();
           String propFileName = "config.property";
           InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
           prop.load(inputStream);
           String userName = prop.getProperty("DataFilePath");*/
            String userName=Pathuse.USER_PATH;
            new File(userName+u1.getEmailID()+"/images/").mkdirs();
           FileInputStream fis = new FileInputStream(new File(userName+"fb_default.jpg"));
       UserImageService uis = new UserImageService();
       uis.uploadProfilePic(fis, "blah", "blah", u1.getEmailID());
           
           

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