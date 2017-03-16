package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.varun.fbproj.model.Event;
import com.varun.fbproj.model.User;

public class EventService {

	public static String createNewEvent(Event eobj)
	{

		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection for Event service");
            }
            String query = "insert into Event(ename,hName,location,create_date,userID,end_date) values (?,?,?,?,?,?)";
            PreparedStatement ps = connect.con.prepareStatement(query);
           
			ps.setString(1,eobj.getEname());
			ps.setString(2,eobj.getHname());
			ps.setString(3, eobj.getLocation());
			ps.setDate(4, eobj.getEvent_date());
			ps.setInt(5, eobj.getuID());
			ps.setDate(6, eobj.getEvent_end_date());
            ps.executeUpdate();
            connect.stop();
            
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "event is created";
	}

	
	
	
	public static ArrayList<Event> retriveAllEvent(int id) // give all eventDetail for particular user 
	{
		ArrayList<Event> ael=new ArrayList<Event>();
		
		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	System.out.println("trying connection for Event service");
            }
            String query = "select * from Event where userID=? and end_date>=curdate() ";
            PreparedStatement ps = connect.con.prepareStatement(query);
           
			ps.setInt(1,id);
			
			ResultSet result = ps.executeQuery();
		
			if (result != null) {
				
				while (result.next()) {
					Event el= new Event();
					el.seteID(result.getInt(1));
					el.setuID(result.getInt(2));
					el.setEname(result.getString(3));
					el.setHname(result.getString(4));
					el.setLocation(result.getString(5));
					el.setEvent_date(result.getDate(6));
					ael.add(el);
					
				}
					connect.stop();
								
					
				}
			}
           
            
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ael;
	}
	
	public static String inviteUserforEvent(int eID,int userID)
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for Event service");
	            }
	            String query = "select * from EventUserlist where eID=? and userID=?";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setInt(1,eID);
	            ps.setInt(2, userID);
				ResultSet result = ps.executeQuery();
				result.last();
				int row=result.getRow();
				result.beforeFirst();
				if(row==0)
				{
					System.out.println("number of rows is "+row);
					query="insert into EventUserlist(eID,userID,status) values (?,?,?)";
					ps = connect.con.prepareStatement(query);
					ps.setInt(1, eID);
					ps.setInt(2, userID);
					ps.setString(3, "pending");
					 ps.executeUpdate();
					 connect.stop();
				}
				else {
					System.out.println("already in database");
					 connect.stop();
				}
	            
		}
		catch (Exception e) {
			e.printStackTrace();
			return "not enter in database";
		}
		return "enter in database";
	}
	
	
	public static String userResponseforEvent(int eID, int userID)	
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for Event service");
	            }
	            String query = "UPDATE EventUserlist SET status=? where eID=? and userID=?";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setString(1,"Accepted");
				ps.setInt(2,eID);
				ps.setInt(3,userID);	
				ps.executeUpdate();
	            connect.stop();
		
		
             }
		
	            catch (Exception e) {
	    			e.printStackTrace();
	    		
	    		}     
	            
		
		return "user event status updated";
	}
	
public static String eventDelete(int eID)
{
	try {

    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection for Event service");
          }
	
          String query = "delete from EventUserlist  where eID=? ";
          PreparedStatement ps = connect.con.prepareStatement(query);
          ps.setInt(1,eID);
          ps.executeUpdate();
          query="delete from Event where eID=?";
          PreparedStatement ps1 = connect.con.prepareStatement(query);
          ps1.setInt(1, eID);
          ps1.executeUpdate();
          
          
          
          
          connect.stop();
          
	}  
	
	catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return "event is deleted";
}
	
	
	


// geting user detail which user is come on the paricular event	
public static ArrayList<User> AlluserDetail(int eID)
{
	ArrayList<User> udetail= new ArrayList<User>();
	try{
		 DBAccess connect = new DBAccess();
         boolean check=false;
         while(check==false)
         {
         	check=connect.start();
         	System.out.println("trying connection for Event service");
         }
         String query = "select userID from EventUserlist where eID=?  and status=? ";
         PreparedStatement ps = connect.con.prepareStatement(query);
        
			ps.setInt(1,eID);
			ps.setString(2, "Accepted");
			ResultSet result = ps.executeQuery();
			//System.out.println("we are in all user detail");
		if(result!=null){
		//	System.out.println("Result is not null");
			while(result.next())
			{
				User u=new User();
				int id=result.getInt(1);
				
				query="select * from User where userID=?";
				ps = connect.con.prepareStatement(query);
				ps.setInt(1, id);
		
				ResultSet result1 = ps.executeQuery();
				while(result1.next())
				{
					u.setUserID(result1.getInt(1));
					u.setEmailID(result1.getString(2));
					u.setFname(result1.getString(4));
					u.setLname(result1.getString(5));
					u.setDate(result1.getString(6));
					u.setMob_no(result1.getString(8));		
					u.setCollege(result1.getString(9));
					u.setPlaceOfWork(result1.getString(10));
					u.setHometown(result1.getString(11));
					u.setCityOfWork(result1.getString(12));
					u.setHighschool(result1.getString(13));
				}
				udetail.add(u);
			}
		}
		else
		{
			System.out.println("no user for this eventID"+eID);
		}
        connect.stop(); 
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return udetail;
}


public static Event getYourEventDetail(int eID)
{
	Event eobj=new Event();

	try {

    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection for Event service");
          }
          String query = "select * from Event where eID=?";
          PreparedStatement ps = connect.con.prepareStatement(query);
          ps.setInt(1,eID);
			ResultSet result = ps.executeQuery();
			if(result.next())
			{
				eobj.seteID(eID);
				eobj.setuID(result.getInt(2)); 
				eobj.setEname(result.getString(3));
				eobj.setHname(result.getString(4));
				eobj.setLocation(result.getString(5));
				eobj.setEvent_date(result.getDate(6));
				 eobj.setEvent_end_date(result.getDate(7));
			}
			else {
				System.out.println("this Eid is not in  database");
				
			}
			connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
		
	}
	
	
	
	return eobj;
}




public static ArrayList<Event> retriveInvitaionList(int userID) {
	ArrayList<Event> eil=new ArrayList<Event>();
	
	
	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	System.out.println("trying connection for Event service");
        }
        String query = "select eID from EventUserlist where userID=? and status=?";
        PreparedStatement ps = connect.con.prepareStatement(query);
        ps.setInt(1,userID);
        ps.setString(2, "pending");
			ResultSet result = ps.executeQuery();
			if(result!=null){
			while(result.next())
			{
				Event eobj=new Event();
				query="select * from Event where eID=?";
				ps=connect.con.prepareStatement(query);
				ps.setInt(1, result.getInt(1));
				ResultSet rs=ps.executeQuery();
			   
				while(rs.next())
				{
				  	eobj.seteID(rs.getInt(1));
				  	eobj.setuID(rs.getInt(2)); 
					eobj.setEname(rs.getString(3));
					eobj.setHname(rs.getString(4));
					eobj.setLocation(rs.getString(5));
					eobj.setEvent_date(rs.getDate(6));
					 eobj.setEvent_end_date(rs.getDate(7));
					 eil.add(eobj);
				  	
				}		
				
			}
			}
			else
			{
			  System.out.println("Result set is null");	
			}
			connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
		
	}
	
	
	
	return eil;
}




public static void userRejectforEvent(int eID, int userID) {
	try {

    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection for Event service");
          }
          String query = "UPDATE EventUserlist SET status=? where eID=? and userID=?";
          PreparedStatement ps = connect.con.prepareStatement(query);
          ps.setString(1,"Rejected");
			ps.setInt(2,eID);
			ps.setInt(3,userID);	
			ps.executeUpdate();
          connect.stop();
	
	
       }
	
          catch (Exception e) {
  			e.printStackTrace();
  		
  		}  
	
	
}




public static ArrayList<Event> retriveInvitaionList1(int userID) {
	ArrayList<Event> eil=new ArrayList<Event>();
	
	
	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	System.out.println("trying connection for Event service");
        }
        String query = "select eID from EventUserlist where userID=? and status=?";
        PreparedStatement ps = connect.con.prepareStatement(query);
        ps.setInt(1,userID);
        ps.setString(2, "Accepted");
			ResultSet result = ps.executeQuery();
			if(result!=null){
			while(result.next())
			{
				Event eobj=new Event();
				query="select * from Event where eID=?";
				ps=connect.con.prepareStatement(query);
				ps.setInt(1, result.getInt(1));
				ResultSet rs=ps.executeQuery();
			   
				while(rs.next())
				{
				  	eobj.seteID(rs.getInt(1));
				  	eobj.setuID(rs.getInt(2)); 
					eobj.setEname(rs.getString(3));
					eobj.setHname(rs.getString(4));
					eobj.setLocation(rs.getString(5));
					eobj.setEvent_date(rs.getDate(6));
					 eobj.setEvent_end_date(rs.getDate(7));
					 eil.add(eobj);
				  	
				}		
				
			}
			}
			else
			{
			  System.out.println("Result set is null");	
			}
			connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
		
	}
	
	
	
	return eil;
}




public static ArrayList<User> getFriendthatisNotinvited(ArrayList<User> u1,int eID) {
	

	
	try {

	  	  DBAccess connect = new DBAccess();
	        boolean check=false;
	        while(check==false)
	        {
	        	check=connect.start();
	        	System.out.println("trying connection for Event service");
	        }
	        String query = "select userID from EventUserlist where eID=? ";
	        PreparedStatement ps = connect.con.prepareStatement(query);
	        ps.setInt(1,eID);
	      
				ResultSet result = ps.executeQuery();
				if(result!=null){
			  
				for(int i=0;i<u1.size();i++){
				int id=u1.get(i).getUserID();
				int flag=0;
				while(result.next())
				{
				    
					if(id==result.getInt(1))
					{
				      flag=1;
				      break;
					}
				
				}
				if(flag==1)
				u1.remove(i);
				result.beforeFirst();
				
				
				}
				
				}
				else
				{
				  System.out.println("Result set is null");	
				}
				connect.stop();
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	
	
	
	
	return u1;
}



	
	
}