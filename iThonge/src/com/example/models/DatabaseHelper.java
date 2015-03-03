package com.example.models;

import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private String DATABASE_NAME = "iThong.sqlite";
	private String DATABASE_PATH = context.getApplicationContext().getPackageName() + "/databases/";
	private SQLiteDatabase mDatabase;

	// private String DATABASE_PATH =
	// "/data/data/"+context.getApplicationContext().getPackageName() +
	// "/databases/";

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

//	public DatabaseHelper(Context context) throws IOException{
//		//super(context,DATABASE_NAME,null,1);
//	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
