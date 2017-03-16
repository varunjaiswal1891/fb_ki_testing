package com.varun.fbproj.model;

import java.sql.Date;


public class Event {
private int eID;
private int uID;
private String ename;
private String hname;
private Date event_date;
private Date event_end_date;
private String location;
public Event()
{
	
}

public Event(int eID, int uID, String ename, String hname, Date event_date, Date event_end_date,
		String location) {
	super();
	this.eID = eID;
	this.uID = uID;
	this.ename = ename;
	this.hname = hname;
	this.event_date = event_date;
	this.location = location;
	this.event_end_date=event_end_date;
}

public Date getEvent_end_date() {
	return event_end_date;
}

public void setEvent_end_date(Date event_end_date) {
	this.event_end_date = event_end_date;
}

public int geteID() {
	return eID;
}
public void seteID(int eID) {
	this.eID = eID;
}
public String getEname() {
	return ename;
}
public void setEname(String ename) {
	this.ename = ename;
}
public String getHname() {
	return hname;
}
public int getuID() {
	return uID;
}
public void setuID(int uID) {
	this.uID = uID;
}
public void setHname(String hname) {
	this.hname = hname;
}
public Date getEvent_date() {
	return event_date;
}
public void setEvent_date(Date event_date) {
	this.event_date = event_date;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}

}
