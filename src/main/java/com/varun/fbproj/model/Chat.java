package com.varun.fbproj.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class Chat {

	private String senderEmail;
	private String senderName;
	private String receiverEmail;
	private String receiverName;
	private String status;
	private String message;
	private String time;
	private int chatID;
	
	public int getChatID() {
		return chatID;
	}

	public void setChatID(int chatID) {
		this.chatID = chatID;
	}

	public Chat(){
		
	}
	
	public Chat(String senderEmail, String senderName, String receiverEmail, String receiverName, String status,
			String message, String time) {
		super();
		this.senderEmail = senderEmail;
		this.senderName = senderName;
		this.receiverEmail = receiverEmail;
		this.receiverName = receiverName;
		this.status = status;
		this.message = message;
		this.time = time;
	}

	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
	
}//class ends here