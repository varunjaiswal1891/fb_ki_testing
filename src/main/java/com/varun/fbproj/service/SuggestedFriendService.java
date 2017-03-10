package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.varun.fbproj.model.User;

public class SuggestedFriendService {

	public static ArrayList<User> getSuggestedFriends(ArrayList<User> al_friends,String myEmailID)
	{
		
		try {

	      	  DBAccess connect = new DBAccess();
	            boolean check=false;
	            while(check==false)
	            {
	            	check=connect.start();
	            	System.out.println("trying connection for suggested friends");
	            }
	            
	            
	            PreparedStatement prepStatement = connect.con.prepareStatement("select u2.emailID from User u1,User u2 where u1.emailID=? and u1.emailID!=u2.emailID and (u1.college=u2.college or u1.hometown=u2.hometown or u1.highschool=u2.highschool)");
				
				prepStatement.setString(1,myEmailID);
				ResultSet result = prepStatement.executeQuery();
				
				//if (result .next()) {
					while (result.next()) {
				
					String e1=result.getString("emailID");
					User u_obj=new User();	
					u_obj=RetriveService.getUserAllData(e1);				
					//adding user objects of my suggested to arraylist
					al_friends.add(u_obj);
					}
				//}//if ends	
				
				
				for(int j=0;j<al_friends.size();j++)
				{
					String e2=al_friends.get(j).getEmailID();
					System.out.println("e2="+e2);
					if(IsMyFriendService.isMyFriend(myEmailID, e2))
					{
						System.out.println("already a friend. so dont suggest this to me");
						
						Iterator<User> iter = al_friends.iterator();
						while (iter.hasNext()) 
						{
						    User user = iter.next();
						    if(user.getEmailID().equals(e2))
						    {
						        //Use iterator to remove this User object.
						        iter.remove();
						    }
						}
						
					}
					
					
					
				}//for loop j wala end
			
				connect.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		return al_friends;
	}
	
	
}//class ends here
