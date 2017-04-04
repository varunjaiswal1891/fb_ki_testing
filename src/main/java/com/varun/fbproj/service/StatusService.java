package com.varun.fbproj.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.ws.rs.CookieParam;

import javassist.bytecode.Descriptor.Iterator;

import com.varun.fbproj.model.Comment;
import com.varun.fbproj.model.Likes;
import com.varun.fbproj.model.Status;
import com.varun.fbproj.model.User;
//this is service class for all status related work
public class StatusService {
int statusid12=1;
DBAccess db= new DBAccess();
public int addStatus1(Status s1){//this function adds status to the status table
 try{
boolean check=false;
while(check!=true){
System.out.println("trying connection");
check= db.start();
}
 
String  status_desc=s1.getStatus_desc(); 
String emailID=s1.getEmailID();
String feeling=s1.getFeeling();
String timelineid=s1.getTimelineid();
System.out.println("insiddddddddddddddddddddddde adddddstatusssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
String query = "insert into status(status_desc,emailID,group_name,feeling,timelineid) values(?,?,?,?,?)";
PreparedStatement pstmnt = db.con.prepareStatement(query);
pstmnt.setString(1,status_desc);
pstmnt.setString(2,emailID);
pstmnt.setString(3,s1.getGroup_name());
pstmnt.setString(4,feeling);
pstmnt.setString(5,timelineid);
pstmnt.executeUpdate();
 
 
String query134 = "select max(statusID) as statusID from  status where   emailID=?";
PreparedStatement pstmnt134=db.con.prepareStatement(query134);
pstmnt134.setString(1,emailID);
ResultSet rs134= pstmnt134.executeQuery();
if(rs134.next()){
statusid12=rs134.getInt("statusID");
System.out.println("aaajaaaaaayarrrrrrrrrrrrrrrr"+statusid12);
}
 db.stop();
return  statusid12;
 }
 catch (Exception e) 
     {
         System.out.println(e.getMessage());
     }
return statusid12 ;
 
  }//add status ends here
 
public int addStatus(Status s1){
         try{
            boolean check=false;
            while(check!=true){
                System.out.println("trying connection ppppppppppppppppp");
               check= db.start();
            }
            String  status_desc=s1.getStatus_desc(); 
String emailID=s1.getEmailID();
String feeling=s1.getFeeling();
String timelineid=s1.getTimelineid();
 
            String query1="select privacy from Group1 where group_name=?";
            PreparedStatement pstmnt1 = db.con.prepareStatement(query1);
            pstmnt1.setString(1, s1.getGroup_name());
            ResultSet rs5= pstmnt1.executeQuery();
            rs5.next();
            String privacy = rs5.getString("privacy");
            System.out.println(privacy);
           // int flag=0;
            if(privacy.equals("public"))
            {
            String query = "insert into status(status_desc,emailID,group_name,feeling,timelineid) values(?,?,?,?,?)";
    PreparedStatement pstmnt = db.con.prepareStatement(query);
    pstmnt.setString(1,status_desc);
    pstmnt.setString(2,emailID);
    pstmnt.setString(3,s1.getGroup_name());
    pstmnt.setString(4,feeling);
    pstmnt.setString(5,timelineid);
    
    pstmnt.executeUpdate();
     
     
    String query134 = "select max(statusID) as statusID from  status where   emailID=?";
    PreparedStatement pstmnt134=db.con.prepareStatement(query134);
    
    pstmnt134.setString(1,emailID);
    
    ResultSet rs134= pstmnt134.executeQuery();
    if(rs134.next()){
    statusid12=rs134.getInt("statusID");
    System.out.println("aaajaaaaaayarrrrrrrrrrrrrrrr"+statusid12);
    }
    
     db.stop();
    return  statusid12;
      
            }
            else
            {
            System.out.println("in private else if oooooooooooooooooooo");	
            String query = "insert into privategroupstatus(status_desc,emailID,group_name,feeling,timelineid) values(?,?,?,?,?)";
      PreparedStatement pstmnt = db.con.prepareStatement(query);
      pstmnt.setString(1,status_desc);
      pstmnt.setString(2,emailID);
      pstmnt.setString(3,s1.getGroup_name());
      pstmnt.setString(4,feeling);
      pstmnt.setString(5,timelineid);
     
      pstmnt.executeUpdate();
       
       
      String query134 = "select max(statusID) as statusID from  privategroupstatus where   emailID=?";
      PreparedStatement pstmnt134=db.con.prepareStatement(query134);
     
      pstmnt134.setString(1,emailID);
     
      ResultSet rs134= pstmnt134.executeQuery();
      if(rs134.next()){
      statusid12=rs134.getInt("statusID");
      System.out.println("aaajaaaaaayarrrrrrrrrrrrrrrr"+statusid12);
      }
     
       db.stop();
      return  statusid12;
         }
         }
         catch (Exception e) 
         {
             System.out.println(e.getMessage());
         }
        return statusid12;  
      }//add status ends here
 
 
 
 
 
//abhi incomplete h ye removeStatus
public boolean removeStatus(Status s1){//This function removes status but currently not in use
try{
boolean check=false;
while(check!=true){
System.out.println("trying connection");
check= db.start();
}
//String status_id=s1.getStatusID();
String query = "UPDATE status SET flag=? where statusID = ?";
        PreparedStatement pstmnt = db.con.prepareStatement(query);
        pstmnt.setInt(1,0);
pstmnt.setInt(2,s1.getStatusID());
pstmnt.executeUpdate();
db.stop();
return true;
 }
 catch (Exception e) 
     {
         System.out.println(e.getMessage());
     }
return false;  
  }//removeStatus ends here
 
 
 
 
 
//this method is VERY IMPORTANT
//given an email ID ..it returns all status of that guy and all status of his friends
//along with all comments and likes on every status
 
public ArrayList<Status> getAllDetailsOfEachStatus(String emailID,String timelineID,String loggedInEmailID) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
{
 ArrayList<Status> statusArrayList = new ArrayList<Status>();
String feel1="is feeling ";
String feel2="";
String posted0="";
String postedon="";
 
   System.out.println("this is the email id of logged in person: "+loggedInEmailID);
   
   
System.out.println("eamil ddd  "+emailID);
System.out.println("timelineid "+timelineID);
String rs1;
boolean check=false;
   

   try{
 while(check!=true){
 System.out.println("trying connection in getStatus");
check= db.start();
 }
   
 String query1="select * from status where emailID=? and timelineid= ?";   
 PreparedStatement pstmnt=db.con.prepareStatement(query1);
 pstmnt.setString(1,emailID); // user_id is the one sent in paramater
 pstmnt.setString(2,timelineID); // timelineid is the one sent in paramater
 
 ResultSet rs= pstmnt.executeQuery();  
 
 
       while (rs.next())
       {   System.out.println("inside first result set");
        Status status_obj = new Status();
status_obj.setStatusID(rs.getInt(1));
status_obj.setStatus_desc(rs.getString(2));
status_obj.setCreated(String.valueOf(rs.getTimestamp(3)));
status_obj.setEmailID(emailID);
status_obj.setFlag(rs.getInt(5));
status_obj.setTimelineid(rs.getString("timelineid"));
java.sql.Timestamp timestamp2 = rs.getTimestamp(3);
 
if((rs.getString("feeling"))!=null)
{feel2=rs.getString("feeling");
feel1=feel1.concat(feel2);
status_obj.setFeeling(feel1);}
else 
status_obj.setFeeling(null);
//statusArrayList.add(status_obj); 
feel1="is feeling ";
        System.out.println("eeeeeeeeeeeeeeeeeeeeee"+rs.getString(2));
 String query11="select fname,lname from User where emailID=?";   
 PreparedStatement pstmnt11=db.con.prepareStatement(query11);
 pstmnt11.setString(1,emailID); // user_id is the one sent in paramater
 ResultSet rs11= pstmnt11.executeQuery();
 rs11.next();
 status_obj.setName(rs11.getString("fname")+" "+ rs11.getString("lname"));
System.out.println("name =  "+status_obj.getName());
if((!(rs.getString("timelineid").equals("home")))&&((rs.getString("timelineid")!=null)))
{
String query111="select fname,lname from User where emailID=?";   
PreparedStatement pstmnt111=db.con.prepareStatement(query111);
pstmnt111.setString(1,rs.getString("timelineid")); // user_id is the one sent in paramater
ResultSet rs111= pstmnt111.executeQuery();
rs111.next();
if(rs.getString("timelineid").equals(rs.getString("emailID")))
{
posted0="posted on his own timeline";
status_obj.setName2(posted0);
posted0="";
 
 
}
else if(!(rs.getString("timelineid").equals(rs.getString("emailID"))))
{posted0="Posted on ";
posted0=posted0.concat(rs111.getString("fname"));
posted0=posted0.concat(" ");
posted0=posted0.concat(rs111.getString("lname"));
posted0=posted0.concat("'s timeline");
//posted2=posted2.concat(posted1);
//System.out.println("posted0000000000000000000000000000000000000000000000000000"+posted0);
status_obj.setName2(posted0);
posted0="";
}
 //status_obj.setName(rs111.getString("fname")+" "+ rs111.getString("lname"));
}
else if(rs.getString("timelineid").equals("home"))
{
status_obj.setName2(null);
System.out.println("inside home status");
}
if(rs.getString("timelineid").equals("group"))
{ posted0=posted0.concat("Posted in");
   posted0=posted0.concat(rs.getString("group_name"));
status_obj.setGroup_name(posted0);
posted0="";
}
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

           
            
      /*********LikesColor***************/      
            
           
String query133 = "select * from likes where statusID = ? and emailID=?";
PreparedStatement pstmnt133=db.con.prepareStatement(query133);
pstmnt133.setInt(1,stID); // user_id is the one sent in paramater
pstmnt133.setString(2,loggedInEmailID);
ResultSet rs133= pstmnt133.executeQuery();
if(rs133.next()){
status_obj.setColor(1);
}
else{
status_obj.setColor(0);
}
/*********time of posting***************/      
       
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
if(Days<365)
{
postedon=Integer.toString(Days);
if(Days==1)
postedon=postedon.concat(" day ago");
else
postedon=postedon.concat(" days ago");
status_obj.setPostedon(postedon);
}
else
{ 
int years=Days/365;
postedon=Integer.toString(years);
if(years==1)
postedon=postedon.concat(" year ago");
else
postedon=postedon.concat(" years ago");
status_obj.setPostedon(postedon);
}
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
  

   return statusArrayList;
 
}//method getAllDetailsOfEachStatus ends here
 
 
 
public  ArrayList<User> getStatusLikesName(int statusid){//This function is used to get name of people who liked the status
ArrayList<User> userobjlist = new ArrayList<User>(); 
 
try{
boolean check=false;
while(check!=true){
System.out.println("trying connection");
check= db.start();
}
 
String query15= "select emailID from likes where statusID=? and flag=?";
PreparedStatement pstmnt15 = db.con.prepareStatement(query15);
pstmnt15.setInt(1,statusid);
pstmnt15.setInt(2,1);
ResultSet rs15= pstmnt15.executeQuery();
 while(rs15.next())
{User u=new User();
//System.out.println("resultset"+rs15.getString("emailID"));
String query116= "select * from User where emailID=?";
PreparedStatement pstmnt116 = db.con.prepareStatement(query116);
//System.out.println(rs15.getString("emailID"));
pstmnt116.setString(1,rs15.getString("emailID"));
//System.out.println("qazwsxedcrfvtgb");
ResultSet rs116= pstmnt116.executeQuery();
//System.out.println("qwerty");
rs116.next();
// System.out.println(rs116.getString("fname"));
u.setFname(rs116.getString("fname"));
u.setLname(rs116.getString("lname"));
u.setEmailID(rs116.getString("emailID"));
//System.out.println("ugname"+u.getFname());
userobjlist.add(u);  
 
 
}
db.stop();
return userobjlist;
 
 
 
 
 
 }
 catch (Exception e) 
     {
         System.out.println(e.getMessage());
     }
 return userobjlist; 
  }//method ends here

public  String gettimelineid(int statusid) {
String timelineid;
boolean check=false;
try{
while(check!=true){
System.out.println("trying connection");
check= db.start();
}
 
//System.out.println("resultset"+rs15.getString("emailID"));
String query116= "select timelineid from status where statusID=?";
PreparedStatement pstmnt116 = db.con.prepareStatement(query116);
//System.out.println(rs15.getString("emailID"));
pstmnt116.setInt(1,statusid);
//System.out.println("qazwsxedcrfvtgb");
ResultSet rs116= pstmnt116.executeQuery();
//System.out.println("qwerty");
rs116.next();
// System.out.println(rs116.getString("fname"));
timelineid=rs116.getString("timelineid");
db.stop();
return timelineid;
 
 
 
 
 
}
catch (Exception e) 
    {
        System.out.println(e.getMessage());
    }
 

return null;
}
 

 
 
 
}//class ends