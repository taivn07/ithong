package com.paditech.atgt.models;

public class TableViolation {

	private int Violation_ID;
	private String Object;
	private String Name;
	private String NameEN;
	private int LawID;
	private int Bookmark_ID;
	private int IsWarning;
	private int IsPoppular;
	private String MainContent;
	private String Fines;
	private String Additional_Penalties;
	private String Remedial_Measures;
	private String Other_Penalties;
	private int Group_Value;
	private int Type_Value;
	private String LawTitle;
	private int Disabled;
	private String LastUpdated;

	public TableViolation(int violation_ID, String object, String name, String nameEN, int lawID, int bookmark_ID, int isWarning,
			int isPoppular, String mainContent, String fines, String additional_Penalties, String remedial_Measures,
			String other_Penalties, int group_Value, int type_Value, String lawTitle, int disabled, String lastUpdated) {
		Violation_ID = violation_ID;
		Object = object;
		Name = name;
		NameEN = nameEN;
		LawID = lawID;
		Bookmark_ID = bookmark_ID;
		IsWarning = isWarning;
		IsPoppular = isPoppular;
		MainContent = mainContent;
		Fines = fines;
		Additional_Penalties = additional_Penalties;
		Remedial_Measures = remedial_Measures;
		Other_Penalties = other_Penalties;
		Group_Value = group_Value;
		Type_Value = type_Value;
		LawTitle = lawTitle;
		Disabled = disabled;
		LastUpdated = lastUpdated;
	}

	public int getViolation_ID() {
		return Violation_ID;
	}

	public void setViolation_ID(int violation_ID) {
		Violation_ID = violation_ID;
	}

	public String getObject() {
		return Object;
	}

	public void setObject(String object) {
		Object = object;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNameEN() {
		return NameEN;
	}

	public void setNameEN(String nameEN) {
		NameEN = nameEN;
	}

	public int getLawID() {
		return LawID;
	}

	public void setLawID(int lawID) {
		LawID = lawID;
	}

	public int getBookmark_ID() {
		return Bookmark_ID;
	}

	public void setBookmark_ID(int bookmark_ID) {
		Bookmark_ID = bookmark_ID;
	}

	public int getIsWarning() {
		return IsWarning;
	}

	public void setIsWarning(int isWarning) {
		IsWarning = isWarning;
	}

	public int getIsPoppular() {
		return IsPoppular;
	}

	public void setIsPoppular(int isPoppular) {
		IsPoppular = isPoppular;
	}

	public String getMainContent() {
		return MainContent;
	}

	public void setMainContent(String mainContent) {
		MainContent = mainContent;
	}

	public String getFines() {
		return Fines;
	}

	public void setFines(String fines) {
		Fines = fines;
	}

	public String getAdditional_Penalties() {
		return Additional_Penalties;
	}

	public void setAdditional_Penalties(String additional_Penalties) {
		Additional_Penalties = additional_Penalties;
	}

	public String getRemedial_Measures() {
		return Remedial_Measures;
	}

	public void setRemedial_Measures(String remedial_Measures) {
		Remedial_Measures = remedial_Measures;
	}

	public String getOther_Penalties() {
		return Other_Penalties;
	}

	public void setOther_Penalties(String other_Penalties) {
		Other_Penalties = other_Penalties;
	}

	public int getGroup_Value() {
		return Group_Value;
	}

	public void setGroup_Value(int group_Value) {
		Group_Value = group_Value;
	}

	public int getType_Value() {
		return Type_Value;
	}

	public void setType_Value(int type_Value) {
		Type_Value = type_Value;
	}

	public String getLawTitle() {
		return LawTitle;
	}

	public void setLawTitle(String lawTitle) {
		LawTitle = lawTitle;
	}

	public int getDisabled() {
		return Disabled;
	}

	public void setDisabled(int disabled) {
		Disabled = disabled;
	}

	public String getLastUpdated() {
		return LastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		LastUpdated = lastUpdated;
	}

}
