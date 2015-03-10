package com.example.ithonge.utils;

import java.util.ArrayList;

import com.example.ithong.models.ListResultItem;

public class Variables {
	// Global variables
	public static  String currentTitle = "";
	public static ArrayList<ListResultItem> currentListResultItems = new ArrayList<ListResultItem>();
	// for Activity
	// Main
	public static final String TAG_VEHICLE_POSITION = "vehicle_position";
	public static final String TAG_OPTION_POSITION = "option_position";
	public static final String TAG_VIOLATIOID = "ViolationID";
	// Tag for Database
	// Violation Table
	public static final String TAG_ROW_ID = "rowid";
	public static final String TAG_ID = "ID";
	public static final String TAG_OBJECT = "Object";
	public static final String TAG_NAME = "Name";
	public static final String TAG_NAME_EN = "NameEN";
	public static final String TAG_LAW_ID = "LawID";
	public static final String TAG_BOOKMARK_ID = "Bookmark_ID";
	public static final String TAG_MAIN_CONTENT = "rowid";
	public static final String TAG_FINES = "rowid";
	public static final String TAG_ADDITIONAL_PENALTIES = "rowid";
	public static final String TAG_GROUP_VALUE = "Group_Value";
	public static final String TAG_TYPE_VALUE = "Type_Value";

	// Group Table
	public static final String TAG_GROUP_ID = "Group_ID";
	public static final String TAG_GROUP_NAME = "Group_Name";
	// public static final String TAG_GROUP_VALUE = "Group_Value";
	public static final String TAG_SORT_VALUE = "SortValue";
}
