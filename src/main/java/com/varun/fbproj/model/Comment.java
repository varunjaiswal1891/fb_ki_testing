package com.varun.fbproj.model;

import java.util.*;
public class Comment {

	private int commentID;
	private int statusID;
	private String emailID;
	private int flag;
	private String comment_desc;
	private Date comment_Date;
	private String name;
	private String group_name;
	private String timelineid;
	
	
	public String getTimelineid() {
		return timelineid;
	}

	public void setTimelineid(String timelineid) {
		this.timelineid = timelineid;
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

	public Comment(int commentID,int statusID,String group_name,int flag,String comment_desc,String timelineid,String comment_Date,String emailID,String name){
		this.commentID=commentID;
		this.statusID=statusID;
		this.flag=flag;
		this.name=name;
		this.group_name=group_name;
		this.emailID=emailID;
		this.comment_desc=comment_desc;
		this.comment_Date=new Date();
		this.name=name;
		this.timelineid=timelineid;
	}

	public Comment() {
		// TODO Auto-generated constructor stub
	}
	public int getCommentID() {
		return commentID;
	}


	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getComment_desc() {
		return comment_desc;
	}

	public void setComment_desc(String comment_desc) {
		this.comment_desc = comment_desc;
	}

	public Date getComment_Date() {
		return comment_Date;
	}

	public void setComment_Date(Date comment_Date) {
		this.comment_Date = comment_Date;
	}
	
	
}//class ends here