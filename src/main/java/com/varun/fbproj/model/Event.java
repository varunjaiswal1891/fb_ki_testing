package com.varun.fbproj.model;

import java.util.Date;




public class Event {
private int eID;
private int uID;
private String ename;
private String hname;
private String event_date;
private String event_end_date;
private String location;
private String eventType;
private String time;

public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}

public String getEventType() {
	return eventType;
}

public void setEventType(String eventType) {
	this.eventType = eventType;
}

public Event()
{
	
}

public Event(int eID, int uID, String ename, String hname, String event_date, String event_end_date,
		String location,String eventType, String time) {
	super();
	this.eID = eID;
	this.uID = uID;
	this.ename = ename;
	this.hname = hname;
	this.event_date = event_date;
	this.location = location;
	this.event_end_date=event_end_date;
	this.eventType=eventType;
	this.time=time;
}

public String getEvent_end_date() {
	return event_end_date;
}

public void setEvent_end_date(String event_end_date) {
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
public String getEvent_date() {
	return event_date;
}
public void setEvent_date(String event_date) {
	this.event_date = event_date;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}

}