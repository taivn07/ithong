package com.paditech.atgt.models;

public class TableKeyword {
	private int Keyword_ID;
	private String Keyword_Name;
	private String Keyword_NameEN;
	private int Sort;
	private String LastUpdated;

	public TableKeyword(int keyword_ID, String keyword_Name, String keyword_NameEN, int sort, String lastUpdated) {
		Keyword_ID = keyword_ID;
		Keyword_Name = keyword_Name;
		Keyword_NameEN = keyword_NameEN;
		Sort = sort;
		LastUpdated = lastUpdated;
	}

	public int getKeyword_ID() {
		return Keyword_ID;
	}

	public void setKeyword_ID(int keyword_ID) {
		Keyword_ID = keyword_ID;
	}

	public String getKeyword_Name() {
		return Keyword_Name;
	}

	public void setKeyword_Name(String keyword_Name) {
		Keyword_Name = keyword_Name;
	}

	public String getKeyword_NameEN() {
		return Keyword_NameEN;
	}

	public void setKeyword_NameEN(String keyword_NameEN) {
		Keyword_NameEN = keyword_NameEN;
	}

	public int getSort() {
		return Sort;
	}

	public void setSort(int sort) {
		Sort = sort;
	}

	public String getLastUpdated() {
		return LastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		LastUpdated = lastUpdated;
	}

}
