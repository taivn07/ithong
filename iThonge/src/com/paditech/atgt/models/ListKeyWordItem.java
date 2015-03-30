package com.paditech.atgt.models;

public class ListKeyWordItem {
	private String name;
	private String nameEN;

	public ListKeyWordItem(String name, String nameEN) {
		this.name = name;
		this.nameEN = nameEN;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public ListKeyWordItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
