package com.varun.fbproj.service;

import java.sql.PreparedStatement;

import com.varun.fbproj.model.Comment;

public class CommentService {

	DBAccess db= new DBAccess();
	
	 public boolean addComment(Comment c){
	        //String result;
			boolean check=false;
			try{
			  while(check!=true){
				  System.out.println("trying connection in addComment");
				 check= db.start();
			  }
			  
			 String query = "insert into comments(comment_desc,emailID,statusID) values(?,?,?)";
		     PreparedStatement pstmnt=db.con.prepareStatement(query);
				 pstmnt.setString(1,c.getComment_desc());
				 pstmnt.setString(2,c.getEmailID());
				 pstmnt.setInt(3,c.getStatusID());
				 pstmnt.executeUpdate();
				 db.stop();
				 return true;
			  }
			  catch (Exception e) 
		      {
		          System.out.println(e.getMessage());
		      }
			 return false;  
			 
    }//add comment method ends here
	
	
}//class ends here
