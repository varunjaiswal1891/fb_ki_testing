package com.varun.fbproj.model;
import java.util.*;

public class Group {

	private String group_name;
	private String owner;
	private String group_privacy;

	public Group()
	{
		
	}


	public Group(String group_name,String owner,String group_privacy)
    {
		super();
		this.group_name=group_name;
		this.owner=owner;
		this.group_privacy=group_privacy;
	}
	
	
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}


	public String getGroup_privacy() {
		return group_privacy;
	}


	public void setGroup_privacy(String group_privacy) {
		this.group_privacy = group_privacy;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	
}//class ends
