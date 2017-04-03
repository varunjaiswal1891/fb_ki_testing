package com.varun.fbproj.service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import com.varun.fbproj.model.Chat;
import com.varun.fbproj.model.User;

public class ChatService {

	static DBAccess db = new DBAccess();
	public static ArrayList<Chat> getAll(Chat obj)
	{
		boolean check=false;
		  ArrayList<Chat> ch = new ArrayList<Chat>();
	    try{
			  while(check!=true){
				 
				 check= db.start();
			  }
	    
			  String query2="select * from chat where (senderEmail= ? AND receiverEmail= ? AND flag=? AND showemailID=? ) OR (senderEmail= ? AND receiverEmail= ?  AND flag=? AND showemailID=? )";	   
			  PreparedStatement pstmnt1=db.con.prepareStatement(query2);
			  pstmnt1.setString(1,obj.getSenderEmail()); 
			  pstmnt1.setString(2,obj.getReceiverEmail());
			  pstmnt1.setInt(3, 1);
			  pstmnt1.setString(4,obj.getSenderEmail()); 
			  pstmnt1.setString(5,obj.getReceiverEmail()); 
			  pstmnt1.setString(6,obj.getSenderEmail()); 
			  pstmnt1.setInt(7, 1);
			  pstmnt1.setString(8,obj.getSenderEmail());
			  ResultSet rs1= pstmnt1.executeQuery();
			  while (rs1.next()) {
					Chat c = new Chat();
					c.setSenderEmail(rs1.getString(2));
					c.setReceiverEmail(rs1.getString(3));
					c.setChatID(rs1.getInt(1));
					c.setMessage(rs1.getString(5));
					ch.add(c);
				}
			  
			  
			  String query1="select * from chat where (senderEmail= ? AND receiverEmail= ? AND flag=?) OR (senderEmail= ? AND receiverEmail= ? AND flag=?)";	   
			  PreparedStatement pstmnt=db.con.prepareStatement(query1);
			  pstmnt.setString(1,obj.getSenderEmail()); 
			  pstmnt.setString(2,obj.getReceiverEmail());
			  pstmnt.setInt(3, 0);
			  pstmnt.setString(4,obj.getReceiverEmail()); 
			  pstmnt.setString(5,obj.getSenderEmail()); 
			  pstmnt.setInt(6, 0);
			  ResultSet rs= pstmnt.executeQuery();
			
			  while (rs.next()) {
					Chat c = new Chat();
					c.setChatID(rs.getInt(1));
					c.setSenderEmail(rs.getString(2));
					c.setReceiverEmail(rs.getString(3));
					
					c.setMessage(rs.getString(5));
					ch.add(c);
					  				}
			   
			  String query3="update chat set status='Delivered' where senderEmail=? AND receiverEmail=?  ";	   
			  PreparedStatement ps=db.con.prepareStatement(query3);
			  ps.setString(2,obj.getReceiverEmail()); 
			  ps.setString(1,obj.getSenderEmail());
			  
			
			  ps.executeUpdate();

			
			  
			  
			  
			  
			  
			  for(int i=0;i<ch.size();i++)
				{
					User u=new User();
					u=RetriveNameService.getUserAllData(ch.get(i).getSenderEmail());
					ch.get(i).setSenderName(u.getFname()+" "+u.getLname());
					u=RetriveNameService.getUserAllData(ch.get(i).getReceiverEmail());
					ch.get(i).setReceiverName(u.getFname()+" "+u.getLname());
					
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
				  String query1="select * from chat where senderEmail= ? AND receiverEmail= ?  AND status= ? ";	   
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
						  String query2="update chat set status='Delivered' where senderEmail=? AND receiverEmail=? AND message=? ";	   
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
				  String query1="insert into chat (senderEmail,receiverEmail,status,message) values (?,?,?,?)";	   
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,c.getSenderEmail()); 
				  pstmnt.setString(2,c.getReceiverEmail());
				  pstmnt.setString(3,c.getStatus()); 
				  pstmnt.setString(4,c.getMessage());
				 
				
				  int x=pstmnt.executeUpdate();
				  if(x!=0)
				  {
					  System.out.println("insert done in chat table");
					  db.stop();
					  return true;
				  }	  
				  else
					  db.stop();
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
				 db.stop();
				 return true;
		    } catch(Exception e)
		    {
			System.out.println(e);
			return false;
		    }

	
	}
	
	    public static ArrayList<Chat> fetchAllConversations(String email)
		{
			boolean check=false;
		
		    try{
				  while(check!=true){
				
					 check= db.start();
				  }
				  String query1="select receiverEmail from chat where senderEmail=?  group by receiverEmail ";
				  String query2="select senderEmail,count(status),status from chat where receiverEmail=?  group by senderEmail,status ";
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,email);
				  //pstmnt.setInt(2, 0);
				  ResultSet rs= pstmnt.executeQuery();
				  System.out.println("this is done");
				 
				  ArrayList<Chat> ch = new ArrayList<Chat>();
				  while (rs.next()) 
				  {
						Chat c1 = new Chat();
						c1.setSenderEmail(rs.getString(1));
						c1.setStatus("Delivered");
						System.out.println("sender emails :"+c1.getSenderEmail());
						if(!ch.contains(c1))
						{	
							System.out.println("adding");
							ch.add(c1);
				  }}
				  db.stop();
				  check=false;
				  while(check!=true){
						
						 check= db.start();
					  }
				
				  System.out.println(ch.size());
						  PreparedStatement ps=db.con.prepareStatement(query2);
						  ps.setString(1,email); 
						 // ps.setInt(2, 0);
						  ResultSet rs1= ps.executeQuery();
					System.out.println("here here here");	  
						  while (rs1.next()) {
								Chat c1 = new Chat();
								c1.setSenderEmail(rs1.getString(1));
								c1.setStatus(rs1.getString(3));
								//int count=rs1.getInt(2);
								
								System.out.println(c1.getSenderEmail());
								int flag=0;
								for(int i=0;i<ch.size();i++)
								{
									if(ch.get(i).getSenderEmail().equals(c1.getSenderEmail()))
									{	if(c1.getStatus().equals("NEW"))
											ch.get(i).setStatus("NEW");
										flag=1;
									}	
								}
									if(flag==0)
										ch.add(c1);
								
								
								}
						  for(int i=0;i<ch.size();i++)
							{
								User u=new User();
								u=RetriveNameService.getUserAllData(ch.get(i).getSenderEmail());
								ch.get(i).setSenderName(u.getFname()+" "+u.getLname());
							}
							
								
								
								System.out.println(ch.size());
								
					
				  db.stop();
				  return ch;
				  
		    	  }
		    catch(Exception e)
		    {
			System.out.println(e);
			return null;
		    }

	
	}
		public static String deleteyourAllMessage(String emailID,String friendID)
	    {
		
			boolean check=false;
			
		    try {
				  while(check!=true){
				
					 check= db.start();
				  }
				   String query1="delete from chat where flag=? and senderEmail=? and receiverEmail=? and showemailID=?";	   
				   PreparedStatement pstmnt=db.con.prepareStatement(query1);
				   pstmnt.setInt(1,1); 
				   pstmnt.setString(2,emailID);
				   pstmnt.setString(3,friendID);
				   pstmnt.setString(4,emailID);
				   pstmnt.executeUpdate();
				    query1="delete from chat where flag=? and senderEmail=? and receiverEmail=? and showemailID=?";	   
				    pstmnt=db.con.prepareStatement(query1);
				   pstmnt.setInt(1,1); 
				   pstmnt.setString(3,emailID);
				   pstmnt.setString(2,friendID);
				   pstmnt.setString(4,emailID);
				   pstmnt.executeUpdate();
				   String query2="update chat set flag=?,showemailID=?  where senderEmail=? AND receiverEmail=? AND flag=?";	   
					  PreparedStatement ps=db.con.prepareStatement(query2);
					  ps.setInt(1,1); 
					  ps.setString(2,friendID);
					  ps.setString(3,emailID);
					  ps.setString(4,friendID);
					  ps.setInt(5, 0);
					  ps.executeUpdate();
					  
					  query2="update chat set flag=?,showemailID=?  where senderEmail=? AND receiverEmail=? AND flag=?";	   
					  ps=db.con.prepareStatement(query2);
					  ps.setInt(1,1); 
					  ps.setString(2,friendID);
					  ps.setString(4,emailID);
					  ps.setString(3,friendID);
					  ps.setInt(5, 0);
					  ps.executeUpdate();
					  db.stop();
				  
				  return "message deleted sucessfully";
				
				 
				
		    } catch(Exception e)
		    {
			System.out.println(e);
			
		    }
			
			
			return "not delete";
		}
		public static String deleteyouroneMessage(String emailID,String friendID, int chatID) {
	boolean check=false;
			
		    try {
				  while(check!=true){
				
					 check= db.start();
				  }
				   String query1="delete from chat where flag=? and senderEmail=? and receiverEmail=? and showemailID=? and chatID=? ";	   
				   PreparedStatement pstmnt=db.con.prepareStatement(query1);
				   pstmnt.setInt(1,1); 
				   pstmnt.setString(2,emailID);
				   pstmnt.setString(3,friendID);
				   pstmnt.setString(4,emailID);
				   pstmnt.setInt(5, chatID);
				   pstmnt.executeUpdate();
				    query1="delete from chat where flag=? and senderEmail=? and receiverEmail=? and showemailID=? and chatID=?";	   
				    pstmnt=db.con.prepareStatement(query1);
				   pstmnt.setInt(1,1); 
				   pstmnt.setString(3,emailID);
				   pstmnt.setString(2,friendID);
				   pstmnt.setString(4,emailID);
				   pstmnt.setInt(5, chatID);
				   pstmnt.executeUpdate();
				   String query2="update chat set flag=?,showemailID=?  where senderEmail=? AND receiverEmail=? AND flag=? and chatID=?";	   
					  PreparedStatement ps=db.con.prepareStatement(query2);
					  ps.setInt(1,1); 
					  ps.setString(2,friendID);
					  ps.setString(3,emailID);
					  ps.setString(4,friendID);
					  ps.setInt(5, 0);
					  ps.setInt(6,chatID);
					  ps.executeUpdate();
					  
					  query2="update chat set flag=?,showemailID=?  where senderEmail=? AND receiverEmail=? AND flag=? and chatID=?";	   
					  ps=db.con.prepareStatement(query2);
					  ps.setInt(1,1); 
					  ps.setString(2,friendID);
					  ps.setString(4,emailID);
					  ps.setString(3,friendID);
					  ps.setInt(5, 0);
					  ps.setInt(6,chatID);
					  ps.executeUpdate();
					  db.stop();
				  
				  return "message deleted sucessfully";
				
				 
				
		    } catch(Exception e)
		    {
			System.out.println(e);
			
		    }
			
			
			return "not delete";
		}
	
	
}//class ends here