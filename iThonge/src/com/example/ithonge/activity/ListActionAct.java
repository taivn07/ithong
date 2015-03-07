package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ithonge.R;
import com.example.ithonge.adapter.ListActionAdapter;
import com.example.ithonge.utils.Variables;
import com.example.models.DatabaseHelper;
import com.example.models.ListActionItem;

public class ListActionAct extends Activity {
	private ListView mListAct;
	private ListActionAdapter mListActAdapter;
	private ArrayList<ListActionItem> mListActItems;
	private int[] mListActIcons;
	private String[] mListActTitles;
	public Context context;

	// test - dungna.bka
	private DatabaseHelper mDatabaseHelper;
	private LinearLayout linTest;
	private ImageView imgvTest;
	private int vehiclePosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_action);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		vehiclePosition = getIntent().getExtras().getInt(Variables.TAG_VEHICLE_POSITION);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setTitle(getResources().getStringArray(R.array.list_vehicles)[vehiclePosition]);
		// init local variable
		try {
			mDatabaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mListAct = (ListView) findViewById(R.id.lv_list_action);
		mListActIcons = new int[] { R.drawable.hv1, R.drawable.hv2, R.drawable.hv3, R.drawable.hv4, R.drawable.hv5, R.drawable.hv6,
				R.drawable.hv7, R.drawable.hv8};
		mListActTitles = getResources().getStringArray(R.array.list_action_item);
		mListAct.setOnItemClickListener(new ListActOnItemClickListener());
		mListActItems = new ArrayList<ListActionItem>();
		// get list Action from Database and create listview
		mListActItems = getListActionFromDB(vehiclePosition + 1);
		mListActAdapter = new ListActionAdapter(this, mListActItems);
		mListAct.setAdapter(mListActAdapter);
	}

	private class ListActOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(ListActionAct.this, ListResultAct.class);
			intent.putExtra(Variables.TAG_OPTION_POSITION, mListActItems.get(position).getpos());
			intent.putExtra(Variables.TAG_VEHICLE_POSITION, vehiclePosition);
			startActivity(intent);
		}
	}


	private ArrayList<ListActionItem> getListActionFromDB(int vehicleID) {
		ArrayList<ListActionItem> list = new ArrayList<ListActionItem>();
		String sql = "Select * From Group_Type Where TransportID = " + vehicleID;
		Log.e("ListActionAct", sql);
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		Log.e("ListActionAct", "" + mResult.getCount());
		mResult.moveToFirst();

		do {
			int groupID = mResult.getInt(mResult.getColumnIndex("GroupID"));
			Log.e("ListActionAct", "groupID: " + groupID);
			if (groupID != 0) {
				ListActionItem item = new ListActionItem(mListActIcons[groupID - 1], mListActTitles[groupID - 1], (groupID-1));
				list.add(item);
			}
			mResult.moveToNext();
		} while (!mResult.isAfterLast());

		return list;
	}
}
