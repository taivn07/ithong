package com.example.ithong.models;

/**
 * Wednesday, March 04 2015 //
 * 
 * @author STramp // To save result
 */

// An------------------------------------------------

public class ListResultItem {
	private CharSequence violationName;
	private CharSequence violationNameEn;
	private String fine;
	private String message;
	private long ViolationID;

	public ListResultItem(CharSequence violationName, CharSequence violationNameEn, String fine, String message, long ViolationID) {
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

	public CharSequence getVioName() {
		return violationName;
	}

	public void setVioName(CharSequence violation) {
		this.violationName = violation;
	}

	public CharSequence getVioNameEn() {
		return violationNameEn;
	}

	public void setVioNameEn(CharSequence violation) {
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
