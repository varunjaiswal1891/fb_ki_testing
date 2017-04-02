
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
	        // retriving groupid from Group1
	            String query12="select gid from Group1 where group_name=? and owner=?";
	            PreparedStatement ps12 = connect.con.prepareStatement(query12);
	            ps12.setString(1,group_name);
	            ps12.setString(2, emailID);
	            ResultSet result = ps12.executeQuery();
	            int gid=0;
	            while(result.next()){
	            	gid=result.getInt(1);
	            }
	            
	            
	            
	            //adding owner to other table also
	            String query1 = "insert into UserGroup(group_name,emailID1,emailID2,gid) values (?,?,?,?)";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
				ps1.setString(2,emailID);//it is the emailID of owner of group
				ps1.setString(3,emailID);// it is emailID of member of group thatis owner is member of group
				ps1.setInt(4,gid);
				ps1.executeUpdate();
	            
	            
				connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
	}//create group method ends here
	
	
	public static boolean addUserGroup(String group_name,String emailID,String ownerEmailID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for adding people to the group");
	            }
	            
	            
	         // retriving groupid from Group1
	            String query12="select gid from Group1 where group_name=? and owner=?";
	            PreparedStatement ps12 = connect.con.prepareStatement(query12);
	            ps12.setString(1,group_name);
	            ps12.setString(2, ownerEmailID);
	            ResultSet result = ps12.executeQuery();
	            int gid=0;
	            while(result.next()){
	            	gid=result.getInt(1);
	            }
	            
	            
	            
	            String query = "insert into UserGroup(group_name,emailID1,emailID2,gid) values (?,?,?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,group_name);
				ps.setString(2,ownerEmailID);//it is the emailID of owner of the group;
				ps.setString(3,emailID);  // it is the emailID of user that member of the group;
				ps.setInt(4,gid); 
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
	            String query = "select * from UserGroup where emailID2=?";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,emailID);
	            
	              ResultSet result = ps.executeQuery();
				
					while (result.next()) {
				
					String e1=result.getString(1);
					int gid=result.getInt(4);
					//System.out.println("e1="+e1);
					//al_groups.add(e1);
					Group g1=new Group();
					g1=RetriveService.getGroupAllData(e1,gid);
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
	            String query1="select owner from Group1 where group_name=? and owner=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ps1.setString(2,myEmailID );
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
					 
					 String query3="delete from UserGroup where group_name=? and emailID1=?";
			            PreparedStatement ps3 = connect.con.prepareStatement(query3);
			            ps3.setString(1,group_name);
			            ps3.setString(2,myEmailID );
			             ps3.executeUpdate();
					
					    String query2="delete from Group1 where group_name=? and owner=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,group_name);
			            ps2.setString(2,myEmailID );
			             ps2.executeUpdate();
					  
				 
				
				
			}//while ends
	            
	            		
				connect.stop();
				return true;
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
	            String query1="select owner from Group1 where group_name=? and owner=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ps1.setString(2,owner);
	            ResultSet result = ps1.executeQuery();
				
				while (result.next()) {
			     String e1=result.getString("owner");
				 System.out.println("e1="+e1);
				 if(e1.equals(owner))  //he is the owner so delete the user
				 {
					 
					 String query2="delete from UserGroup where emailID2=? and emailID1=? and group_name=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,emailID);
			            ps2.setString(2,owner);
			            ps2.setString(3,group_name);
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
	            
	            String query1="select owner from Group1 where group_name=? and owner=?";
	            PreparedStatement ps1 = connect.con.prepareStatement(query1);
	            ps1.setString(1,group_name);
	            ps1.setString(2, emailID);
	            ResultSet result = ps1.executeQuery();
				result.last();
				int row= result.getRow();
				result.beforeFirst();
		
				 if(row==0)  //not owner so delete from group
				 {
					 String query2="delete from UserGroup where emailID2=? and group_name=?";
			            PreparedStatement ps2 = connect.con.prepareStatement(query2);
			            ps2.setString(1,emailID);
			            ps2.setString(2,group_name);
			            int x= ps2.executeUpdate();
			            
			            if(x>0)
			            {
			            	System.out.println(" one user deleted  from successfully ");
			            	return true;
			            }
			            
					 
				 }
			 else
				 {
				 
					   query1="select emailID2 from UserGroup where group_name=? and emailID1=?"; 
					// count number of member in particular group
			            PreparedStatement ps12 = connect.con.prepareStatement(query1);
			            ps12.setString(1,group_name);
			            ps12.setString(2, emailID);
			            ResultSet result1 = ps1.executeQuery();
			            result1.last();
						 row= result.getRow();
						result1.beforeFirst();
						System.out.println("number of rows "+row);
			            	if(row==1) // only owner is member of this group
			            	{
			            		String query2="delete from UserGroup where emailID2=? and group_name=?";
					            PreparedStatement ps2 = connect.con.prepareStatement(query2);
					            ps2.setString(1,emailID);
					            ps2.setString(2,group_name);
					             ps2.executeUpdate();
					             query2="delete from Group1 where owner=? and group_name=?";
						             ps2 = connect.con.prepareStatement(query2);
						            ps2.setString(1,emailID);
						            ps2.setString(2,group_name);
						             ps2.executeUpdate();
					             
			            	}
			         
			            	else  // make some other to owner of that group
			            	{
			            		
			            		String query12 = "delete from UserGroup where group_name=? and emailID2=? ";
			                    PreparedStatement ps11 = connect.con.prepareStatement(query12);
			                    ps11.setString(1,group_name);
			                    ps11.setString(2,emailID);
			                      ps11.executeUpdate();
			            		
			            		String query = "select emailID2 from UserGroup where group_name=? and emailID1=? order by RAND() LIMIT 1";
			                    PreparedStatement ps = connect.con.prepareStatement(query);
			                    ps.setString(1,group_name);
			                    ps.setString(2,emailID);
			                      ResultSet rs2 = ps.executeQuery();
			                   String   e2=rs2.getString("emailID");
			                   query="update Group1 set owner=? where group_name=? and owner=?";
			                   PreparedStatement ps3 = connect.con.prepareStatement(query);
		                          ps3.setString(1,e2);
		                          ps3.setString(2, group_name);
		                          ps3.setString(3, emailID);
		                          ps3.executeUpdate();
			                 
		                          query="update UserGroup set emailID1=? where group_name=? and emailID1=?";
				                    ps3 = connect.con.prepareStatement(query);
			                          ps3.setString(1,e2);
			                          ps3.setString(2, group_name);
			                          ps3.setString(3, emailID);
			                          ps3.executeUpdate();
			            	
			            	}
				
				 
				 }//outer else ends
			            


	            
	            		
				connect.stop();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
		  }
		return false;
		
	}//method delete user leave Group ends here

	
	
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

	
public  static ArrayList<User> getGroupMembers(String group_name,String userEmailID){
		    
		 String result;
		 DBAccess db= new DBAccess();
    	boolean check=false;
	  try{
	      while(check!=true){
		  System.out.println("trying connection in get all members of group");
		  check= db.start();
	  }
	      System.out.println(group_name+userEmailID);
	  String sql="select emailID1 from UserGroup where group_name= ? and emailID2=?";
	  PreparedStatement pstmnt=db.con.prepareStatement(sql);
	  pstmnt.setString(1,group_name); 
	  pstmnt.setString(2,userEmailID); 
	  ResultSet rs= pstmnt.executeQuery();
	  rs.next();
	  String e1=rs.getString("emailID1");
	  System.out.println("owner email id is "+e1);
	  
	   sql="select emailID2 from UserGroup where group_name= ? and emailID1=?";
	  PreparedStatement pstmnt1=db.con.prepareStatement(sql);
	  pstmnt1.setString(1,group_name); 
	  pstmnt1.setString(2,e1); 
	  ResultSet rs1= pstmnt1.executeQuery();
	  
	  
	  
	  
	  ArrayList<User> user_list= new ArrayList<User>();
	 
	  if(rs1!=null){
		
		  while (rs1.next()) {
			  User u1=new User();
			  String e11=rs1.getString(1);
			  u1=RetriveService.getUserAllData(e11);
			  user_list.add(u1);
		System.out.println(e11+"Is added");
			}
	  }
	  else{
		  System.out.println("resultset empty");
	  }
	  db.stop();
	  System.out.println("database is stop");
	  return user_list;
	}
	catch(Exception e){
		
	}
	
	 return  null;
 }//method get all members of Group ends here



public  static User getGroupAdmin(int gid){
    
	 String result;
	 DBAccess db= new DBAccess();
	boolean check=false;
 try{
     while(check!=true){
	  System.out.println("trying connection in get all members of group");
	  check= db.start();
 }
 String sql="select owner from Group1 where gid=?";

 PreparedStatement pstmnt=db.con.prepareStatement(sql);
 pstmnt.setInt(1,gid);  
 ResultSet rs= pstmnt.executeQuery();
 User u1=new User();
 if(rs!=null){
	  
	  while (rs.next()) {
		  
		  String e1=rs.getString("owner");
		  u1=RetriveService.getUserAllData(e1);
		break;
	
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

//geting owneremailId from groupname and member emailID 
public static String getOwnerId(String group_name, String myEmailID){
	
	try {

    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection for deleting group");
          }
          String query1="select emailID1 from UserGroup where group_name=? and emailID2=?";
          PreparedStatement ps1 = connect.con.prepareStatement(query1);
          ps1.setString(1,group_name);
          ps1.setString(2,myEmailID );
          ResultSet result = ps1.executeQuery();
          while(result.next()){
        	  String e1= result.getString("emailID1");
        	  connect.stop();
        	  return e1;
          }
          
	}
	catch(Exception e){
		
	}
	return null;
}


public static String getGroupNamefromGid(int gid) {

	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	System.out.println("trying connection for deleting group");
        }
        String query1="select group_name from Group1 where  gid=?";
        PreparedStatement ps1 = connect.con.prepareStatement(query1);
        ps1.setInt(1,gid);
        ResultSet result = ps1.executeQuery();
        while(result.next()){
      	  String e1= result.getString("group_name");
      	  connect.stop();
      	  return e1;
        }
        
	}
	catch(Exception e){
		
	}
	
	return null;
}



	
	
}//class ends