package com.varun.fbproj.model;

public class Likes {

	private int likeID;
	private int statusID;
	private String emailID;
	private int lflag;
	
	public Likes(){
		
	}
	public Likes(int likeID,int statusID,String emailID,int flag)
	{
		this.likeID=likeID;
		this.statusID=statusID;
		this.emailID=emailID;	
		this.lflag=flag;
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
