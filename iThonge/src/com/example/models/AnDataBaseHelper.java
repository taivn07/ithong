package com.example.models;

import java.io.*;

import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.content.Context;

public class AnDataBaseHelper extends SQLiteOpenHelper{

//DBPATH uses the default system path for a given application
// "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";
private static String DBPATH = "";
private static String DBNAME = "iThong.db";
private SQLiteDatabase myDatabase;
private final Context myContext;
private static final String LOG_TAG = "Database";

public AnDataBaseHelper(Context context) throws IOException {
	super(context, DBNAME, null, 1);
	this.myContext = context;
	DBPATH = "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";
	if (checkDatabase()) {
		   Log.e(LOG_TAG, "Database is exists.");
		  } else {
		   Log.e(LOG_TAG, "Database doesn't exists.");
		   createDatabase();
		  }
	openDatabase();
}

public void createDatabase() throws IOException{
if (!checkDatabase()) {
this.getWritableDatabase();
try {
copyDatabase();
}
catch (IOException e) {
throw new Error("Error copying database from system assets");
}
}
}

private void copyDatabase() throws IOException{
InputStream myInput = myContext.getAssets().open(DBNAME);
OutputStream myOutput = new FileOutputStream(DBPATH+DBNAME);
byte[] buffer = new byte[1024];
int length;
while ((length = myInput.read(buffer))>0){
myOutput.write(buffer, 0, length);
}
myOutput.flush();
myOutput.close();
myInput.close();
}

private boolean checkDatabase(){
SQLiteDatabase checkableDatabase = null;
try {
checkableDatabase =
SQLiteDatabase.openDatabase(DBPATH+DBNAME, null,
SQLiteDatabase.OPEN_READONLY);
}
catch (SQLiteException e) {
//our database doesn't exist, so we'll return false below.
}
if (checkableDatabase != null) {
checkableDatabase.close();
}
return checkableDatabase != null ? true : false;
}

public void openDatabase() throws SQLException{
myDatabase = SQLiteDatabase.openDatabase(DBPATH+DBNAME, null,
SQLiteDatabase.OPEN_READWRITE);
}

public Cursor getAllName() {
return myDatabase.rawQuery("select Group_Name from Groups order by Group_Name", null);
}

private void startManagingCursor(Cursor foodCursor) {
	// TODO Auto-generated method stub
	
}

@Override
public synchronized void close() {
if(myDatabase != null)
myDatabase.close();
super.close();
}
@Override
public void onCreate(SQLiteDatabase db) {
//Handle creation tasks, etc.
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//Handle upgrade tasks, etc.
}
	
}