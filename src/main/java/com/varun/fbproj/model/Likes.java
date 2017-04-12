package com.varun.fbproj.model;

public class Likes {

	private int likeID;
	private int statusID;
	private String emailID;
	private int lflag;
	private String timelineid;
	private String group_name;
	
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Likes(){
		
	}
	public Likes(int likeID,int statusID,String emailID,int flag,String timelineid,String group_name)
	{
		this.likeID=likeID;
		this.statusID=statusID;
		this.emailID=emailID;	
		this.lflag=flag;
		this.timelineid=timelineid;
		this.group_name=group_name;
	}
	public String getTimelineid() {
		return timelineid;
	}
	public void setTimelineid(String timelineid) {
		this.timelineid = timelineid;
	}
	public int getLikeID() {
		return likeID;
	}
	public void setLikeID(int likeID) {
		this.likeID = likeID;
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
	public int getLflag() {
		return lflag;
	}
	public void setLflag(int lflag) {
		this.lflag = lflag;
	}


	
}//class ends here
