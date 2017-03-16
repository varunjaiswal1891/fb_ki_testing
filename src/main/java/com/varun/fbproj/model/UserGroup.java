package com.varun.fbproj.model;

public class UserGroup {

	private String emailID;
	private String group_name;
	
	public UserGroup()
	{
		
	}
	
	public UserGroup(String group_name,String emailID)
    {
		super();
		this.group_name=group_name;
		this.emailID=emailID;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	
}//class ends here
