package com.paditech.atgt.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private static String DATABASE_NAME = "iThong.db";

	// DATABASE_PATH = "/data/data/" +
	// context.getApplicationContext().getPackageName() + "/databases/";

	private SQLiteDatabase mDatabase;
	public static final String LOG_TAG = "DatabaseHelper";

	private String DATABASE_PATH;

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context) throws IOException {
		super(context, DATABASE_NAME, null, 1);
		this.context = context;
		DATABASE_PATH = "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";
		if (checkDatabaseExist()) {
			Log.e(LOG_TAG, "Database is exists.");
		} else {
			Log.e(LOG_TAG, "Database doesn't exists.");
			createDatabase();
		}
		openDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private void createDatabase() {
		if (checkDatabaseExist()) {
			Log.e(LOG_TAG, "This database is exists.");
		} else {
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (Exception e) {
				Log.e(LOG_TAG, "Can't copy database. Check and try again. Error: " + e.getLocalizedMessage());
			}
		}
	}

	private boolean checkDatabaseExist() {
		boolean check = false;
		try {
			String path = DATABASE_PATH + DATABASE_NAME;
			File dbFile = new File(path);
			check = dbFile.exists();
		} catch (SQLiteException e) {
			Log.e(LOG_TAG, "Can't check this database. Please check again. Error: " + e.getLocalizedMessage());
		}
		return check;
	}

	private void copyDatabase() throws IOException {
		InputStream mInputStream = context.getAssets().open(DATABASE_NAME);
		// Path to create new database
		String path = DATABASE_PATH + DATABASE_NAME;
		OutputStream mOutputStream = new FileOutputStream(path);
		// tranfer bytes from inputStream to outputStream;
		byte[] buffer = new byte[1024];
		int length;

		while ((length = mInputStream.read(buffer)) > 0) {
			mOutputStream.write(buffer, 0, length);
		}

		mOutputStream.flush();
		mOutputStream.close();
		mInputStream.close();
	}

	private void openDatabase() throws SQLException {
		String path = DATABASE_PATH + DATABASE_NAME;
		mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
	}

	public synchronized void close() {
		if (mDatabase != null) {
			mDatabase.close();
		}
		super.close();
	}

	// Xay dung cac phuong thuc lam viec voi database

	public void deleteFromTableLuuTru(long value) {
		String sql = "DELETE FROM LuuTru WHERE Violation_ID =" + value;
		mDatabase.execSQL(sql);
	}

	public void insertintoTableLuuTru(long value) {
		String sql = "INSERT INTO LuuTru (Violation_ID) Values " + "('" + value + "')";
		mDatabase.execSQL(sql);
	}

	public void insertIntoTableKeyword(ArrayList<TableKeyword> keys) {
		for (TableKeyword item : keys) {
			String sql = "INSERT INTO `Keywords` VALUES(" + item.getKeyword_ID() + ",'" + item.getKeyword_Name() + "','"
					+ item.getKeyword_NameEN() + "'," + item.getSort() + ",'" + item.getLastUpdated() + "')";

			Log.e(LOG_TAG, "Error: " + sql);
			mDatabase.execSQL(sql);
		}
	}

	public void insertIntoTableViolation(ArrayList<TableViolation> violations) {
		for (TableViolation item : violations) {
			String sql = "INSERT INTO `Violation` VALUES(" + item.getViolation_ID() + ",'" + item.getObject() + "','" + item.getName()
					+ "'," + item.getNameEN() + ",'" + item.getLawID() + item.getBookmark_ID() + "'," + item.getMainContent() + ",'"
					+ item.getFines() + item.getIsWarning() + ",'" + item.getIsPoppular() + "','" + item.getAdditional_Penalties() + "',"
					+ item.getRemedial_Measures() + ",'" + item.getOther_Penalties() + item.getGroup_Value() + "'," + item.getType_Value()
					+ ",'" + item.getDisabled() + item.getLawTitle() + ",'" + item.getLastUpdated() + "')";

			Log.e(LOG_TAG, "Error: " + sql);
			mDatabase.execSQL(sql);
		}
	}

	public void deleteFromTableKeyword(long value) {
		String sql = "DELETE FROM 'Keywords' WHERE Keyword_ID =" + value;
		Log.e(LOG_TAG, "Delete: " + sql);
		mDatabase.execSQL(sql);
	}

	public Cursor getAllFromTable(String tableName) {
		String sql = "Select * from " + tableName;
		return mDatabase.rawQuery(sql, null);
	}

	public Cursor getResultFromSQL(String sql) {
		return mDatabase.rawQuery(sql, null);
	}

	/**
	 * @author Tuesday, March 03, 2015 Comment by Dungna
	 */
	/*
	 * public static class DBManagerOpenHelper extends SQLiteOpenHelper{
	 * 
	 * public DBManagerOpenHelper(Context context, String name, CursorFactory
	 * factory, int version) { super(context, name, factory, version); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * //Create new Database
	 * 
	 * @Override public void onCreate(SQLiteDatabase db) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * //Check database version if was changed.
	 * 
	 * @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int
	 * newVersion) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * }
	 */// end comment - dungna
}
