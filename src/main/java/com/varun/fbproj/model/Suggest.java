package com.varun.fbproj.model;

public class Suggest {
	
	private int sfid;
	private String firstemailid;
	private String secondemailid;
	private String suggestedemailid;
	
	Suggest(){
		
	}
	Suggest(int sfid,String firstemailid,String secondemailid,String suggestedemailid)
	{
		this.sfid=sfid;
		this.firstemailid=firstemailid;
		this.secondemailid=secondemailid;
		this.suggestedemailid=suggestedemailid;
		
		
		
	}

	public int getSfid() {
		return sfid;
	}

	public void setSfid(int sfid) {
		this.sfid = sfid;
	}

	public String getFirstemailid() {
		return firstemailid;
	}

	public void setFirstemailid(String firstemailid) {
		this.firstemailid = firstemailid;
	}

	public String getSecondemailid() {
		return secondemailid;
	}

	public void setSecondemailid(String secondemailid) {
		this.secondemailid = secondemailid;
	}

	public String getSuggestedemailid() {
		return suggestedemailid;
	}

	public void setSuggestedemailid(String suggestedemailid) {
		this.suggestedemailid = suggestedemailid;
	}

	
}
