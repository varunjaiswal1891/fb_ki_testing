package com.varun.fbproj.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.varun.fbproj.model.User;

public class SuggestedFriendService {
		//This function is used to get list of suggested friends
	public static ArrayList<User> getSuggestedFriends(ArrayList<User> al_suggested_users,String myEmailID)
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
				
				/***Adding all suggested users******/
					while (result.next()) {
				
					String e1=result.getString("emailID");
					User u_obj=new User();	
					u_obj=RetriveService.getUserAllData(e1);				
					System.out.println("-------Adding this entry:--------"+u_obj.getEmailID());
					al_suggested_users.add(u_obj);
					}   
					
					
					System.out.println("the matched frnds are");
					for(User u:al_suggested_users){
						System.out.print(u.getEmailID()+"   ");
					}
					
		        /****suggested users entry done according to match*******/	
					
					
					
					
					
				/****finding list of frnds from myEmailID and removing them from suggested users *******/
						
				    ArrayList<User> al_friends=new ArrayList(); //list of all frnds
				    al_friends=GetMyAllFriends.getMyFriends(al_friends, myEmailID);
				    //removng all frnds from al_suugested_users
				    for(int i=0;i<al_friends.size();i++){
				    	 String eid_friend=al_friends.get(i).getEmailID();
				    	 System.out.println(eid_friend+" "+"already a friend. so dont suggest this to me");
							//al_friends.remove(j);
							Iterator<User> iter = al_suggested_users.iterator();
							while (iter.hasNext()) 
							{
							    User user = iter.next();
							    if(user.getEmailID().equals(eid_friend))
							    {
							        //Use iterator to remove this User object.
							        iter.remove();
							    }
							}
				       } //after completion all frnds will be removed from suggested users
				    
	         /****finding list of frnds from myEmailID and removing them from suggested users DONE*******/
				    
				    
				    //now task is to remove all frnds of frnds from suggested users as they will be show in 
				    //people u  may know
	        /****finding list of frnds of frnds from myEmailID and removing them from suggested users *******/
				    for(int i=0;i<al_friends.size();i++)
					{
						String e1=al_friends.get(i).getEmailID(); // e1 is a frnd
						ArrayList<User> temp=new ArrayList<User>();
						System.out.println("fetching all my friends k frnds list");
						//---temp an array list of frnds of frnds---//
						temp=GetMyAllFriends.getMyFriends(temp,e1);
						System.out.println("temp before="+temp);
						
						Iterator<User> iter = temp.iterator();
						/*for(int j=0;j<temp.size();j++){
						 *    if(temp.get(j).getEmailID()==myEmailID){
						 *       temp.remove(j);
						 * }
						 */
						
						
						/***here we are removing the self entry i.e frnds ki frnd list me mera bhi naam rhega wo hatao****/
						while (iter.hasNext()) 
						{
						    User user = iter.next();
						    if(user.getEmailID().equals(myEmailID))
						    {
						        //Use iterator to remove this User object.
						        iter.remove();
						    }
						}					
						System.out.println("temp after removing self entry="+temp);
						
						
						for(int j=0;j<temp.size();j++)
						{
							String e2=temp.get(j).getEmailID();
							System.out.println("e2="+e2);
							/** if frnd ka frnd is not my frnd then remove it from suggested frnds coz it will appear in people umay know**/
							if(!IsMyFriendService.isMyFriend(myEmailID, e2))
							{
								System.out.println("yes remove "+e2+" from al_suggested users");
								Iterator<User> iter1 = al_suggested_users.iterator();
								while (iter1.hasNext()) 
								{
								    User user = iter1.next();
								    if(user.getEmailID().equals(e2))
								    {
								        //Use iterator to remove this User object.
								        iter1.remove();
								    }
								}
							}			
						}//for loop j wala end
				    
					} // frnds of frnds removal done i loop over
				    
			/****DONE finding list of frnds of frnds from myEmailID and removing them from suggested users *******/
			
				    
				    
				    
			/***removing all sent requests and received requests*********************************/
				    for(int j=0;j<al_suggested_users.size();j++)
					{
						String e2=al_suggested_users.get(j).getEmailID();  // e2 will contain the email id from which some may belong to frnds or request already sent or received or people u mayknow
						System.out.println("e2="+e2);    
                        if(IsRequestAlreadySentService.isRequestAlreadySent(myEmailID,e2)){
						
						System.out.println("already sent request. so dont suggest this to me");
						//al_friends.remove(j);
						Iterator<User> iter = al_suggested_users.iterator();
						while (iter.hasNext()) 
						{
						    User user = iter.next();
						    if(user.getEmailID().equals(e2))
						    {
						        //Use iterator to remove this User object.
						        iter.remove();
						    }
						}
						 
					 	
					 } //  IsRequestAlreadySentService.isRequestAlreadySent ends
                        else{
                        	if(IsRequestAlreadyReceived.isRequestAlreadyReceived(myEmailID,e2)){
    							
    							System.out.println("already received  request. so dont suggest this to me");
    							//al_friends.remove(j);
    							Iterator<User> iter = al_suggested_users.iterator();
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
                        	else
							{
							  System.out.println(al_suggested_users.get(j).getEmailID()+"not a frnd");}
							}
				    
					} 
				    
				    
			/*** Done removing all sent requests and received requests*********************************/
				    
					connect.stop();
		
					
				
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		return al_suggested_users;
	}
	
	
}//class ends here
