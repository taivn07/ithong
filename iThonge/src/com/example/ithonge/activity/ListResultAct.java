package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;

import android.app.SearchManager;
import android.content.Context;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ithonge.R;
import com.example.ithonge.adapter.ListResultAdapter;
import com.example.ithonge.utils.Variables;
import com.example.models.DatabaseHelper;
import com.example.models.ListResultItem;

public class ListResultAct extends Activity {
	// ListView Result
	private ListView mListResult;
	private ListResultAdapter mListResultAdapter;
	private ArrayList<ListResultItem> mListResultItems;
	// Database/LogTag
	public static final String LOG_TAG = "ListResultAct";
	private DatabaseHelper mDatabaseHelper;
	private int optionPosition;
	private int vehiclePosition;

	private TextView tvResultCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result);
		optionPosition = getIntent().getExtras().getInt(Variables.TAG_OPTION_POSITION);
		vehiclePosition = getIntent().getExtras().getInt(Variables.TAG_VEHICLE_POSITION);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(getResources().getStringArray(R.array.list_vehicles)[vehiclePosition]+"  >>  "+getResources().getStringArray(R.array.list_action_item)[optionPosition]);

		// init local variables
		mListResultItems = new ArrayList<ListResultItem>();
		try {
			mDatabaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			Log.e(LOG_TAG, "Can't Create Database. Please check and try again. Error: " + e.getMessage());
		}

		// create listview
		mListResultItems = getResultFromGroupId(optionPosition, vehiclePosition);
		mListResult = (ListView) findViewById(R.id.lv_list_result);
		mListResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		mListResultAdapter = new ListResultAdapter(this, mListResultItems);
		mListResult.setAdapter(mListResultAdapter);

	}

	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ListResultAct.this, ListResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID,mListResultItems.get(position).getVioID());
			startActivity(intent);
		}
	}

	// get result from position: group id(database: Groups table)
	private ArrayList<ListResultItem> getResultFromGroupId(int groupId, int vehicleID) {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();
		int group_value = (int) Math.pow(2, groupId);
		int type_value = (int) Math.pow(2, vehicleID);
		String sql = "Select * From Violation Where Group_Value = " +group_value + " and Type_Value = " + type_value ;
		Log.e(LOG_TAG, sql);
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		// get result and save to mListResultItems
		mResult.moveToFirst();
		String Violationame = null;
		String Fine =null;
		String strMessage = null;
		long ViolationID = 0;
		int count = 0;
		if (mResult.getCount()!=0)
		while (!mResult.isAfterLast()) {
			Violationame = mResult.getString(mResult.getColumnIndex("Name"));
			Fine = mResult.getString(mResult.getColumnIndex("Fines"));
			strMessage = mResult.getString(mResult.getColumnIndex("Object"));
			ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
			list.add(new ListResultItem(Violationame, Fine, strMessage,ViolationID));
			mResult.moveToNext();
		}
		return list;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
