
package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.varun.fbproj.model.Comment;
import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;

public class GroupService {

	public static boolean createGroup(String group_name,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for creating group");
	            }
	            String query = "insert into Group1(group_name,owner) values (?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,group_name);
				ps.setString(2,emailID);//it is the emailID of owner 
	            ps.executeUpdate();		
	            
	            
	            //adding owner to other table also
	            String query1 = "insert into UserGroup(group_name,emailID) values (?,?)";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
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
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(e1.equals(myEmailID))  //he is the owner so delete group
				 {
					 
					 String query3="delete from UserGroup where group_name=?";
			            PreparedStatement ps3 = connect.con.prepareStatement(query3);
			            ps3.setString(1,group_name);
			            int y= ps3.executeUpdate();
					 
					 if(y>0)
					 {
					 
					 String query2="delete from Group1 where group_name=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,group_name);
			            int x=  ps2.executeUpdate();
			            if(x>0)
			            {
			            	System.out.println(" one Group deleted successfully ");
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


	
	public static boolean deleteUserFromGroup(String group_name,String owner,String emailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for deleting people from the group");
	            }
	            String query1="select owner from Group1 where group_name=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(e1.equals(owner))  //he is the owner so delete the user
				 {
					 
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
	
	
	 public  static ArrayList<Status> getStatusByGroup(String group_name){
		 DBAccess db= new DBAccess();
		 
		 //group_name="patel ka group";
			String rs1;
			boolean check=false;
		    String emailID="";

		    try{
				  while(check!=true){
					  System.out.println("trying connection in getStatus");
					 check= db.start();
				  }
		    
				  String query1="select * from status where group_name= ?";	   
				  PreparedStatement pstmnt=db.con.prepareStatement(query1);
				  pstmnt.setString(1,group_name); // user_id is the one sent in paramater
				  ResultSet rs= pstmnt.executeQuery();
				  ArrayList<Status> statusArrayList = new ArrayList<Status>();				  
				  
				  
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
	  String sql="select emailID from UserGroup where group_name= ?";
	 
	  PreparedStatement pstmnt=db.con.prepareStatement(sql);
	  pstmnt.setString(1,group_name); 
	  ResultSet rs= pstmnt.executeQuery();
	  ArrayList<User> user_list= new ArrayList<User>();
	  if(rs!=null){
		  
		  while (rs.next()) {
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

	
	
}//class ends