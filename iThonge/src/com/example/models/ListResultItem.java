package com.example.models;

/**
 * Wednesday, March 04 2015 //
 * 
 * @author STramp // To save result
 */

// An------------------------------------------------



public class ListResultItem {
	private String violation;
	private String fine;
	private String message;
	private long ViolationID;

	public ListResultItem(String violation,String fine, String message, long ViolationID) {
		this.violation = violation;
		this.fine =fine;
		this.message = message;
		this.ViolationID = ViolationID;
	}
	
	public long getVioID() {
		return ViolationID;
	}

	public void setVioID(long violationID) {
		this.ViolationID = violationID;
	}
	public String getTitle() {
		return violation;
	}

	public void setTitle(String violation) {
		this.violation = violation;
	}
	
	public String getfine() {
		return fine;
	}

	public void setfine(String fine) {
		this.fine = fine;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
