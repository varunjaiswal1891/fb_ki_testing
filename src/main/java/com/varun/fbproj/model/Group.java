package com.varun.fbproj.model;
import java.util.*;

public class Group {

	private String group_name;
	private String owner;
	private int gid;
	public int getGid() {
		return gid;
	}


	public void setGid(int gid) {
		this.gid = gid;
	}


	public Group()
	{
		
	}


	public Group(String group_name,String owner,int gid)
    {
		super();
		this.group_name=group_name;
		this.owner=owner;
		this.gid=gid;
	}
	
	
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	
}//class ends
