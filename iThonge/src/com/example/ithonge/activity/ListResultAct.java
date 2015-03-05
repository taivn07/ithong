package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
	private TextView optiontitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result);
		optiontitle = (TextView) findViewById(R.id.tv_result_count);
		optionPosition = getIntent().getExtras().getInt(Variables.TAG_OPTION_POSITION);
		optiontitle.setText(getResources().getString(R.string.tv_muc)+":   "+getResources().getStringArray(R.array.list_action_item)[optionPosition]);
		vehiclePosition = getIntent().getExtras().getInt(Variables.TAG_VEHICLE_POSITION);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(getResources().getStringArray(R.array.list_vehicles)[vehiclePosition]);
		// init local variables
		mListResultItems = new ArrayList<ListResultItem>();
		try {
			mDatabaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			Log.e(LOG_TAG, "Can't Create Database. Please check and try again. Error: " + e.getMessage());
		}
		// create listview
		mListResultItems = getResultFromGroupId(optionPosition);
		mListResult = (ListView) findViewById(R.id.lv_list_result);
		mListResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		mListResultAdapter = new ListResultAdapter(this, mListResultItems);
		mListResult.setAdapter(mListResultAdapter);
	}

	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
                Log.e("aaaa","s");
		}
	}

	// get result from position: group id(database: Groups table)
	private ArrayList<ListResultItem> getResultFromGroupId(int groupId) {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();
		int group_value = (int) Math.pow(2, groupId);
		String sql = "Select * From Violation Where Group_Value = " +group_value;
		Log.e(LOG_TAG, sql);
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		Log.e(LOG_TAG, "" + mResult.getCount());
		// get result and save to mListResultItems
		mResult.moveToFirst();
		String strTitle = null;
		String strMessage = null;
		int count = 0;
		while (!mResult.isLast()) {
			strTitle = mResult.getString(mResult.getColumnIndex("Object"));
			strMessage = mResult.getString(mResult.getColumnIndex("Name"));
			list.add(new ListResultItem(strTitle, strMessage));
			mResult.moveToNext();
		}
		Log.e(LOG_TAG, "list: " + list.size());
		return list;
	}

}
