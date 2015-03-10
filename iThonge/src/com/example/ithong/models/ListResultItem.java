package com.example.ithong.models;

/**
 * Wednesday, March 04 2015 //
 * 
 * @author STramp // To save result
 */

// An------------------------------------------------

public class ListResultItem {
	private String violationName;
	private String violationNameEn;
	private String fine;
	private String message;
	private long ViolationID;

	public ListResultItem(String violationName, String violationNameEn, String fine, String message, long ViolationID) {
		this.violationName = violationName;
		this.violationNameEn = violationNameEn;
		this.fine = fine;
		this.message = message;
		this.ViolationID = ViolationID;
	}

	public long getVioID() {
		return ViolationID;
	}

	public void setVioID(long violationID) {
		this.ViolationID = violationID;
	}

	public String getVioName() {
		return violationName;
	}

	public void setVioName(String violation) {
		this.violationName = violation;
	}

	public String getVioNameEn() {
		return violationNameEn;
	}

	public void setVioNameEn(String violation) {
		this.violationNameEn = violation;
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
