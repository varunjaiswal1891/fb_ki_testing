package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;
import com.varun.fbproj.model.Event;
import com.varun.fbproj.model.EventReview;
import com.varun.fbproj.model.User;

public class EventService {
// one user not create two event of same name
	public static String createNewEvent(Event eobj) // create new event by user
	{

		try {

      	  DBAccess connect = new DBAccess();
            boolean check=false;
            while(check==false)
            {
            	check=connect.start();
            	//System.out.println("trying connection for Event service");
            }
            String query1="select eID from Event where userID=? and ename=? ";
            PreparedStatement ps1 = connect.con.prepareStatement(query1);
            ps1.setInt(1, eobj.getuID());
			ps1.setString(2,eobj.getEname());
			ResultSet result = ps1.executeQuery();
          boolean val= result.last();
			if(!val){
			String query = "insert into Event(ename,hName,location,create_date,userID,end_date,eventType,time) values (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connect.con.prepareStatement(query);
           
			ps.setString(1,eobj.getEname());
			ps.setString(2,eobj.getHname());
			ps.setString(3, eobj.getLocation());
			ps.setString(4, eobj.getEvent_date());
			ps.setInt(5, eobj.getuID());
			ps.setString(6, eobj.getEvent_end_date());
			ps.setString(7,eobj.getEventType());
			ps.setString(8, eobj.getTime());
            ps.executeUpdate();
            connect.stop();
			}
			else 
			{
				connect.stop();
				return "event is already present";
			}
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
            
            java.util.Date date = new java.util.Date();
   		 
   		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String cdate=sdf.format(date);
            //String query = "select * from Event where userID=? and end_date like ? ";
            String query = "select * from Event where userID=? ";
            PreparedStatement ps = connect.con.prepareStatement(query);
           
			ps.setInt(1,id);
	
			ResultSet result = ps.executeQuery();
		
			if (result != null) {
				
				while (result.next()) {
					String d=result.getString(8);
					System.out.println("hereeeeeeeeee"+ d);
					int x= cdate.compareTo(d);
					if(x<=0)
					{	Event el= new Event();
					el.seteID(result.getInt(1));
					el.setuID(result.getInt(2));
					el.setEname(result.getString(3));
					el.setHname(result.getString(4));
					el.setLocation(result.getString(5));
					el.setEvent_date(result.getString(6));
					el.setTime(result.getString(9));
					ael.add(el);
					}
					else System.out.println("Event is Expired");
				}
					connect.stop();
								
					
				}
			}
           
            
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ael;
	}
	
	public static String inviteUserforEvent(int eID,int userID) // user send event invitation to their friend
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
	
	
	public static String userResponseforEvent(int eID, int userID)	// user going for particular event
	{
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for Event service");
	            }
	            String query = "select * from EventUserlist where eID=? and userID=? ";
	            PreparedStatement ps = connect.con.prepareStatement(query);
	            ps.setInt(1,eID);
	            ps.setInt(2,userID);
	    			ResultSet result = ps.executeQuery();
	    			if(result.last())
	            
	    			{
	            query = "UPDATE EventUserlist SET status=? where eID=? and userID=?";
	             ps = connect.con.prepareStatement(query);
	            ps.setString(1,"Accepted");
	    			ps.setInt(2,eID);
	    			ps.setInt(3,userID);	
	    			ps.executeUpdate();
	    			}
	    			else
	    			{
	    				query="insert into EventUserlist(eID,userID,status) values (?,?,?)";
	    				ps = connect.con.prepareStatement(query);
	    				ps.setInt(1, eID);
	    				ps.setInt(2, userID);
	    				ps.setString(3, "Accepted");
	    				 ps.executeUpdate();
	    			}
	            connect.stop();
	    	
		
             }
		
	            catch (Exception e) {
	    			e.printStackTrace();
	    		
	    		}     
	            
		
		return "user event status updated";
	}
	
public static String eventDelete(int eID) // event is deleted by the admin user
{
	try {

    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection for Event service");
          }
          String query = "select userID from EventUserlist where eID=? ";
          PreparedStatement ps11 = connect.con.prepareStatement(query);
          ps11.setInt(1,eID);   
          ResultSet rs1= ps11.executeQuery();
         
          query = "select * from Event where eID=? ";
          PreparedStatement ps113 = connect.con.prepareStatement(query);
         ps113.setInt(1,eID); 
      
         ResultSet rs12= ps113.executeQuery();
         rs12.next(); 
         while(rs1.next())
          {
        	  
           query = "insert into EventNotification(eID,userID,ename,hname) values (?,?,?,?)";
           PreparedStatement    ps112 = connect.con.prepareStatement(query);
                   ps112.setInt(1, eID);
      	           ps112.setInt(2, rs1.getInt(1));	
      	           ps112.setString(3,rs12.getString(3));
      	           ps112.setString(4,rs12.getString(4));
                   ps112.executeUpdate();
              
          }
           query = "delete from EventUserlist  where eID=? ";
          PreparedStatement ps = connect.con.prepareStatement(query);
          ps.setInt(1,eID);
          ps.executeUpdate();
          query = "delete from EventReview  where eID=? ";
          PreparedStatement ps12 = connect.con.prepareStatement(query);
          ps12.setInt(1,eID);
          ps12.executeUpdate();
          
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


public static Event getYourEventDetail(int eID) // got event deatil for particular event
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
				eobj.setEvent_date(result.getString(6));
				 eobj.setEvent_end_date(result.getString(7));
				 eobj.setTime(result.getString(9));
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




public static ArrayList<Event> retriveInvitaionList(int userID) // your event invitation list that is pending 
  {
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
 java.util.Date date = new java.util.Date();
	   		 
	   		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String cdate=sdf.format(date);
			if(result!=null){
			while(result.next())
			{
				Event eobj=new Event();
				query="select * from Event where eID=?";
				ps=connect.con.prepareStatement(query);
				ps.setInt(1, result.getInt(1));
				ResultSet rs=ps.executeQuery();

	           
			   
				if (result != null) {
					
					while (rs.next()) {
						String d=rs.getString(8);
						int x= cdate.compareTo(d);
						if(x<=0)
						{	Event el= new Event();
						el.seteID(rs.getInt(1));
						el.setuID(rs.getInt(2));
						el.setEname(rs.getString(3));
						el.setHname(rs.getString(4));
						el.setLocation(rs.getString(5));
						el.setEvent_date(rs.getString(6));
						el.setTime(rs.getString(9));
						eil.add(el);
						}
						
						else System.out.println("Event is Expired");
					}
				
			}
		
			
			}
			}
			 query = "select * from Event where eventType=?";
		     PreparedStatement ps1 = connect.con.prepareStatement(query);
		     ps1.setString(1,"Public");
		     
					ResultSet rs = ps1.executeQuery();
			
					 
					 if(rs!=null){
						 while (rs.next()) {
								String d=rs.getString(8);
								int x= cdate.compareTo(d);
								if(x<=0)
								{	
									query = "select * from EventUserlist where eID=? and userID=?";
								     PreparedStatement ps2 = connect.con.prepareStatement(query);
								     ps2.setInt(1,rs.getInt(1));
								     ps2.setInt(2, userID);
										ResultSet rs1 = ps2.executeQuery();
										if(!rs1.last()){
								Event el= new Event();
								el.seteID(rs.getInt(1));
								el.setuID(rs.getInt(2));
								el.setEname(rs.getString(3));
								el.setHname(rs.getString(4));
								el.setLocation(rs.getString(5));
								el.setEvent_date(rs.getString(6));
								el.setTime(rs.getString(9));
								eil.add(el);
										}
								}
								
								else System.out.println("Event is Expired");
							}
							}
					 else System.out.println("no public event");
					 
					 
					 
			
	}

	catch (Exception e) {
		e.printStackTrace();
		
	}
	 ArrayList<Event> eil1= new ArrayList<Event>();
	for(int i=0;i<eil.size();i++)
	{
		if(eil.get(i).getuID()!=userID){
			eil1.add(eil.get(i));
			
		}
	}
	
	return eil1;
}



// user not going for particular event
public static void userRejectforEvent(int eID, int userID)
  {
	try {

    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection for Event service");
          }
          String query = "select * from EventUserlist where eID=? and userID=? ";
          PreparedStatement ps = connect.con.prepareStatement(query);
          ps.setInt(1,eID);
          ps.setInt(2,userID);
          
  			ResultSet result = ps.executeQuery();
  			if(result.last())
          
  			{
          query = "UPDATE EventUserlist SET status=? where eID=? and userID=?";
           ps = connect.con.prepareStatement(query);
          ps.setString(1,"Rejected");
  			ps.setInt(2,eID);
  			ps.setInt(3,userID);	
  			ps.executeUpdate();
  			}
  			else
  			{
  				query="insert into EventUserlist(eID,userID,status) values (?,?,?)";
  				ps = connect.con.prepareStatement(query);
  				ps.setInt(1, eID);
  				ps.setInt(2, userID);
  				ps.setString(3, "Rejected");
  				 ps.executeUpdate();
  			}
          connect.stop();
  	
	
	
       }
	
          catch (Exception e) {
  			e.printStackTrace();
  		
  		}  
	
	
}



// list of incoming event for particular user
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
		    java.util.Date date = new java.util.Date();
	   		 
	   		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String cdate=sdf.format(date);
		
			if(result!=null){
			while(result.next())
			{
				
				query="select * from Event where eID=?";
				ps=connect.con.prepareStatement(query);
				ps.setInt(1, result.getInt(1));
				ResultSet rs=ps.executeQuery();
			   
				while(rs.next())
				{
					String d=rs.getString(8);
					int x= cdate.compareTo(d);
					if(x<=0)
					{	Event el= new Event();
					el.seteID(rs.getInt(1));
					el.setuID(rs.getInt(2));
					el.setEname(rs.getString(3));
					el.setHname(rs.getString(4));
					el.setLocation(rs.getString(5));
					el.setEvent_date(rs.getString(6));
					el.setTime(rs.getString(9));
					eil.add(el);
					}
					else System.out.println("Event is Expired");
				  	
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



// friend list which is not invited
public static ArrayList<User> getFriendthatisNotinvited(ArrayList<User> u1,int eID) {
	
    ArrayList<User> l1= new ArrayList<User>();
	
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
				if(flag==0)
				l1.add(u1.get(i));
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
		
	
	
	
	
	return l1;
}



// user list that maybe come on particular event
public static void userMayBeComingforEvent(int eID, int userID) {
	
	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	System.out.println("trying connection for Event service");
        }
        
        String query = "select * from EventUserlist where eID=? and userID=? ";
        PreparedStatement ps = connect.con.prepareStatement(query);
        ps.setInt(1,eID);
        ps.setInt(2, userID);
      
			ResultSet result = ps.executeQuery();
			if(result.last())
        
			{
        query = "UPDATE EventUserlist SET status=? where eID=? and userID=?";
         ps = connect.con.prepareStatement(query);
        ps.setString(1,"maybe");
			ps.setInt(2,eID);
			ps.setInt(3,userID);	
			ps.executeUpdate();
			}
			else
			{
				query="insert into EventUserlist(eID,userID,status) values (?,?,?)";
				ps = connect.con.prepareStatement(query);
				ps.setInt(1, eID);
				ps.setInt(2, userID);
				ps.setString(3, "maybe");
				 ps.executeUpdate();
			}
        connect.stop();
	
	
     }
	
        catch (Exception e) {
			e.printStackTrace();
		
		} 
	
	
}




public static ArrayList<User> AlluserDetail1(int eID) // user list that maybe coming on particular event 
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
			ps.setString(2, "maybe");
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


public static ArrayList<User> AlluserDetail2(int eID) // user list that is not coming on particular event
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
			ps.setString(2, "Rejected");
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

// user list that have birthday from System date

public static ArrayList<User> getBirthdayList() // get all userlist that has birthday on current month
{
	ArrayList<User> udetail= new ArrayList<User>();
	try{
		 DBAccess connect = new DBAccess();
         boolean check=false;
         while(check==false)
         {
         	check=connect.start();
         	//System.out.println("trying connection for Event service");
         }
         String query = "select userID from User where dob like (SELECT DATE_FORMAT(curdate(), '%%%M%'));";
         PreparedStatement ps = connect.con.prepareStatement(query);
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
			System.out.println("no user  is  birthday on current month");
		}
        connect.stop(); 
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return udetail;
}

public static ArrayList<User> getTodayBirthdayList() // get all user that has today birthday 
{
	ArrayList<User> udetail= new ArrayList<User>();
	try{
		 DBAccess connect = new DBAccess();
         boolean check=false;
         while(check==false)
         {
         	check=connect.start();
         	//System.out.println("trying connection for Event service");
         }
         String query = "select userID from User where dob like (SELECT DATE_FORMAT(curdate(), '%e%M%'));";
         PreparedStatement ps = connect.con.prepareStatement(query);
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
			System.out.println("no user  is today birthday");
		}
        connect.stop(); 
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return udetail;
}


//method for  insert review event 

public static String insertReview(int eID, int userID, String status_desc) {
	

	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	//System.out.println("trying connection for Event service");
        }
        String query = "insert into EventReview(eID,userID,review_desc) values (?,?,?)";
        PreparedStatement ps = connect.con.prepareStatement(query);
       ps.setInt(1, eID);
       ps.setInt(2, userID);
       ps.setString(3, status_desc);   
	
        ps.executeUpdate();
        connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return "review insert in database";
}


//method for getting Event Review for particular event

public static ArrayList<EventReview> getEventReview(int eID) {

	ArrayList<EventReview> erl = new ArrayList<EventReview>();

	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	//System.out.println("trying connection for Event service");
        }
    
        String query="select * from EventReview where eID=?";
	PreparedStatement	ps = connect.con.prepareStatement(query);
		ps.setInt(1, eID);

		ResultSet result = ps.executeQuery();
        while(result.next())
        {
          	EventReview eobj= new EventReview();
          	eobj.setReview_desc(result.getString(2));
        	eobj.seteID(result.getInt(4));
        	eobj.setUserID(result.getInt(5));
        	java.sql.Timestamp timestamp2 = result.getTimestamp(3);
     
        	eobj.setT(timestamp2);
        
        	 User u1= new User();
             u1=RetriveService.getUserAllDataByUserID(eobj.getUserID());
        eobj.setU1(u1);
        //status_obj.setName(rs11.getString("fname")+" "+ rs11.getString("lname"));
         eobj.setName(u1.getFname()+" "+u1.getLname());
        erl.add(eobj);
        }
        
        connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	
	return erl;
}



//method for geting notification when event is deleted
public static ArrayList<Event> getEventNotification(int id) {
	ArrayList<Event> e1= new ArrayList<Event>();
	
	try {

	  	  DBAccess connect = new DBAccess();
	        boolean check=false;
	        while(check==false)
	        {
	        	check=connect.start();
	        	//System.out.println("trying connection for Event service");
	        }
   String query="select * from EventNotification where userID=?";	    
	     PreparedStatement ps= connect.con.prepareStatement(query);   
	    ps.setInt(1, id);
	      ResultSet rs= ps.executeQuery();
	      while(rs.next()){
	    	  Event eobj= new Event();
	    	  eobj.seteID(rs.getInt(1));
	    	  eobj.setuID(rs.getInt(2));
	    	  eobj.setEname(rs.getString(3));
	    	  eobj.setHname(rs.getString(4));
	    	  
	    	  e1.add(eobj);
	      }
	      query="delete from EventNotification where userID=?";
	         ps= connect.con.prepareStatement(query);   
		    ps.setInt(1, id);
		    ps.executeUpdate();
	        connect.stop();
	        return e1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	
	return e1;
}



//method  when event is updated
public static String updateEvent(Event eobj) {
    int count=0;
	StringBuilder sb= new StringBuilder("UPDATE Event SET ");
	if(eobj.getEname().length()>0)
	{	
		sb.append("ename='"+eobj.getEname()+ "',"); 
        count++;
	}
	if(eobj.getHname().length()>0)
	{	sb.append("hname='"+eobj.getHname()+ "',");  count++; }
	if(eobj.getLocation().length()>0)
	{	sb.append("location='"+eobj.getLocation()+ "',");  count++;  }
	if(eobj.getEvent_date().length()>0)
	{	sb.append("create_date='"+eobj.getEvent_date()+ "',"); count++;}
	if(eobj.getEvent_end_date().length()>0)
	{	sb.append("end_date='"+eobj.getEvent_end_date()+ "',"); count++;}
	if(eobj.getTime().length()>0)
	{	sb.append("time='"+eobj.getTime()+ "',"); count++;}
	sb.append("where eID="+eobj.geteID());
    if(count>0){
	   int index= sb.lastIndexOf(",");
       sb.deleteCharAt(index);
    }
	String str= sb.toString();
	System.out.println(str);
	try 
    {
    	  DBAccess connect = new DBAccess();
          boolean check=false;
          while(check==false)
          {
          	check=connect.start();
          	System.out.println("trying connection in update");
          }
          
          String query1 = "select userID from EventUserlist where eID=? and status = ? ";
          PreparedStatement ps11 = connect.con.prepareStatement(query1);
          ps11.setInt(1,eobj.geteID());   
          ps11.setString(2,"Accepted");
          ResultSet rs1= ps11.executeQuery();
         
          String query2 = "select * from Event where eID=? ";
          PreparedStatement ps113 = connect.con.prepareStatement(query2);
         ps113.setInt(1,eobj.geteID()); 
      
         ResultSet rs12= ps113.executeQuery();
         rs12.next(); 
         if(count>0)
         connect.con.createStatement().executeUpdate(str);
         
       /*  
         String query = "UPDATE Event SET "
         		+ "ename=?,hname=?,location=?,"
         		+ "create_date=?,end_date=? , time=?  where eID = ?";
              PreparedStatement ps = connect.con.prepareStatement(query);
              ps.setString(1,eobj.getEname());
      		ps.setString(2,eobj.getHname());
      		ps.setString(3, eobj.getLocation());
      		ps.setString(4, eobj.getEvent_date());
      		ps.setString(5, eobj.getEvent_end_date());
           	ps.setString(6, eobj.getTime());
           	ps.setInt(7, eobj.geteID());
      	
      		
      		ps.executeUpdate();*/
         while(rs1.next())
          {
        	  
          String query3 = "insert into EventUpdateNotification(eID,userID,ename,hname) values (?,?,?,?)";
           PreparedStatement    ps112 = connect.con.prepareStatement(query3);
                   ps112.setInt(1, eobj.geteID());
      	           ps112.setInt(2, rs1.getInt(1));	
      	           ps112.setString(3,rs12.getString(3));
      	           ps112.setString(4,rs12.getString(4));
                   ps112.executeUpdate();
              
          }
          
          
          
   
		check=connect.stop();
        
    } 
    catch (Exception e) 
    {
        System.out.println(e.getMessage());
    }

	return "Event is updated";
}



// method for geting notification when event is updated
public static ArrayList<Event> getEventupdateNotification(int id) {
ArrayList<Event> e1= new ArrayList<Event>();
	
	try {

	  	  DBAccess connect = new DBAccess();
	        boolean check=false;
	        while(check==false)
	        {
	        	check=connect.start();
	        	//System.out.println("trying connection for Event service");
	        }
   String query="select * from EventUpdateNotification where userID=?";	    
	     PreparedStatement ps= connect.con.prepareStatement(query);   
	    ps.setInt(1, id);
	      ResultSet rs= ps.executeQuery();
	      while(rs.next()){
	    	  Event eobj= new Event();
	    	  eobj.seteID(rs.getInt(1));
	    	  eobj.setuID(rs.getInt(2));
	    	  eobj.setEname(rs.getString(3));
	    	  eobj.setHname(rs.getString(4));
	    	  
	    	  e1.add(eobj);
	      }
	      query="delete from EventUpdateNotification where userID=?";
	         ps= connect.con.prepareStatement(query);   
		    ps.setInt(1, id);
		    ps.executeUpdate();
	        connect.stop();
	        return e1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	
	return e1;
}




public static String insertSummary(int eID, int userID, String status_desc) {
	try {

	  	  DBAccess connect = new DBAccess();
	        boolean check=false;
	        while(check==false)
	        {
	        	check=connect.start();
	        	System.out.println("trying connection for Event service");
	        }
	        
	       
	        
				
	       String query = "UPDATE Event SET summary=? where eID=? and userID=?";
	        PreparedStatement ps = connect.con.prepareStatement(query);
	        ps.setString(1,status_desc);
				ps.setInt(2,eID);
				ps.setInt(3,userID);	
				ps.executeUpdate();
				
			
	        connect.stop();
	        return "sucess";
		
		
	     }
		
	        catch (Exception e) {
				e.printStackTrace();
			
			} 
	return null;
}




public static String getEventSummary(int eID) {


	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	//System.out.println("trying connection for Event service");
        }
    
        String query="select summary from Event where eID=?";
	PreparedStatement ps = connect.con.prepareStatement(query);
		ps.setInt(1, eID);
       String s=null;
		ResultSet result = ps.executeQuery();
        while(result.next())
        {
          	s=result.getString(1);
          //	System.out.println("the value of summary is "+s);
        }
        
        connect.stop();
        return s;
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}




public static ArrayList<Event> retriveInvitaionList2(int userID) {
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
		    java.util.Date date = new java.util.Date();
	   		 
	   		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String cdate=sdf.format(date);
		
			if(result!=null){
			while(result.next())
			{
				
				query="select * from Event where eID=?";
				ps=connect.con.prepareStatement(query);
				ps.setInt(1, result.getInt(1));
				ResultSet rs=ps.executeQuery();
			   
				while(rs.next())
				{
					String d=rs.getString(8);
					int x= cdate.compareTo(d);
					if(!(x<=0))
					{	Event el= new Event();
					el.seteID(rs.getInt(1));
					el.setuID(rs.getInt(2));
					el.setEname(rs.getString(3));
					el.setHname(rs.getString(4));
					el.setLocation(rs.getString(5));
					el.setEvent_date(rs.getString(6));
					el.setTime(rs.getString(9));
					eil.add(el);
					}
					else System.out.println("Event is Expired");
				  	
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




public static ArrayList<Event> retriveInvitaionList3(int userID) {
ArrayList<Event> eil=new ArrayList<Event>();
	
	
	try {

  	  DBAccess connect = new DBAccess();
        boolean check=false;
        while(check==false)
        {
        	check=connect.start();
        	System.out.println("trying connection for Event service");
        }
        String query = "select * from Event where userID=? ";
        PreparedStatement ps = connect.con.prepareStatement(query);
        ps.setInt(1,userID);
			ResultSet rs = ps.executeQuery();
 java.util.Date date = new java.util.Date();
	   		 
	   		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String cdate=sdf.format(date);
		
		
			while(rs.next())
			{
				
				String d=rs.getString(8);
				int x= cdate.compareTo(d);
				if(!(x<=0))
				{
					Event el= new Event();
					el.seteID(rs.getInt(1));
					el.setuID(rs.getInt(2));
					el.setEname(rs.getString(3));
					el.setHname(rs.getString(4));
					el.setLocation(rs.getString(5));
					el.setEvent_date(rs.getString(6));
					el.setTime(rs.getString(9));
					eil.add(el);
				}
			}
			
			
			connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
		
	}
	
	
	
	return eil;
}




public static int IsadminorNot(int eID, int userID) {
	int flag=0;
	try {
          
	  	  DBAccess connect = new DBAccess();
	        boolean check=false;
	        while(check==false)
	        {
	        	check=connect.start();
	        //	System.out.println("trying connection for Event service");
	        }
	        String query = "select * from Event where userID=? and eID=? ";
	        PreparedStatement ps = connect.con.prepareStatement(query);
	        ps.setInt(1,userID);
	        ps.setInt(2,eID);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					flag=1;
					break;
				}
				connect.stop();
	}
	catch (Exception e) {
		e.printStackTrace();
		
	}
	return flag;
}	
	


}