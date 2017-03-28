package com.varun.fbproj.service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.varun.fbproj.model.Chat;

public class ChatService {

	static DBAccess db = new DBAccess();
	public static ArrayList<Chat> getAll(Chat obj)
	{
		boolean check=false;
	
	    try{
			  while(check!=true){
				 
				 check= db.start();
			  }
	    
			  String query1="select * from chat where (senderEmail= ? AND receiverEmail= ? ) OR (senderEmail= ? AND receiverEmail= ? )";	   
			  PreparedStatement pstmnt=db.con.prepareStatement(query1);
			  pstmnt.setString(1,obj.getSenderEmail()); 
			  pstmnt.setString(2,obj.getReceiverEmail());
			  pstmnt.setString(3,obj.getReceiverEmail()); 
			  pstmnt.setString(4,obj.getSenderEmail()); 
			
			  ResultSet rs= pstmnt.executeQuery();
			  ArrayList<Chat> ch = new ArrayList<Chat>();
			  while (rs.next()) {
					Chat c = new Chat();
					c.setSenderEmail(rs.getString(2));
					c.setReceiverEmail(rs.getString(3));
					c.setMessage(rs.getString(5));
					ch.add(c);
					  String query2="update chat set status='Delivered' where senderEmail=? AND receiverEmail=? AND message=?";	   
					  PreparedStatement ps=db.con.prepareStatement(query2);
					  ps.setString(1,c.getSenderEmail()); 
					  ps.setString(2,c.getReceiverEmail());
					  ps.setString(3,c.getMessage()); 
					
					  ps.executeUpdate();
				}
			  db.stop();
			  return ch;
			  

	    	  }
	    catch(Exception e)
	    {
		System.out.println(e);
		db.stop();
		return null;
	    }
	}
	    public static ArrayList<Chat> fetchNew(Chat c)
		{
			boolean check=false;
		
		    try{
				  while(check!=true){
				
					 check= db.start();
				  }
				  String query1="select * from chat where senderEmail= ? AND receiverEmail= ?  AND status= ?";	   
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,c.getSenderEmail());
				  pstmnt.setString(2,c.getReceiverEmail()); 
				  pstmnt.setString(3,c.getStatus());
				   
				  System.out.println("this is done");
				  ResultSet rs= pstmnt.executeQuery();
				  ArrayList<Chat> ch = new ArrayList<Chat>();
				  while (rs.next()) {
						Chat c1 = new Chat();
						c1.setSenderEmail(rs.getString(2));
						c1.setReceiverEmail(rs.getString(3));
						c1.setMessage(rs.getString(5));
						ch.add(c1);
						  String query2="update chat set status='Delivered' where senderEmail=? AND receiverEmail=? AND message=?";	   
						  PreparedStatement ps=db.con.prepareStatement(query2);
						  ps.setString(1,c1.getSenderEmail()); 
						  ps.setString(2,c1.getReceiverEmail());
						  ps.setString(3,c1.getMessage()); 
						
						  ps.executeUpdate();
						  
					}
				  db.stop();
				  return ch;
				  
		    	  }
		    catch(Exception e)
		    {
			System.out.println(e);
			return null;
		    }

	
	}
	    public static boolean postMsg(Chat c)
		{
			boolean check=false;
		
		    try{
				  while(check!=true){
				
					 check= db.start();
				  }
				  System.out.println("here in service of postMsg");
				  String query1="insert into chat (senderEmail,receiverEmail,status,message,time) values (?,?,?,?,?)";	   
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,c.getSenderEmail()); 
				  pstmnt.setString(2,c.getReceiverEmail());
				  pstmnt.setString(3,c.getStatus()); 
				  pstmnt.setString(4,c.getMessage());
				  pstmnt.setString(5, c.getTime());
				
				  int x=pstmnt.executeUpdate();
				  if(x!=0)
				  {
					  System.out.println("insert done in chat table");
					  return true;
				  }	  
				  else
					  return false;
		    	  }
		    catch(Exception e)
		    {
			System.out.println(e);
			return false;
		    }

	
	}
	    public static boolean deleteMsg(Chat c)
		{
			boolean check=false;
		
		    try{
				  while(check!=true){
				
					 check= db.start();
				  }
		    
				  String query1="delete from chat where senderEmail=? , receiverEmail=?,message=?";	   
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,c.getSenderEmail()); 
				  pstmnt.setString(2,c.getReceiverEmail());
				  pstmnt.setString(3,c.getMessage()); 
				  
				
				 pstmnt.executeUpdate();
				 return true;
		    } catch(Exception e)
		    {
			System.out.println(e);
			return false;
		    }

	
	}
	
	
	
}//class ends here
