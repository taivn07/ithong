package com.example.ithong.models;

public class ListActionItem {
	private int icon;
	private int position;
	private String title;

	public ListActionItem(int icon, String title, int position) {
		this.icon = icon;
		this.title = title;
		this.position = position;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public int getpos() {
		return position;
	}

	public void setpos(int position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
