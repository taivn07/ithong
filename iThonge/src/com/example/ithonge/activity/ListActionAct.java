package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_action);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// creating listview (list action)
		mListAct = (ListView) findViewById(R.id.lv_list_action);
		mListAct.setOnItemClickListener(new ListActOnItemClickListener());
		mListActIcons = new int[] { R.drawable.hv1, R.drawable.hv2, R.drawable.hv3, R.drawable.hv4, R.drawable.hv5, R.drawable.hv6,
				R.drawable.hv7, R.drawable.hv8};
		mListActTitles = getResources().getStringArray(R.array.list_action_item);
		mListActItems = new ArrayList<ListActionItem>();
		for (int i = 0; i < mListActTitles.length; i++) {
			mListActItems.add(new ListActionItem(mListActIcons[i], mListActTitles[i]));
		}
		mListActAdapter = new ListActionAdapter(this, mListActItems);
		mListAct.setAdapter(mListActAdapter);

		// test - dungna.bka
		linTest = (LinearLayout) findViewById(R.id.linTest);
		context = this;
	}

	private class ListActOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(ListActionAct.this, ListResultAct.class);
			startActivity(intent);
		}
	}

	public class Test extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				mDatabaseHelper = new DatabaseHelper(ListActionAct.this);
				Cursor mCursorTest = mDatabaseHelper.getAllFromTable("Groups");
				// String test =
				// mCursorTest.getString(mCursorTest.getColumnIndex("Group_Name"));
				mCursorTest.moveToFirst();
				int i = 0;
				while (!mCursorTest.isLast()) {
					i++;
					Log.e("dungna", "" + mCursorTest.getCount());
					Log.e("dungna", "" + mCursorTest.toString());
					Log.e("dungna", "" + mCursorTest.getColumnCount());
					int GroupNameIndex = mCursorTest.getColumnIndexOrThrow("block");
					byte[] bData = mCursorTest.getBlob(GroupNameIndex);
					String wordTest = new String(bData);
					Log.e("dungna", i + ". " + wordTest);
					publishProgress(wordTest);

					mCursorTest.moveToNext();
				}
			} catch (IOException e) {
				Log.e("Dungna", e.getMessage());
				Log.e("dungna", "Can't not convert cursor to string.");
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			TextView tvTest = new TextView(context);
			tvTest.setText(values[0]);
			linTest.addView(tvTest);
		}

	}
}
