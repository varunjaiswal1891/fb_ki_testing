
package com.varun.fbproj.service;
import java.util.Calendar;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.varun.fbproj.model.Comment;
import com.varun.fbproj.model.Group;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;

public class GroupService {
//This function is used to insert group name and group owner into Group1 table
public static boolean createGroup(String group_name,String emailID,String privacy)
{
try {

       DBAccess connect = new DBAccess();
           boolean check=false;
           while(check==false)
           {
            check=connect.start();
            System.out.println("trying connection for creating group");
           }
           String query = "insert into Group1(group_name,owner,privacy) values (?,?,?)";
           PreparedStatement ps = connect.con.prepareStatement(query);
           ps.setString(1,group_name);
ps.setString(2,emailID);
ps.setString(3,privacy);
//it is the emailID of owner 
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
//This function is used to add members to Usergroup table
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

//This function is used to get group name of all groups
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

//This function is used for deleting group
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
           
           String query1="select owner from Group1 where group_name=?";
           PreparedStatement ps1 = connect.con.prepareStatement(query1);
           ps1.setString(1,group_name);
           ResultSet result = ps1.executeQuery();
while (result.next()) {
    String e1=result.getString("owner");
System.out.println("e1="+e1);
if(!e1.equals(emailID))  //not owner so delete from group
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

//This function is used to get status using group name
public  static ArrayList<Status> getStatusByGroup(String group_name,String loggedInEmailID){
String postedon="";
 
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
                    File outputfile = new File("/home/vishal/git/fb_ki_testing/src/main/webapp/users/"+status_obj.getEmailID()+"/"+status_obj.getStatusID()+".jpg/");
if(outputfile.exists())
status_obj.setFlag(0);
java.sql.Timestamp timestamp2 = rs.getTimestamp(3);
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
                     
                         
                         System.out.println("insiiiiiiiiiiiiiiiiiiiide group like"+loggedInEmailID);
            String query1333 = "select * from likes where statusID = ? and emailID=?";
PreparedStatement pstmnt1333=db.con.prepareStatement(query1333);
pstmnt1333.setInt(1,stID); // user_id is the one sent in paramater
pstmnt1333.setString(2,loggedInEmailID);
ResultSet rs1333= pstmnt1333.executeQuery();
if(rs1333.next()){// System.out.println("insiiiiiiiiiiiiiiiiiiiide group like");
           
status_obj.setColor(1);
}
else{
status_obj.setColor(0);
}
 
Calendar calendar = Calendar.getInstance();
   java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
   long milliseconds = ourJavaTimestampObject.getTime() - timestamp2.getTime();
   int seconds = (int) milliseconds / 1000;
 
   // calculate hours minutes and seconds
   int hours = seconds / 3600;
   int minutes = (seconds % 3600) / 60;
   seconds = (seconds % 3600) % 60;
 
 
   System.out.println("timestamp1: " + ourJavaTimestampObject);
   System.out.println("timestamp2: " + timestamp2);
 
   System.out.println("Difference: ");
   System.out.println(" Hours: " + hours);
   System.out.println(" Minutes: " + minutes);
   System.out.println(" Seconds: " + seconds);   
if(hours==0)
{
if(minutes==0)
{ 
postedon=" Just now";
status_obj.setPostedon(postedon);
}
else
{
postedon=Integer.toString(minutes);
if(minutes==1)
postedon=postedon.concat(" minute ago");
else  
postedon=postedon.concat(" minutes ago");
status_obj.setPostedon(postedon);
 
 
}
 
 
}
 
else if((hours>=1)&&(hours<24))
{
postedon=Integer.toString(hours);
if(hours==1)
postedon=postedon.concat(" hour ago");
else
postedon=postedon.concat(" hours ago");
status_obj.setPostedon(postedon);
 
}
else
{
int Days=hours/24;
postedon=Integer.toString(Days);
if(Days==1)
postedon=postedon.concat(" day ago");
else
postedon=postedon.concat(" days ago");
status_obj.setPostedon(postedon);
 
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
                         
                            File outputfile = new File("/home/vishal/git/fb_ki_testing/src/main/webapp/users/"+status_obj.getEmailID()+"/"+status_obj.getStatusID()+".jpg/");
        if(outputfile.exists())
        status_obj.setFlag(0);
       
        java.sql.Timestamp timestamp2 = rs.getTimestamp(3);
       
                            
                            
                            
                            
                            
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
                             
                                 
                                 String query1333 = "select * from likes where statusID = ? and emailID=?";
        PreparedStatement pstmnt1333=db.con.prepareStatement(query1333);
        pstmnt1333.setInt(1,stID); // user_id is the one sent in paramater
        pstmnt1333.setString(2,loggedInEmailID);
        ResultSet rs1333= pstmnt1333.executeQuery();
        if(rs1333.next()){// System.out.println("insiiiiiiiiiiiiiiiiiiiide group like");
                   
        status_obj.setColor(1);
        }
        else{
        status_obj.setColor(0);
        }
       
         
        Calendar calendar = Calendar.getInstance();
           java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
           long milliseconds = ourJavaTimestampObject.getTime() - timestamp2.getTime();
           int seconds = (int) milliseconds / 1000;
         
           // calculate hours minutes and seconds
           int hours = seconds / 3600;
           int minutes = (seconds % 3600) / 60;
           seconds = (seconds % 3600) % 60;
         
         
           System.out.println("timestamp1: " + ourJavaTimestampObject);
           System.out.println("timestamp2: " + timestamp2);
         
           System.out.println("Difference: ");
           System.out.println(" Hours: " + hours);
           System.out.println(" Minutes: " + minutes);
           System.out.println(" Seconds: " + seconds);   
        if(hours==0)
        {
        if(minutes==0)
        { 
       
        postedon=" Just now";
       
        status_obj.setPostedon(postedon);
        }
        else
        {
        postedon=Integer.toString(minutes);
        if(minutes==1)
        postedon=postedon.concat(" minute ago");
        else  
        postedon=postedon.concat(" minutes ago");
        status_obj.setPostedon(postedon);
         
         
        }
         
         
        }
         
        else if((hours>=1)&&(hours<24))
        {
       
        postedon=Integer.toString(hours);
        if(hours==1)
        postedon=postedon.concat(" hour ago");
        else
        postedon=postedon.concat(" hours ago");
       
        status_obj.setPostedon(postedon);
         
        }
        else
        {
        int Days=hours/24;
       
        postedon=Integer.toString(Days);
        if(Days==1)
        postedon=postedon.concat(" day ago");
        else
        postedon=postedon.concat(" days ago");
       
        status_obj.setPostedon(postedon);
         
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

//This function is used to get group members using group name 
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


//This function is used to get group admin using group name
public  static User getGroupAdmin(String group_name){
    
String result;
DBAccess db= new DBAccess();
boolean check=false;
 try{
     while(check!=true){
 System.out.println("trying connection in get all members of group");
 check= db.start();
 }
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
public static String getPrivacy(String gname) {
	String privacy="";
	
	DBAccess db= new DBAccess();
    boolean check=false;
 try{
     while(check!=true){
      System.out.println("trying connection in privacy");
      check= db.start();
 }
     
     //getting the group names from which the user has left
 String sql="select privacy from Group1 where group_name=?";

 PreparedStatement pstmnt=db.con.prepareStatement(sql);
   pstmnt.setString(1,gname);
 ResultSet rs= pstmnt.executeQuery();

 if(rs!=null){
      
      while (rs.next()) {
          
          privacy=rs.getString(1);
           
    
        }
 }
 
 db.stop();
}
catch(Exception e){
    
}

	
	
	
	return privacy;
}
}//class ends