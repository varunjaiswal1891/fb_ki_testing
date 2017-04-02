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
	   private String feeling;
	   private String timelineid;
	   private String name2;
	   private String postedon;
	   private  ArrayList<Comment> a;
	 private int color;
	   
	   public String getName2() {
		return name2;
	}
	
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	public String getTimelineid() {
		return timelineid;
	}
	public void setTimelineid(String timelineid) {
		this.timelineid = timelineid;
	}
	
	   
	   
	   
	   
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
	
	   
	   public Status(){
		 
	   }
	   public Status(int color,int statusID,String postedon,String status_desc,String name2,String emailID,String created,int LikesCount,String name,String feeling,String timelineid){
		   this.statusID=statusID;
		   this.status_desc=status_desc;
		   this.emailID=emailID;
		   this.created=created;
		   flag=1;
		   this.name=name;
		   this.postedon=postedon;
		   this.name2=name2;
		   this.likes_count=LikesCount;
		   this.feeling=feeling;
		   this.timelineid=timelineid;
		   a=new ArrayList<Comment>();
		   this.color=color;
		 
		   }
	  
	public String getPostedon() {
		return postedon;
	}

	public void setPostedon(String postedon) {
		this.postedon = postedon;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
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