package com.varun.fbproj.model;
import java.util.*;

public class Status {

	  private int statusID;
	   private String status_desc;
	   private String created;
	   private String emailID;
	   private int flag;  
	   private int likes_count;
	   ArrayList<Comment> a;
	   
	   public Status(){
		 
	   }
	   public Status(int statusID,String status_desc,String emailID,String created,int LikesCount){
		   this.statusID=statusID;
		   this.status_desc=status_desc;
		   this.emailID=emailID;
		   this.created=created;
		   flag=1;
		   this.likes_count=LikesCount;
		   a=new ArrayList<Comment>();
		   }
	  
	public int getStatusID() {
		return statusID;
	}
	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	
	
	

	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getStatus_desc() {
		return status_desc;
	}
	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public ArrayList<Comment> getA() {
		return a;
	}
	public void setA(ArrayList<Comment> a) {
		System.out.println("****hereeee******");
		this.a = a;
	}
	public int getLikesCount() {
		return likes_count;
	}
	public void setLikesCount(int LikesCount) {
		
		this.likes_count = LikesCount;
		System.out.println("setting likes count"+likes_count);
	}
	
	
	
}//class ends here
