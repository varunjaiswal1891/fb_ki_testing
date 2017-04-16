package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.varun.fbproj.model.Likes;

public class LikeService {

	DBAccess db= new DBAccess();
	//This function is called when user clicks like button(it increments no of likes in likes table)
	public int incrementLike(Likes likeobj){
		boolean check=false;

	    try{
			  while(check!=true){
				 System.out.println("trying connection in LikeIncrement");
				 check= db.start();
				 
			  }
			  
			/*  if(likeobj.getTimelineid().equals("home"))
			  {*/
				  String query1="select * from likes where statusID=? and emailID=?"; 
					
					PreparedStatement pstmnt1=db.con.prepareStatement(query1);
					
					pstmnt1.setInt(1,likeobj.getStatusID());
					pstmnt1.setString(2,likeobj.getEmailID());
					ResultSet rs1= pstmnt1.executeQuery();
						 rs1.last();
						 int rows = rs1.getRow();
						 rs1.beforeFirst();
				    if(rows==0){
				    	System.out.println("Inside likeIncrement service and inserting a like entry");
				    	String query2= "insert into likes(statusID,emailID,flag)values(?,?,?)";
				    	PreparedStatement pstmnt2=db.con.prepareStatement(query2);
				    	pstmnt2.setInt(1,likeobj.getStatusID());
				    	pstmnt2.setString(2, likeobj.getEmailID());
				    	pstmnt2.setInt(3, 1);
				    	pstmnt2.executeUpdate();
				    	 db.stop();
				    	 //System.out.println("cnkjancakcmackmd");
				    	 return 1;
				    	
				    }
				    else{
				    	System.out.println("Inside likeIncrement service and updating like");
				    	
				    	
				    		String query3= "delete from likes where statusID=? and emailID=?";

					    	PreparedStatement pstmnt3=db.con.prepareStatement(query3);
					    	pstmnt3.setInt(1,likeobj.getStatusID());
					    	pstmnt3.setString(2,likeobj.getEmailID());
					    	pstmnt3.executeUpdate();
					    	 db.stop();
				    	
				    	
				    	 return 2;
				    }
					 
				  
				  
			 /* }
			  else{ 
				  if(likeobj.getTimelineid().equals("group")){
				  
					  String privacy=GroupService.getPrivacy(likeobj.getGroup_name());
	            	  if(privacy.equals("public"))
	            	  {
	            		  String query1="select * from likes where statusID=? and emailID=?"; 
	  					
	  					PreparedStatement pstmnt1=db.con.prepareStatement(query1);
	  					
	  					pstmnt1.setInt(1,likeobj.getStatusID());
	  					pstmnt1.setString(2,likeobj.getEmailID());
	  					ResultSet rs1= pstmnt1.executeQuery();
	  						 rs1.last();
	  						 int rows = rs1.getRow();
	  						 rs1.beforeFirst();
	  				    if(rows==0){
	  				    	System.out.println("Inside likeIncrement service and inserting a like entry");
	  				    	String query2= "insert into likes(statusID,emailID,flag)values(?,?,?)";
	  				    	PreparedStatement pstmnt2=db.con.prepareStatement(query2);
	  				    	pstmnt2.setInt(1,likeobj.getStatusID());
	  				    	pstmnt2.setString(2, likeobj.getEmailID());
	  				    	pstmnt2.setInt(3, 1);
	  				    	pstmnt2.executeUpdate();
	  				    	 db.stop();
	  				    	 //System.out.println("cnkjancakcmackmd");
	  				    	 return 1;
	  				    	
	  				    }
	  				    else{
	  				    	System.out.println("Inside likeIncrement service and updating like");
	  				    	
	  				    	
	  				    		String query3= "delete from likes where statusID=? and emailID=?";

	  					    	PreparedStatement pstmnt3=db.con.prepareStatement(query3);
	  					    	pstmnt3.setInt(1,likeobj.getStatusID());
	  					    	pstmnt3.setString(2,likeobj.getEmailID());
	  					    	pstmnt3.executeUpdate();
	  					    	 db.stop();
	  				    	
	  				    	
	  				    	 return 2;
	  				    }
	  					 
	  				    
	            	  }
	            	  
	            	  else{
					  String query1="select * from privategrouplikes where statusID=? and emailID=?"; 
						
						PreparedStatement pstmnt1=db.con.prepareStatement(query1);
						
						pstmnt1.setInt(1,likeobj.getStatusID());
						pstmnt1.setString(2,likeobj.getEmailID());
						ResultSet rs1= pstmnt1.executeQuery();
							 rs1.last();
							 int rows = rs1.getRow();
							 rs1.beforeFirst();
					    if(rows==0){
					    	System.out.println("Inside likeIncrement service and inserting a like entry");
					    	String query2= "insert into privategrouplikes(statusID,emailID,flag)values(?,?,?)";
					    	PreparedStatement pstmnt2=db.con.prepareStatement(query2);
					    	pstmnt2.setInt(1,likeobj.getStatusID());
					    	pstmnt2.setString(2, likeobj.getEmailID());
					    	pstmnt2.setInt(3, 1);
					    	pstmnt2.executeUpdate();
					    	 db.stop();
					    	 //System.out.println("cnkjancakcmackmd");
					    	 return 1;
					    	
					    }
					    else{
					    	System.out.println("Inside privatelikeIncrement service and updating like");
					    	
					    	
					    		String query3= "delete from privategrouplikes where statusID=? and emailID=?";

						    	PreparedStatement pstmnt3=db.con.prepareStatement(query3);
						    	pstmnt3.setInt(1,likeobj.getStatusID());
						    	pstmnt3.setString(2,likeobj.getEmailID());
						    	pstmnt3.executeUpdate();
						    	 db.stop();
					    	
					    	
					    	 return 2;
					    }
						 
				  
	            	  }
			  }

			  }	  
				  
				  
				  
				  
				  
			  
			  
			String query1="select * from likes where statusID=? and emailID=?"; 
			
			PreparedStatement pstmnt1=db.con.prepareStatement(query1);
			
			pstmnt1.setInt(1,likeobj.getStatusID());
			pstmnt1.setString(2,likeobj.getEmailID());
			ResultSet rs1= pstmnt1.executeQuery();
				 rs1.last();
				 int rows = rs1.getRow();
				 rs1.beforeFirst();
		    if(rows==0){
		    	System.out.println("Inside likeIncrement service and inserting a like entry");
		    	String query2= "insert into likes(statusID,emailID,flag)values(?,?,?)";
		    	PreparedStatement pstmnt2=db.con.prepareStatement(query2);
		    	pstmnt2.setInt(1,likeobj.getStatusID());
		    	pstmnt2.setString(2, likeobj.getEmailID());
		    	pstmnt2.setInt(3, 1);
		    	pstmnt2.executeUpdate();
		    	 db.stop();
		    	 //System.out.println("cnkjancakcmackmd");
		    	 return 1;
		    	
		    }
		    else{
		    	System.out.println("Inside likeIncrement service and updating like");
		    	
		    	
		    		String query3= "delete from likes where statusID=? and emailID=?";

			    	PreparedStatement pstmnt3=db.con.prepareStatement(query3);
			    	pstmnt3.setInt(1,likeobj.getStatusID());
			    	pstmnt3.setString(2,likeobj.getEmailID());
			    	pstmnt3.executeUpdate();
			    	 db.stop();
		    	
		    	
		    	 return 2;
		    }
			 
*/		} catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
		 return 0;
	}//increment like method ends here

	
	
	
	public int decrementLike(Likes likeobj){
		boolean check=false;

	    try{
			  while(check!=true){
				 System.out.println("trying connection in LikeIncrement");
				 check= db.start();
				 
			  }
			  
			String query1="select * from likes where statusID=? and emailID=?"; 
			
			PreparedStatement pstmnt1=db.con.prepareStatement(query1);
						pstmnt1.setInt(1,likeobj.getStatusID());
			pstmnt1.setString(2,likeobj.getEmailID());
			ResultSet rs1= pstmnt1.executeQuery();
				 rs1.last();
				 int rows = rs1.getRow();
				 rs1.beforeFirst();
		    if(rows==0){
		    	System.out.println("Inside decrementlike service and inserting a unlike entry");
		    	String query2= "insert into likes(statusID,emailID,flag)values(?,?,?)";
		    	PreparedStatement pstmnt2=db.con.prepareStatement(query2);
		    	pstmnt2.setInt(1,likeobj.getStatusID());
		    	pstmnt2.setString(2, likeobj.getEmailID());
		    	pstmnt2.setInt(3, 0);
		    	pstmnt2.executeUpdate();
		    	 db.stop();
		    	 //System.out.println("cnkjancakcmackmd");
		    	 return 1;
		    	
		    }
		    else{
		    	System.out.println("Inside decrementlike service and updating a unlike entry ");
		    	
		    	rs1.next();
		    	int fl = rs1.getInt("flag");
		    	if(fl==1){
		    		String query3= "delete from likes where statusID=? and emailID=?";	
		    	
		    	PreparedStatement pstmnt3=db.con.prepareStatement(query3);
		    	pstmnt3.setInt(1,likeobj.getStatusID());
		    	pstmnt3.setString(2,likeobj.getEmailID());
		    	pstmnt3.executeUpdate();
		    	 db.stop();
		    	}
		    	else{
		    		System.out.println("NO NEED TO UNLIKE!");
		    	}
		    	 return 1;
		    }
			 
		} catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
		 return 0;
	}//increment like method ends here

	
	
	
	
	
	
	
}//class ends
