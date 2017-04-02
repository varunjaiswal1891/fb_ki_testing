
package com.varun.fbproj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.OutputStream;

import com.varun.fbproj.model.Comment;
import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;

public class GroupService {

	public static boolean createGroup(Group group,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for creating group");
	            }
	            System.out.println("called service class");
	            
	        //inserting new group info into group1 table
	        //stores owner and privacy of the group    
	            String query = "insert into Group1(group_name,owner,privacy) values (?,?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,group.getGroup_name());
				ps.setString(2,emailID);//it is the emailID of owner 
				ps.setString(3,group.getGroup_privacy());
	            ps.executeUpdate();		
	            
	         //adding owner to group member table also
	         //group members are stored in this table   
	            String query1 = "insert into UserGroup(group_name,emailID) values (?,?)";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group.getGroup_name());
				ps1.setString(2,emailID);//it is the emailID of user being added to the group
	            ps1.executeUpdate();
	            
	            
				connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
	}//create group method ends here
	
	
	public static boolean addUserGroup(String group_name,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for adding people to the group");
	            }
	            
	            //adding user to group members table
	            //the added user mailId is stored in this table
	            String query = "insert into UserGroup(group_name,emailID) values (?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,group_name);
				ps.setString(2,emailID);//it is the emailID of user being added to the group
	            ps.executeUpdate();		
				connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
		
	}//method addUserGroup ends here

	
	public static ArrayList<Group> getMyAllGroups(String emailID,ArrayList<Group> al_groups)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for getting all my groups");
	            }
	            
	            //Retrieving group names of the current user.
	            //current users groups are displayed in the mygroup html page
	            String query = "select group_name from UserGroup where emailID=?";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,emailID);
	            
	              ResultSet result = ps.executeQuery();
				
					while (result.next()) {
				
					String e1=result.getString("group_name");
					System.out.println("e1="+e1);
					//al_groups.add(e1);
					Group g1=new Group();
					g1=RetriveService.getGroupAllData(e1);
					al_groups.add(g1);
					
				}
	             	
				connect.stop();
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		System.out.println("Group list="+al_groups);
		return al_groups;
		
	}//method addUserGroup ends here

	
	
	public static boolean deleteGroup(String group_name,String myEmailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for deleting group");
	            }
	            
	            //Select the owner of the group that has to be deleted.
	            //only owner can delete the group
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 
				 //check whether the person who wants to delete,is the owner of the group or not
				 if(e1.equals(myEmailID))  //he is the owner so delete group
				 {
					 //check the privacy of the group
					 //since statuses of private and public groups are stored separately
					 String query="select privacy from Group1 where group_name=?";
					 PreparedStatement pstmnt1 = connect.con.prepareStatement(query);
					 pstmnt1.setString(1,group_name);
					 ResultSet rs5= pstmnt1.executeQuery();
					 rs5.next();
					 String privacy = rs5.getString("privacy");
					 System.out.println(privacy);
					 if(privacy.equals("public"))
					 {
						 //delete the group entry from the user group table
					 String query3="delete from UserGroup where group_name=?";
			            PreparedStatement ps3 = connect.con.prepareStatement(query3);
			            ps3.setString(1,group_name);
			            ps3.executeUpdate();
					 
			            //delete the group entry from the group table
				    	 String query2="delete from Group1 where group_name=? and owner=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,group_name);
			            ps2.setString(2,myEmailID);
			            int x=  ps2.executeUpdate();
			            if(x>0)
			            {
			            	System.out.println(" one Group deleted successfully ");
			            }
			            
			            //delete the statuses of the group that is being deleted
			            //this is to remove statuses of deleted group from the user wall
			            String query4="delete from status where group_name=?";
			            PreparedStatement ps4 = connect.con.prepareStatement(query4);
			            ps4.setString(1,group_name);
			            int z= ps4.executeUpdate();
			            if(z>0)
			            {
			            	return true;
			            }
					 }
					 else
					 {
						 //delete the group from the member table along with all members
						 String query3="delete from UserGroup where group_name=?";
				            PreparedStatement ps3 = connect.con.prepareStatement(query3);
				            ps3.setString(1,group_name);
				            ps3.executeUpdate();
						 
				            //delete the group entry in the group table
					    	 String query2="delete from Group1 where group_name=? and owner=?";
				            PreparedStatement ps2 = connect.con.prepareStatement(query2);
				            ps2.setString(1,group_name);
				            ps2.setString(2,myEmailID);
				            int x=  ps2.executeUpdate();
				            if(x>0)
				            {
				            	System.out.println(" one Group deleted successfully ");
				            }
				            
				            //since the group is private the statuses of the group is deleted fro the private group status table
				            String query4="delete from privategroupstatus where group_name=?";
				            PreparedStatement ps4 = connect.con.prepareStatement(query4);
				            ps4.setString(1,group_name);
				            int z= ps4.executeUpdate();
				            if(z>0)
				            {
				            	return true;
				            }
					 }
				 }
				 else
				 {
					 System.out.println("You are not admin ... so cannot delete group");
					 return false;
				 }
				
			}//while ends
	            
	            		
				connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
		  }
		return false;
		
	}//method deleteGroup ends here


	
	public static boolean deleteUserByOwnerGroup(String group_name,String owner,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for deleting people from the group");
	            }
	            
	            //select owner to know whether the user has the right to delete a member
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(e1.equals(owner))  //he is the owner so delete the user
				 {
					 //delete the member from the member table
					 String query2="delete from UserGroup where emailID=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,emailID);
			            int x= ps2.executeUpdate();
			            if(x>0)
			            {
			            	System.out.println(" one user deleted  from successfully ");
			            	return true;
			            }
				 }
				 else
				 {
					 System.out.println("You are not admin ... so cannot delete group");
					 return false;
				 }
				
			}//while ends
	            
	            		
				connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
		  }
		return false;
		
	}//method delete user from Group ends here

	
	public static boolean deleteUserInGroup(String group_name,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for leave group");
	            }
	            
	            //keeps track of which group the user has left
	            //this is to avoid displaying statuses of the group which user is not part of
	            String quer = "insert into GroupLeft(group_name,emailID) values (?,?)";
	            PreparedStatement p = connect.con.prepareStatement(quer);
	            p.setString(1,group_name);
				p.setString(2,emailID);
	            p.executeUpdate();
	            
	            //check the owner of the group
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(!e1.equals(emailID))  //not owner so delete from group
				 {
					 
					 //since the use is not an owner you can delete him from the group
					 String query2="delete from UserGroup where emailID=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,emailID);
			            int x= ps2.executeUpdate();
			            
			            if(x>0)
			            {
			            	System.out.println(" one user deleted  from successfully ");
			            	return true;
			            }
			            
					 
				 }
				 else
				 {
					 //owner wants to leave
					 //1.remove owner from UserGroup first
					 String query2="delete from UserGroup where emailID=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,emailID);
			            int x= ps2.executeUpdate();
			            
			            if(x>0)
			            {
			            	System.out.println(" one user deleted  from again ");
			            	//2.select a group member and make him admin now
			            	//select emailID from UserGroup where group_name='office' order by RAND() LIMIT 1;
			            	String query = "select emailID from UserGroup where group_name=? order by RAND() LIMIT 1";
				            PreparedStatement ps = connect.con.prepareStatement(query);
				            ps.setString(1,group_name);
				            
				              ResultSet rs2 = ps.executeQuery();
							
								while (rs2.next()) {
							
								 e1=rs2.getString("emailID");
								 
								 //now make this emaiID as new owner of that group
								 System.out.println("random emailID ="+e1);
								 String q3="update Group1 set owner=? where group_name=?";
						            PreparedStatement ps3 = connect.con.prepareStatement(q3);
						            ps3.setString(1,e1);
						            ps3.setString(2, group_name);
						            int y= ps3.executeUpdate();
						         
						            if(y>0)
						            {
						            	System.out.println("New owner assigned to group");
						            	return true;
						            }
								 
								}
			            	
			            }//if x>0 over
			            


				 }//outer else ends
				}//while ends
	            
	            
	            
	            
	            
	            
	            
	            
	            		
				connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
		  }
		return false;
		
	}//method delete user leave Group ends here

	
	
	 public  static ArrayList<Status> getStatusByGroup(String group_name){
		 DBAccess db= new DBAccess();
		 
		 
			String rs1;
			boolean check=false;
		    String emailID="";
		    ArrayList<Status> statusArrayList = new ArrayList<Status>();	

		    try{
				  while(check!=true){
					  System.out.println("trying connection in getStatus");
					 check= db.start();
				  }
				  
				  //select privacy to know whether the group is private or public
				  String query="select privacy from Group1 where group_name=?";
					 PreparedStatement pstmnt1 = db.con.prepareStatement(query);
					 pstmnt1.setString(1,group_name);
					 ResultSet rs5= pstmnt1.executeQuery();
					 rs5.next();
					 String privacy = rs5.getString("privacy");
					 System.out.println(privacy);
					 if(privacy.equals("public"))
					 {
		    //the group is public retrieve all statuses from the status table
				  String query1="select * from status where group_name= ?";	   
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,group_name); // user_id is the one sent in paramater
				  ResultSet rs= pstmnt.executeQuery();
				  			  
				  
				  
		        while (rs.next())
		        
		        { 
		        	//get data of the status
		        	System.out.println("inside first result set");
		        	Status status_obj = new Status();
					status_obj.setStatusID(rs.getInt(1));
					status_obj.setStatus_desc(rs.getString(2));
					status_obj.setCreated(String.valueOf(rs.getTimestamp(3)));
					emailID=rs.getString(4);
					status_obj.setEmailID(rs.getString(4));
					status_obj.setFlag(rs.getInt(5));
					//statusArrayList.add(status_obj);	
					status_obj.setGroup_name(rs.getString(6));	
		         
					  String query11="select fname,lname from User where emailID=?";	   
					  PreparedStatement pstmnt11=db.con.prepareStatement(query11);
					  pstmnt11.setString(1,emailID); // user_id is the one sent in paramater
					  ResultSet rs11= pstmnt11.executeQuery();
					  rs11.next();
					  status_obj.setName(rs11.getString("fname")+" "+ rs11.getString("lname"));
					System.out.println("name =  "+status_obj.getName());
					
					
					
		            ArrayList<Comment> commentArrayList = new ArrayList<Comment>();
		            Comment comment_obj;
	                int stID=rs.getInt(1);
	                System.out.println("*****"+stID+"***");
	                
	                //get the like info of the statuses from the like table
	                /**********LIKES************************/
		            String query3="select count(likeID) from likes where statusID=? and flag=1"; 
					  PreparedStatement pstmnt3=db.con.prepareStatement(query3);
					  pstmnt3.setInt(1,stID); // user_id is the one sent in paramater
					  ResultSet rs3= pstmnt3.executeQuery();
					  
		             while(rs3.next())
		             {   System.out.println("Inside rs3. while ");
		            	 status_obj.setLikesCount(rs3.getInt(1)); 
		            	 System.out.println("likes............: "+status_obj.getLikesCount());
		             }

		             /**********UNLIKES************************/
			            String query31="select count(likeID) from likes where statusID=? and flag=0"; 
						  PreparedStatement pstmnt31=db.con.prepareStatement(query31);
						  pstmnt31.setInt(1,stID); // user_id is the one sent in paramater
						  ResultSet rs31= pstmnt31.executeQuery();
						  
			             while(rs31.next())
			             {   System.out.println("Inside rs31. while ");
			            	 status_obj.setUnlikes_count(rs31.getInt(1)); 
			            	 System.out.println("unlikes............: "+status_obj.getUnlikes_count());
			             }
		             
			             
			             
			             //get comment info of the statuses from the comment table
	                /*********Comments************/
		            String query2 = "select * from comments where statusID = ?";

		            PreparedStatement pstmnt2=db.con.prepareStatement(query2);
					 pstmnt2.setInt(1,stID); // user_id is the one sent in paramater
					 ResultSet rs2= pstmnt2.executeQuery();
					 rs2.last();
					 int rows = rs2.getRow();
					 rs2.beforeFirst();
					 System.out.println("row count:"+rows);
	
		            while (rs2.next())
		            {   
		            	comment_obj=new Comment();
		            	System.out.println("inside second result set");
		                
		                comment_obj.setCommentID(rs2.getInt(1));
		                comment_obj.setEmailID(rs2.getString(3));
						comment_obj.setComment_desc(rs2.getString(4));
						comment_obj.setFlag(rs2.getInt(5));
						
						//get user info the person who has commented on the post
						String query12="select fname,lname from User where emailID=?";	   
						  PreparedStatement pstmnt12=db.con.prepareStatement(query12);
						  pstmnt12.setString(1,comment_obj.getEmailID()); // user_id is the one sent in paramater
						  ResultSet rs12= pstmnt12.executeQuery();
						  rs12.next();
						  comment_obj.setName(rs12.getString("fname")+" "+ rs12.getString("lname"));
		                   
		                commentArrayList.add(comment_obj);   
		                for(Comment clist:commentArrayList){
		                	System.out.print("comment list contains "+clist.getComment_desc()+" "+clist.getCommentID()+",");
		                }
		                System.out.println();
		            }//inner while ends
		           
	                System.out.println("Caling comment class method");
		            status_obj.setA(commentArrayList);		          
		            statusArrayList.add(status_obj);    
		        }//outer while ends
		        db.stop();
					 }
					 else
					 {
						 
						 //this is when the group is private
						 // \fetch statuses from the private group table
						 String query1="select * from privategroupstatus where group_name= ?";	   
						  PreparedStatement pstmnt=db.con.prepareStatement(query1);
						  pstmnt.setString(1,group_name); // user_id is the one sent in paramater
						  ResultSet rs= pstmnt.executeQuery();
						  			  
						  
						  
				        while (rs.next())
				        {   System.out.println("inside first result set");
				        	Status status_obj = new Status();
							status_obj.setStatusID(rs.getInt(1));
							status_obj.setStatus_desc(rs.getString(2));
							status_obj.setCreated(String.valueOf(rs.getTimestamp(3)));
							emailID=rs.getString(4);
							status_obj.setEmailID(rs.getString(4));
							status_obj.setFlag(rs.getInt(5));
							//statusArrayList.add(status_obj);	
							status_obj.setGroup_name(rs.getString(6));	
				         
							  String query11="select fname,lname from User where emailID=?";	   
							  PreparedStatement pstmnt11=db.con.prepareStatement(query11);
							  pstmnt11.setString(1,emailID); // user_id is the one sent in paramater
							  ResultSet rs11= pstmnt11.executeQuery();
							  rs11.next();
							  status_obj.setName(rs11.getString("fname")+" "+ rs11.getString("lname"));
							System.out.println("name =  "+status_obj.getName());
							
							
							
				            ArrayList<Comment> commentArrayList = new ArrayList<Comment>();
				            Comment comment_obj;
			                int stID=rs.getInt(1);
			                System.out.println("*****"+stID+"***");
			                
			                //like info of the status posted
			                /**********LIKES************************/
				            String query3="select count(likeID) from privategrouplikes where statusID=? and flag=1"; 
							  PreparedStatement pstmnt3=db.con.prepareStatement(query3);
							  pstmnt3.setInt(1,stID); // user_id is the one sent in paramater
							  ResultSet rs3= pstmnt3.executeQuery();
				             while(rs3.next())
				             {   System.out.println("Inside rs3. while ");
				            	 status_obj.setLikesCount(rs3.getInt(1)); 
				            	 System.out.println("likes............: "+status_obj.getLikesCount());
				             }

				             /**********UNLIKES************************/
					            String query31="select count(likeID) from privategrouplikes where statusID=? and flag=0"; 
								  PreparedStatement pstmnt31=db.con.prepareStatement(query31);
								  pstmnt31.setInt(1,stID); // user_id is the one sent in paramater
								  ResultSet rs31= pstmnt31.executeQuery();
								  
					             while(rs31.next())
					             {   System.out.println("Inside rs31. while ");
					            	 status_obj.setUnlikes_count(rs31.getInt(1)); 
					            	 System.out.println("unlikes............: "+status_obj.getUnlikes_count());
					             }
				             
					             //comment info of the comment posted on the post
			                /*********Comments************/
				            String query2 = "select * from privategroupcomments where statusID = ?";

				            PreparedStatement pstmnt2=db.con.prepareStatement(query2);
							 pstmnt2.setInt(1,stID); // user_id is the one sent in paramater
							 ResultSet rs2= pstmnt2.executeQuery();
							 rs2.last();
							 int rows = rs2.getRow();
							 rs2.beforeFirst();
							 System.out.println("row count:"+rows);
			
				            while (rs2.next())
				            {   
				            	//fetch info of the user who has commented on the post
				            	comment_obj=new Comment();
				            	System.out.println("inside second result set");
				                
				                comment_obj.setCommentID(rs2.getInt(1));
				                comment_obj.setEmailID(rs2.getString(3));
								comment_obj.setComment_desc(rs2.getString(4));
								comment_obj.setFlag(rs2.getInt(5));
								
								String query12="select fname,lname from User where emailID=?";	   
								  PreparedStatement pstmnt12=db.con.prepareStatement(query12);
								  pstmnt12.setString(1,comment_obj.getEmailID()); // user_id is the one sent in paramater
								  ResultSet rs12= pstmnt12.executeQuery();
								  rs12.next();
								  comment_obj.setName(rs12.getString("fname")+" "+ rs12.getString("lname"));
				                   
				                commentArrayList.add(comment_obj);   
				                for(Comment clist:commentArrayList){
				                	System.out.print("comment list contains "+clist.getComment_desc()+" "+clist.getCommentID()+",");
				                }
				                System.out.println();
				            }//inner while ends
				           
			                System.out.println("Caling comment class method");
				            status_obj.setA(commentArrayList);		          
				            statusArrayList.add(status_obj);    
				        }//outer while ends
				        db.stop();
					 }
		        
		        java.util.Iterator<Status> itr=statusArrayList.iterator();  
		        while(itr.hasNext()){  
		        	System.out.println("inside iterator");
		         System.out.println(itr.next());  
		        }
		        return statusArrayList;
		    } 
		    catch (SQLException e) 
		    {
		        e.printStackTrace();
		    }
		   

		    return null;
 }//method getStatusByGroup ends here

	
public  static ArrayList<User> getGroupMembers(String group_name){
		    
		 String result;
		 DBAccess db= new DBAccess();
    	boolean check=false;
	  try{
	      while(check!=true){
		  System.out.println("trying connection in get all members of group");
		  check= db.start();
	  }
	      
	      //this is to display the members of the group
	  String sql="select emailID from UserGroup where group_name= ?";
	 
	  PreparedStatement pstmnt=db.con.prepareStatement(sql);
	  pstmnt.setString(1,group_name); 
	  ResultSet rs= pstmnt.executeQuery();
	  ArrayList<User> user_list= new ArrayList<User>();
	  if(rs!=null){
		  
		  while (rs.next()) {
			  
			  //info of the user is retrieved
			  User u1=new User();
			  String e1=rs.getString("emailID");
			  u1=RetriveService.getUserAllData(e1);
			  user_list.add(u1);
		
			}
	  }
	  else{
		  System.out.println("resultset empty");
	  }
	  db.stop();
	  return user_list;
	}
	catch(Exception e){
		
	}
	
	 return  null;
 }//method get all members of Group ends here



public  static User getGroupAdmin(String group_name){
    
	 String result;
	 DBAccess db= new DBAccess();
	boolean check=false;
 try{
     while(check!=true){
	  System.out.println("trying connection in get all members of group");
	  check= db.start();
 }
     
     
     //get the owner of the requested group
 String sql="select owner from Group1 where group_name= ?";

 PreparedStatement pstmnt=db.con.prepareStatement(sql);
 pstmnt.setString(1,group_name); 
 ResultSet rs= pstmnt.executeQuery();
 User u1=new User();
 if(rs!=null){
	  
	  while (rs.next()) {
		  
		  String e1=rs.getString("owner");
		  u1=RetriveService.getUserAllData(e1);
		
	
		}
 }
 else{
	  System.out.println("resultset empty");
 }
 db.stop();
 return u1;
}
catch(Exception e){
	
}

return  null;
}//method get Admin of Group ends here


public static ArrayList<String> getgroupname(String myEmailID) {
	ArrayList<String> asl= new ArrayList<String>();
	
	 DBAccess db= new DBAccess();
		boolean check=false;
	 try{
	     while(check!=true){
		  System.out.println("trying connection in get all members of group");
		  check= db.start();
	 }
	     
	     //getting the group names from which the user has left
	 String sql="select * from GroupLeft where emailID=?";

	 PreparedStatement pstmnt=db.con.prepareStatement(sql);
	   pstmnt.setString(1, myEmailID);
	 ResultSet rs= pstmnt.executeQuery();
	
	 if(rs!=null){
		  
		  while (rs.next()) {
			  
			  String gname=rs.getString(2);
			   asl.add(gname);
		
			}
	 }
	 
	 db.stop();
	}
	catch(Exception e){
		
	}
	
	
	
	
	
	
	
	return asl;
}

public String uploadPic(InputStream fileInputStream,
		String fileName, String group_name,String emailID) {
		OutputStream outputStream=null;
		//getEmailId s1=new getEmailId();
		//String email=s1.getemailId(token);
		String path="/home/umesh/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/groups/";
		try{
			outputStream=new FileOutputStream(new File(path+group_name));
			int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = fileInputStream.read(bytes)) != -1) {
            { 
            outputStream.write(bytes, 0, read);
            }
            
			}outputStream.close();
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		finally{
		 
		if(outputStream!=null)
		 return "uploaded Successfully!!!!";
		}
		
		return null;
		
		
}
			
	
	
}//class ends