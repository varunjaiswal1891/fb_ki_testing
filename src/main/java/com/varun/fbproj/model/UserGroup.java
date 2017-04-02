package com.varun.fbproj.model;

public class UserGroup {

	private String emailID;
	private String group_name;
	private String emailID1;
	private String emailID2;
	
	public String getEmailID1() {
		return emailID1;
	}

	public void setEmailID1(String emailID1) {
		this.emailID1 = emailID1;
	}

	public String getEmailID2() {
		return emailID2;
	}

	public void setEmailID2(String emailID2) {
		this.emailID2 = emailID2;
	}

	public UserGroup()
	{
		
	}
	
	public UserGroup(String group_name,String emailID1,String emailID2)
    {
		super();
		this.group_name=group_name;
		this.emailID1=emailID1;
		this.emailID2=emailID2;
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
