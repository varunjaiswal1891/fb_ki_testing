package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.varun.fbproj.model.Suggest;
import com.varun.fbproj.model.SuggestUser;
import com.varun.fbproj.model.User;

public class Suggestservice{
//here we are getting list of suggested friends
	public static ArrayList<User> getSuggestedFriends(Suggest sobj)
	{		
		
			
			ArrayList<User> uobj=new ArrayList<User>();
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for suggested friends");
	            }
	            
	            String firstid=sobj.getFirstemailid();
	            String secondid=sobj.getSecondemailid();
	            //System.out.println("asd"+firstid+secondid);
	            PreparedStatement prepStatement = connect.con.prepareStatement("select friendEmailID from UserFriends where myEmailID=? and friendEmailID!=? and status=?");
				
	           // System.out.println("assaxsaxsa");
	            
				prepStatement.setString(1,firstid);
				prepStatement.setString(2,secondid);
				prepStatement.setString(3,"Accepted");
				ResultSet result = prepStatement.executeQuery();
				//Iterator<User> iterator = uobj.iterator();
				
				
				//System.out.println("ddscdscdsc");

//Here we are adding list people who are not my friends but are friends of my friends
					while (result.next()) {
						PreparedStatement prepStatement1 = connect.con.prepareStatement("select myEmailID from UserFriends where myEmailID=? and friendEmailID=? union select suggestedp from FriendSuggestion where suggestedp=? and secondp=? and firstp=?");
						
						//System.out.println("sdcsdcdscds");
						
						prepStatement1.setString(1,result.getString("friendEmailID"));
						prepStatement1.setString(2,secondid);
						prepStatement1.setString(3,result.getString("friendEmailID"));
						prepStatement1.setString(4,secondid);
						prepStatement1.setString(5,firstid);
			
						ResultSet result1 = prepStatement1.executeQuery();
	
						//System.out.println("dwdewdwedwedwed");
						if(result1.next())
						{
							System.out.println("print"+result1.getString("myEmailID"));
							continue;
						}
						else
						{PreparedStatement prepStatement12 = connect.con.prepareStatement("select * from User where emailID=? ");
						
						//System.out.println("qwerty"+result.getString("friendEmailID"));
						prepStatement12.setString(1,result.getString("friendEmailID"));
					//System.out.println("adasdsadsadsd");
			
						ResultSet result12 = prepStatement12.executeQuery();
						result12.next();
						//obj.setEmailID(result12.getString(""));
						User obj=new User();
						obj.setUserID(result12.getInt("userID"));
						obj.setFname(result12.getString("fname"));
						obj.setLname(result12.getString("lname"));
						obj.setEmailID(result12.getString("emailID"));
						System.out.println("csdcsdcs"+obj.getFname());
						uobj.add(obj);
					
						System.out.println("ddsdsfsdfsdfsd");
						
						
						}
				
						
						
					}
		        	System.out.println("-----------------------------***---------------------------");
					Iterator<User> iterator = uobj.iterator();
					while (iterator.hasNext()) {
						System.out.println("bjbjb"+iterator.next());
					}
					
					
		}	
		catch (Exception e) {
			e.printStackTrace();
		}			
				/****finding list of frnds from myEmailID and removing them from suggested users *******/
		return uobj;
						
	}
	
	
	public static ArrayList<SuggestUser> retriveSuggestedFriends(String emailID)
	{		
		
			
			ArrayList<SuggestUser> uobj=new ArrayList<SuggestUser>();
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for suggested friends");
	            }
	            
	             PreparedStatement prepStatement = connect.con.prepareStatement("select * from FriendSuggestion where secondp=?");
				
	           // System.out.println("assaxsaxsa");
	            
				prepStatement.setString(1,emailID);
				ResultSet result = prepStatement.executeQuery();
				//Iterator<User> iterator = uobj.iterator();
				
				
				//System.out.println("ddscdscdsc");
					while (result.next()) {
						SuggestUser obj = new SuggestUser();
						 PreparedStatement prepStatement1 = connect.con.prepareStatement("select * from User where emailID=?");//Geeting list of people who are suggested to you by your friends
							
				           // System.out.println("assaxsaxsa");
				            
							prepStatement1.setString(1,result.getString("suggestedp"));
							ResultSet result1 = prepStatement1.executeQuery();
							result1.next();
						
						obj.setSuggestfname(result1.getString("fname"));
						obj.setSuggestlname(result1.getString("lname"));
						obj.setSuggestemail(result1.getString("emailID"));
						
						
						 PreparedStatement prepStatement12 = connect.con.prepareStatement("select * from User where emailID=?");
							
				           // System.out.println("assaxsaxsa");
				            
							prepStatement12.setString(1,result.getString("firstp"));
							ResultSet result12 = prepStatement12.executeQuery();
							result12.next();
						
						obj.setFirstfname(result12.getString("fname"));
						obj.setFirstlname(result12.getString("lname"));
						obj.setFirstemail(result12.getString("emailID"));
						
						
						 PreparedStatement prepStatement123 = connect.con.prepareStatement("select * from User where emailID=?");
							
				           // System.out.println("assaxsaxsa");
				            
							prepStatement123.setString(1,result.getString("secondp"));
							ResultSet result123 = prepStatement123.executeQuery();
							result123.next();
						
						obj.setSecondfname(result123.getString("fname"));
						obj.setSecondlname(result123.getString("lname"));
						obj.setSecondemail(result123.getString("emailID"));
						
						
						
						
						
						uobj.add(obj);
						
						
					}
		        	
					
					
		}	
		catch (Exception e) {
			e.printStackTrace();
		}			
				/****finding list of frnds from myEmailID and removing them from suggested users *******/
		return uobj;
						
	}

	
	
	
	
	
	
	
	public static boolean confirmSuggestedFriends(Suggest sobj)
	{		
		
			
			
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for suggested friends");
	            }
	            
	            String firstid=sobj.getFirstemailid();
	            String secondid=sobj.getSecondemailid();
	            String suggestedid=sobj.getSuggestedemailid();
	            
	            //System.out.println("asd"+firstid+secondid);
	            String query= "insert into FriendSuggestion(firstp,secondp,suggestedp) values(?,?,?)";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            
	           // System.out.println("assaxsaxsa");
	            
				ps.setString(1,firstid);
				ps.setString(2,secondid);
				ps.setString(3,suggestedid);
				ps.executeUpdate();
	            
				return true;//System.out.println("ddscdscdsc");
					
		}	
		catch (Exception e) {
			e.printStackTrace();
		}			
				/****finding list of frnds from myEmailID and removing them from suggested users *******/
		return false;
						
	}

}//class ends here
