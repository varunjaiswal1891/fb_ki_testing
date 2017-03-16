package com.varun.fbproj.model;

public class SuggestUser {

	private String firstfname;
	private String firstlname;
	private String secondfname;
	private String secondlname;
	private String suggestfname;
	private String suggestlname;
	
	private String firstemail;
	private String secondemail;
	private String suggestemail;
	
	public SuggestUser(){
		
	}

	public SuggestUser(String firstfname, String firstlname,
			String secondfname, String secondlname, String suggestfname,
			String suggestlname, String firstemail, String secondemail,
			String suggestemail) {
		this.firstfname = firstfname;
		this.firstlname = firstlname;
		this.secondfname = secondfname;
		this.secondlname = secondlname;
		this.suggestfname = suggestfname;
		this.suggestlname = suggestlname;
		this.firstemail = firstemail;
		this.secondemail = secondemail;
		this.suggestemail = suggestemail;
	}

	public String getFirstfname() {
		return firstfname;
	}

	public void setFirstfname(String firstfname) {
		this.firstfname = firstfname;
	}

	public String getFirstlname() {
		return firstlname;
	}

	public void setFirstlname(String firstlname) {
		this.firstlname = firstlname;
	}

	public String getSecondfname() {
		return secondfname;
	}

	public void setSecondfname(String secondfname) {
		this.secondfname = secondfname;
	}

	public String getSecondlname() {
		return secondlname;
	}

	public void setSecondlname(String secondlname) {
		this.secondlname = secondlname;
	}

	public String getSuggestfname() {
		return suggestfname;
	}

	public void setSuggestfname(String suggestfname) {
		this.suggestfname = suggestfname;
	}

	public String getSuggestlname() {
		return suggestlname;
	}

	public void setSuggestlname(String suggestlname) {
		this.suggestlname = suggestlname;
	}

	public String getFirstemail() {
		return firstemail;
	}

	public void setFirstemail(String firstemail) {
		this.firstemail = firstemail;
	}

	public String getSecondemail() {
		return secondemail;
	}

	public void setSecondemail(String secondemail) {
		this.secondemail = secondemail;
	}

	public String getSuggestemail() {
		return suggestemail;
	}

	public void setSuggestemail(String suggestemail) {
		this.suggestemail = suggestemail;
	}
	
	
	
	
}
