package com.varun.fbproj.model;

public class Group {

	private String group_name;
	private String emailID;
	private String owner;
	
	public Group()
	{
		
	}


	public Group(String group_name,String emailID,String owner)
    {
		super();
		this.group_name=group_name;
		this.emailID = emailID;
		this.owner=owner;
	}
	
	
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	
}//class ends
