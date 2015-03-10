package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ithonge.R;
import com.example.ithonge.adapter.ListResultAdapter;
import com.example.ithonge.utils.ExpandableHeightListView;
import com.example.ithonge.utils.Utils;
import com.example.ithonge.utils.Variables;
import com.example.models.DatabaseHelper;
import com.example.models.ListResultItem;

public class ListResultDetailAct extends Activity {
	private DatabaseHelper mDataBaseHelper;
	private long VioID;
	private ExpandableHeightListView mListView;
	private ScrollView mScrollView;
	private ArrayList<ListResultItem> mListResultItems;
	private ListResultAdapter mListResultAdapter;
	private TextView ObjText;
	private TextView MessText;
	private TextView FineText;
	private TextView AddPtext;
	private TextView AddP2text;
	private TextView Remetext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result_detail);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(R.string.tv_chitiet);

		ObjText = (TextView) findViewById(R.id.show_object);
		MessText = (TextView) findViewById(R.id.show_mess);
		FineText = (TextView) findViewById(R.id.show_fine);
		AddPtext = (TextView) findViewById(R.id.show_additionP);
		AddP2text = (TextView) findViewById(R.id.show_additionP2);
		Remetext = (TextView) findViewById(R.id.show_reme);

		VioID = getIntent().getExtras().getLong(Variables.TAG_VIOLATIOID);

		try {
			mDataBaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
Log.e("VioID", ""+VioID);
		 SetView(VioID);

	}

	private void SetView(long VioID) {

		String sql = "Select * From Violation Where ID = " + VioID;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);
		mResult.moveToFirst();
		Log.e("SQl", ""+sql);
		Log.e("cc", ""+mResult.getCount());

		if (mResult.getCount() != 0) {
			ObjText.setText(mResult.getString(mResult.getColumnIndex("Object")));
			MessText.setText(mResult.getString(mResult.getColumnIndex("Name")));
			FineText.setText(mResult.getString(mResult.getColumnIndex("Fines")));

			String temp = mResult.getString(mResult
					.getColumnIndex("Additional_Penalties"));
			if (temp != null && temp != "" && temp != "null") {
				AddPtext.setText(temp);
			}
			temp = mResult.getString(mResult.getColumnIndex("Other_Penalties"));
			if (temp != null && temp != "" && temp != "null") {
				AddP2text.setText(temp);
			}
			temp = mResult.getString(mResult
					.getColumnIndex("Remedial_Measures"));
			if (temp != null && temp != "" && temp != "null") {
				Remetext.setText(temp);
			}
		}

		mScrollView = (ScrollView) findViewById(R.id.show_result);
		mScrollView.fullScroll(ScrollView.FOCUS_UP);
		mScrollView.smoothScrollTo(0, 0);
		mListView = (ExpandableHeightListView) findViewById(R.id.lv_list_hvlq);
		mListView.setExpanded(true);
		mListResultItems = getResultFromViolation(VioID);
		mListResultAdapter = new ListResultAdapter(this, mListResultItems);
		mListView.setOnItemClickListener(new ListResutlItemOnClickListener());
		mListView.setAdapter(mListResultAdapter);

	}

	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ListResultDetailAct.this, ListResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID, mListResultItems.get(position).getVioID());	
			startActivity(intent);
			finish();
		}
	}

	
	private ArrayList<ListResultItem> getResultFromViolation(long VioID) {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();

		String sql = "Select Violation.* From Violation,Relations Where Relations.Violation_ID ="+VioID+" and Relations.R_Violation_ID = Violation.ID";
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);
		// get result and save to mListResultItems
		Log.e("SQL-----", "" + mResult.getCount());
		mResult.moveToFirst();
		String Violationame = null;
		String Fine = null;
		String strMessage = null;
		long ViolationID = 0;
		if (mResult.getCount() != 0)
			while (!mResult.isAfterLast()) {

				Violationame = Utils.ReNameFilter(mResult.getString(mResult
						.getColumnIndex("Name")));
				Fine = mResult.getString(mResult.getColumnIndex("Fines"));
				strMessage = mResult
						.getString(mResult.getColumnIndex("Object"));
				ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
				list.add(new ListResultItem(Violationame, Fine, strMessage,
						ViolationID));

				mResult.moveToNext();
			}
		return list;
	}

	private boolean Checkbookmarked() {
		String sql = "Select * From LuuTru Where Violation_ID = " + VioID;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);

		if (mResult.getCount() != 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_act_bookmark, menu);
		MenuItem item = menu.getItem(0);
		if (Checkbookmarked()) {
			item.setIcon(R.drawable.ico_bookmarked);
		} else {
			item.setIcon(R.drawable.ico_bookmark);
		}
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_bookmark:
			if (Checkbookmarked()) {
				item.setIcon(R.drawable.ico_bookmark);
				mDataBaseHelper.deleteFromTableLuuTru(VioID);
			} else {
				item.setIcon(R.drawable.ico_bookmarked);
				mDataBaseHelper.insertintoTableLuuTru(VioID);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
