package com.example.ithonge.utils;

import java.util.ArrayList;

import com.example.ithong.models.ListResultItem;

public class Variables {
	// Global variables
	public static String currentTitle = "";
	public static ArrayList<ListResultItem> currentListResultItems = new ArrayList<ListResultItem>();
	// for Activity
	// Main
	public static final String TAG_VEHICLE_POSITION = "vehicle_position";
	public static final String TAG_OPTION_POSITION = "option_position";
	public static final String TAG_VIOLATIOID = "ViolationID";
	// Tag for Database
	public static final String TAG_BOOKMARKNAME = "Bookmarkname";
	// Violation Table
	public static final String TAG_VIOLATION = "Violation";
	public static final String TAG_VIOLATION_ROW_ID = "rowid";
	public static final String TAG_VIOLATION_ID = "ID";
	public static final String TAG_VIOLATION_OBJECT = "Object";
	public static final String TAG_VIOLATION_NAME = "Name";
	public static final String TAG_VIOLATION_NAME_EN = "NameEN";
	public static final String TAG_VIOLATION_LAW_ID = "LawID";
	public static final String TAG_VIOLATION_BOOKMARK_ID = "Bookmark_ID";
	public static final String TAG_VIOLATION_IS_WARNING = "IsWarning";
	public static final String TAG_VIOLATION_ISPOPPULAR = "IsPoppular";
	public static final String TAG_VIOLATION_MAIN_CONTENT = "MainContent";
	public static final String TAG_VIOLATION_FINES = "Fines";
	public static final String TAG_VIOLATION_ADDITIONAL_PENALTIES = "Additional_Penalties";
	public static final String TAG_VIOLATION_REMEDIAL_MEASURES = "Remedial_Measures";
	public static final String TAG_VIOLATION_OTHER_PENALTIES = "Other_Penalties";
	public static final String TAG_VIOLATION_GROUP_VALUE = "Group_Value";
	public static final String TAG_VIOLATION_TYPE_VALUE = "Type_Value";
	public static final String TAG_VIOLATION_LAW_TITLE = "LawTitle";
	public static final String TAG_VIOLATION_DISABLED = "Disabled";
	public static final String TAG_VIOLATION_LAST_UPDATED = "LastUpdated";

	// Group Table
	public static final String TAG_GROUP_ID = "Group_ID";
	public static final String TAG_GROUP_NAME = "Group_Name";
	// public static final String TAG_GROUP_VALUE = "Group_Value";
	public static final String TAG_SORT_VALUE = "SortValue";

	// Keyword table
	public static final String TAG_KEYWORD = "Keyword";
	public static final String TAG_KEYWORD_ID = "Keyword_ID";
	public static final String TAG_KEYWORD_NAME = "Keyword_Name";
	public static final String TAG_KEYWORD_NAME_EN = "Keyword_NameEN";
	public static final String TAG_KEYWORD_SORT = "Sort";
	public static final String TAG_KEYWORD_LAST_UPDATE = "LastUpdated";
	public static final String TAG_KEYWORD_CODE = "code";
	public static final String TAG_KEYWORD_MESSAGE = "message";

}
