package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import com.example.ithonge.R;
import com.example.ithonge.adapter.ListActionAdapter;
import com.example.models.DatabaseHelper;
import com.example.models.ListActionItem;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListActionAct extends Activity {
	private ListView mListAct;
	private ListActionAdapter mListActAdapter;
	private ArrayList<ListActionItem> mListActItems;
	private int[] mListActIcons;
	private String[] mListActTitles;

	// test - dungna.bka
	private DatabaseHelper mDatabaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_action);

		// creating listview (list action)
		mListAct = (ListView) findViewById(R.id.list);
		mListAct.setOnItemClickListener(new ListActOnItemClickListener());
		mListActIcons = new int[] { R.drawable.hv1, R.drawable.hv2, R.drawable.hv3, R.drawable.hv4, R.drawable.hv5, R.drawable.hv6,
				R.drawable.hv7, R.drawable.hv8, R.drawable.hv1, R.drawable.hv1, R.drawable.hv1 };
		mListActTitles = getResources().getStringArray(R.array.list_action_item);
		mListActItems = new ArrayList<ListActionItem>();
		for (int i = 0; i < mListActTitles.length; i++) {
			mListActItems.add(new ListActionItem(mListActIcons[i], mListActTitles[i]));
		}
		mListActAdapter = new ListActionAdapter(this, mListActItems);
		mListAct.setAdapter(mListActAdapter);

		// test - dungna.bka
		try {
			mDatabaseHelper = new DatabaseHelper(ListActionAct.this);
			Cursor mCursorTest = mDatabaseHelper.getAllFromTable("Groups");
			// String test =
			// mCursorTest.getString(mCursorTest.getColumnIndex("Group_Name"));
			mCursorTest.moveToFirst();
			Log.e("dungna", "" + mCursorTest.getCount());
			Log.e("dungna", "" + mCursorTest.toString());
			Log.e("dungna", "" + mCursorTest.getColumnCount());
			Log.e("dungna", "" + mCursorTest.getColumnName(2));
			int GroupNameIndex = mCursorTest.getColumnIndexOrThrow("Group_Name");
			Log.e("dungna", "" + mCursorTest.getString(GroupNameIndex) + "    " + GroupNameIndex);
		} catch (IOException e) {
			Log.e("Dungna", e.getMessage());
			Log.e("dungna", "Can't not convert cursor to string.");
		}

	}

	private class ListActOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			// do sothing
		}

	}
}
