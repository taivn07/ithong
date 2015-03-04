package com.example.models;

/**
 * Wednesday, March 04 2015 //
 * 
 * @author STramp // To save result
 */
public class ListResultItem {
	private String title;
	private String message;

	public ListResultItem(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
