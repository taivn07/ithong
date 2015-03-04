package com.example.models;

/**
 * Wednesday, March 04 2015 //
 * 
 * @author STramp // To save result
 */
public class ListResultItem {
	private int icon;
	private String title;
	private String message;

	public ListResultItem(int icon, String title, String message) {
		this.icon = icon;
		this.title = title;
		this.message = message;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
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
