package com.varun.fbproj.service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.varun.fbproj.model.Comment;
public class CommentService {


    DBAccess db= new DBAccess();
    
     public boolean addComment(Comment c){//This function is called to when user clicks Comment button ( insert comments into comments table)
            //String result;
            boolean check=false;
            try{
              while(check!=true){
                  System.out.println("trying connection in addComment");
                 check= db.start();
              }
             /* if(c.getTimelineid().equals("home")){
            	 */ String query = "insert into comments(comment_desc,emailID,statusID,group_name) values(?,?,?,?)";
                  java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                      pstmnt.setString(1,c.getComment_desc());
                      pstmnt.setString(2,c.getEmailID());
                      pstmnt.setInt(3,c.getStatusID());
                      pstmnt.setString(4,c.getGroup_name());
                      pstmnt.executeUpdate();
                      db.stop();
                      return true;
             
            	  
            	  
             /* }
              else if(c.getTimelineid().equals("group")){
            	  
            	  String privacy=GroupService.getPrivacy(c.getGroup_name());
            	  if(privacy.equals("public"))
            	  {
            		  String query = "insert into comments(comment_desc,emailID,statusID,group_name) values(?,?,?,?)";
                      java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                          pstmnt.setString(1,c.getComment_desc());
                          pstmnt.setString(2,c.getEmailID());
                          pstmnt.setInt(3,c.getStatusID());
                          pstmnt.setString(4,c.getGroup_name());
                          pstmnt.executeUpdate();
                          db.stop();
                          return true;
                 
            		  
            	  }
            	  else
            	  {
            		  String query = "insert into privategroupcomments(comment_desc,emailID,statusID,group_name) values(?,?,?,?)";
                      java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                          pstmnt.setString(1,c.getComment_desc());
                          pstmnt.setString(2,c.getEmailID());
                          pstmnt.setInt(3,c.getStatusID());
                          pstmnt.setString(4,c.getGroup_name());
                          pstmnt.executeUpdate();
                          db.stop();
                          return true; 
            		  
            	  }
            	  
              }
              else
              {
            	  String query = "insert into comments(comment_desc,emailID,statusID) values(?,?,?)";
                  java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                      pstmnt.setString(1,c.getComment_desc());
                      pstmnt.setString(2,c.getEmailID());
                      pstmnt.setInt(3,c.getStatusID());
                      
                      pstmnt.executeUpdate();
                      db.stop();
                      return true;
              
            	  
            	  
              }
              String query10="select group_name from status where statusID=?";
              java.sql.PreparedStatement pstmnt10 = db.con.prepareStatement(query10);
              pstmnt10.setInt(1,c.getStatusID());
              ResultSet rs50= pstmnt10.executeQuery();
              rs50.next();
              String group_name=rs50.getString("group_name");
              
              if((rs50.getString("group_name"))==null)
              {                 
             String query = "insert into comments(comment_desc,emailID,statusID) values(?,?,?)";
                  java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                      pstmnt.setString(1,c.getComment_desc());
                      pstmnt.setString(2,c.getEmailID());
                      pstmnt.setInt(3,c.getStatusID());
                      pstmnt.executeUpdate();
                      db.stop();
                      return true;
             
              }
              else
              {System.out.print("inside null checker *******************************");
              
              
              //String group_name=c.getGroup_name();
              String query1="select privacy from Group1 where group_name=?";
                 java.sql.PreparedStatement pstmnt1 = db.con.prepareStatement(query1);
                 pstmnt1.setString(1,group_name);
                 ResultSet rs5= pstmnt1.executeQuery();
                 rs5.next();
                 String privacy = rs5.getString("privacy");
                 System.out.println(privacy);
                // int flag=0;
                 if(privacy.equals("public"))
                 {
             String query = "insert into comments(comment_desc,emailID,statusID) values(?,?,?)";
             java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                 pstmnt.setString(1,c.getComment_desc());
                 pstmnt.setString(2,c.getEmailID());
                 pstmnt.setInt(3,c.getStatusID());
                 pstmnt.executeUpdate();
                 db.stop();
                 return true;
              }
                 else
                 {
                     String query = "insert into privategroupcomments(comment_desc,emailID,statusID) values(?,?,?)";
                     java.sql.PreparedStatement pstmnt=db.con.prepareStatement(query);
                         pstmnt.setString(1,c.getComment_desc());
                         pstmnt.setString(2,c.getEmailID());
                         pstmnt.setInt(3,c.getStatusID());
                         pstmnt.executeUpdate();
                         db.stop();
                         return true;
                 }
            }
*/            }
              catch (Exception e) 
              {
                  System.out.println(e.getMessage());
              }
             return false;  
             
    }//add comment method ends here
    
    
}//class ends here
