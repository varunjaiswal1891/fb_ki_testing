package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.varun.fbproj.model.User;

public class SearchFriendService {

	//this method searches for all users in database with given fname or lname
	public static ArrayList<User> searchFriends(ArrayList<User> al_friends,String searchName)
	{
		System.out.println("varun dfjkldjsf="+ searchName);
		
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            
	            String[] splited = searchName.split("\\s+");
	            
	            for(int i=0;i<splited.length;i++)
	            {
	            
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where fname like ? or lname like ? or concat(fname,' ',lname)=? ");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ splited[i] +"%");
	            	prepStatement.setString(2,"%"+ splited[i] +"%");
	            	prepStatement.setString(3,searchName);
	            
					ResultSet result = prepStatement.executeQuery();
					
						while (result.next()) {
						String e1=result.getString("emailID");	
						User u_obj=new User();
						u_obj=RetriveService.getUserAllData(e1);
						/*u_obj.setFname(result.getString("fname"));
						u_obj.setLname(result.getString("lname"));
						u_obj.setUserID(result.getInt("userID"));
						u_obj.setEmailID(result.getString("emailID"));*/
						al_friends.add(u_obj);
						
					}
				  
					
	            }//for ends here
	            

					
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		return al_friends;
	}//method ends here
	public static ArrayList<User> searchFriends(String emailID,String searchName)
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection");
	            }
	            
	            String[] splited = searchName.split("\\s+");
	            
	            for(int i=0;i<splited.length;i++)
	            {
	            
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where fname like ? or lname like ?");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ splited[i] +"%");
	            	prepStatement.setString(2,"%"+ splited[i] +"%");
									
					ResultSet result = prepStatement.executeQuery();
					ArrayList<User> u1 = new ArrayList<User>();
						while (result.next()) {
						String e1=result.getString("emailID");	
						User u_obj=new User();
						u_obj=RetriveService.getUserAllData(e1);
						if(IsMyFriendService.isMyFriend(emailID, e1))
						{u_obj.setMob_no("1");}
						else if(IsRequestAlreadySentService.isRequestAlreadySent(emailID, e1))
						{u_obj.setMob_no("2");}
						else
						{u_obj.setMob_no("0");}
						if(!u_obj.getEmailID().equals(emailID))
						{u1.add(u_obj);}
						
					}
						return u1;
				  
					
	            }//for ends here
	            

					
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
return null;
		
		
	}//method ends here

//this method is used to apply filter to a search result.	
	public static ArrayList<User> searchFriendsFilter(String emailID,String searchName,String college,String highschool,String hometown,String cityOfWork,String friends1)
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection in Filter..");
	            }
	        	ArrayList<User> u1 = new ArrayList<User>();
	            String[] splited = searchName.split("\\s+");
	            String[] fsplited = friends1.split("\\s+");
	            
	            for(int i=0;i<splited.length;i++)
	            {
	            
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where (fname like ? or lname like ?) and (college like ? and hometown like ? and cityOfWork like ? and highschool like ?) ");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ splited[i] +"%");
	            	prepStatement.setString(2,"%"+ splited[i] +"%");
	            	prepStatement.setString(3,"%"+ college +"%");
	            	prepStatement.setString(4,"%"+ hometown +"%");
	            	prepStatement.setString(5,"%"+ cityOfWork +"%");
	            	prepStatement.setString(6,"%"+ highschool +"%");
	            	
					ResultSet result = prepStatement.executeQuery();
				
						while (result.next()) {
						String e1=result.getString("emailID");	
						User u_obj=new User();
						u_obj=RetriveService.getUserAllData(e1);
						if(IsMyFriendService.isMyFriend(emailID, e1))
						{u_obj.setMob_no("1");}
						else if(IsRequestAlreadySentService.isRequestAlreadySent(emailID, e1))
						{u_obj.setMob_no("2");}
						else
						{u_obj.setMob_no("0");}
						if(!u_obj.getEmailID().equals(emailID))
						{u1.add(u_obj);}
						
					
						
					}
						//return u1;
				  
					
	            }//for ends here
	            
	            for(int i=0;i<fsplited.length-1;i=i+2)
	            { System.out.println("....Iterator!!!!!!!!!!");
	            System.out.println("fsplited is"+fsplited[i]);
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where fname like ? and lname like ?");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ fsplited[i] +"%");
	            	prepStatement.setString(2,"%"+ fsplited[i+1] +"%");
	            	
	            	ResultSet result = prepStatement.executeQuery();
	            	
					while (result.next()) {
					String e1=result.getString("emailID");	
				
					
					Iterator<User> iter = u1.iterator();

					while (iter.hasNext()) {
					    User data = iter.next();
					    String email=data.getEmailID();
					    if(!IsMyFriendService.isMyFriend(email, e1))
					        iter.remove();
					}
				}
	            	
	            }
					return u1;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
//return null;
		
		
	}//method ends here

	
	//this service is used to apply filters and search for friends.
	public static ArrayList<User> searchForFriends(String emailID,String name,String college,String hometown,String cityOfWork,String highschool,String friends)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection in findmyFriends...!!");
	            }
	            
	            String[] splited = name.split("\\s+");
	            //if(!friends.isEmpty())
	            String[] fsplited = friends.split("\\s+");
	            ArrayList<User> u1 = new ArrayList<User>();
	            System.out.println("Length is:"+fsplited.length);
	            
	            
	            for(int i=0;i<splited.length;i++)
	            {
	            
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where (fname like ? or lname like ?) and (college like ? and hometown like ? and cityOfWork like ? and highschool like ?) ");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ splited[i] +"%");
	            	prepStatement.setString(2,"%"+ splited[i] +"%");
	            	prepStatement.setString(3,"%"+ college +"%");
	            	prepStatement.setString(4,"%"+ hometown +"%");
	            	prepStatement.setString(5,"%"+ cityOfWork +"%");
	            	prepStatement.setString(6,"%"+ highschool +"%");
					ResultSet result = prepStatement.executeQuery();
				
						while (result.next()) {
							
						String e1=result.getString("emailID");
						System.out.println(e1);	
						User u_obj=new User();
						u_obj=RetriveService.getUserAllData(e1);
						if(IsMyFriendService.isMyFriend(emailID, e1))
						{u_obj.setMob_no("1");}
						else if(IsRequestAlreadySentService.isRequestAlreadySent(emailID, e1))
						{u_obj.setMob_no("2");}
						else
						{u_obj.setMob_no("0");}
						if(!u_obj.getEmailID().equals(emailID))
						{u1.add(u_obj); 
						System.out.println("Added "+u_obj.getEmailID()+" to arraylist");
						}
						
					}
						//return u1;
				  
					
	            }//for ends here
	            
	            
	            for(int i=0;i<fsplited.length-1;i=i+2)
	            { System.out.println("....Iterator!!!!!!!!!!");
	            System.out.println("fsplited is"+fsplited[i]);
	            	PreparedStatement prepStatement = connect.con.prepareStatement("select * from User where fname like ? and lname like ?");
	            	//it searches like %name% like statement of sql
	            	prepStatement.setString(1,"%"+ fsplited[i] +"%");
	            	prepStatement.setString(2,"%"+ fsplited[i+1] +"%");
	            	
	            	ResultSet result = prepStatement.executeQuery();
	            	
					while (result.next()) {
					String e1=result.getString("emailID");	
				
					
					Iterator<User> iter = u1.iterator();

					while (iter.hasNext()) {
					    User data = iter.next();
					    String email=data.getEmailID();
					    if(!IsMyFriendService.isMyFriend(email, e1))
					        iter.remove();
					}
				}
	            	
	            }
return u1;
					
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

	}
	
	
}//class ends here
