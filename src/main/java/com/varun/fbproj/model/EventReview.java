package com.varun.fbproj.model;


import java.sql.Timestamp;



public class EventReview {
private int eID;
public int geteID() {
	return eID;
}
private int month;
private int day;
private int hour;
private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getMonth() {
	return month;
}

public void setMonth(int month) {
	this.month = month;
}

public int getDay() {
	return day;
}

public void setDay(int day) {
	this.day = day;
}

public int getHour() {
	return hour;
}

public void setHour(int hour) {
	this.hour = hour;
}
private Timestamp t;



public Timestamp getT() {
	return t;
}

public void setT(Timestamp timestamp2) {
	this.t = timestamp2;
}

public EventReview(int eID, int userID, String review_desc,User u1) {
	super();
	this.eID = eID;
	this.userID = userID;
	this.review_desc = review_desc;
	this.u1 = u1;
}

public void seteID(int eID) {
	this.eID = eID;
}

public EventReview() {

}

public int getUserID() {
	return userID;
}

public void setUserID(int userID) {
	this.userID = userID;
}

public String getReview_desc() {
	return review_desc;
}

public void setReview_desc(String review_desc) {
	this.review_desc = review_desc;
}

private int userID;
private String review_desc;
private  User u1;
public User getU1() {
	return u1;
}

public void setU1(User u1) {
	this.u1 = u1;
}



}