package com.varun.fbproj.model;
import java.util.*;

public class Status {

	  private int statusID;
	   private String status_desc;
	   private String created;
	   private String emailID;
	   private int flag;  
	   private int likes_count;
	   private int unlikes_count;
	   private String group_name;
	   
	   
	   
	   
	   public int getUnlikes_count() {
		return unlikes_count;
	}
	public void setUnlikes_count(int unlikes_count) {
		this.unlikes_count = unlikes_count;
	}
	private String name;
	   
	   
	   
	   public int getLikes_count() {
		return likes_count;
	}
	public void setLikes_count(int likes_count) {
		this.likes_count = likes_count;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	ArrayList<Comment> a;
	   
	   public Status(){
		 
	   }
	   public Status(int statusID,String status_desc,String emailID,String created,int LikesCount,String name){
		   this.statusID=statusID;
		   this.status_desc=status_desc;
		   this.emailID=emailID;
		   this.created=created;
		   flag=1;
		   this.name=name;
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
