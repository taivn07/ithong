package com.example.ithonge.activity;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ithong.models.DatabaseHelper;
import com.example.ithonge.R;
import com.example.ithonge.utils.Variables;

public class ListResultDetailAct extends Activity {
	private DatabaseHelper mDataBaseHelper;
	private long VioID;
	private ScrollView mScrollView;
	private TextView ObjText;
	private TextView MessText;
	private TextView FineText;
	private TextView AddPtext;
	private TextView AddP2text;
	private TextView Remetext;
	private boolean mainbookmarked, bookmarked;

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
		
		MakeListViewTask mk = new MakeListViewTask(this, VioID);
		mk.execute();

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
			mainbookmarked =true;
		} else {
			item.setIcon(R.drawable.ico_bookmark);
			mainbookmarked=false;
		}
		bookmarked = mainbookmarked;
		return super.onCreateOptionsMenu(menu);
	}

@Override
public void onBackPressed() {
	if (bookmarked!=mainbookmarked) {
		  if (mainbookmarked)	
			mDataBaseHelper.deleteFromTableLuuTru(VioID);
		  if (!mainbookmarked)
			mDataBaseHelper.insertintoTableLuuTru(VioID);
		} 	
	super.onBackPressed();
}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_bookmark:
			bookmarked= !bookmarked;
			if (!bookmarked)
			{
			item.setIcon(R.drawable.ico_bookmark);
			}
			else
			{
			item.setIcon(R.drawable.ico_bookmarked);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
