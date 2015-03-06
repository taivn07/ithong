package com.example.ithonge.activity;

import java.io.IOException;

import com.example.ithonge.R;
import com.example.ithonge.utils.Variables;
import com.example.models.DatabaseHelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class ListResultDetailAct extends Activity{
  private DatabaseHelper mDataBaseHelper;
  private long VioID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result_detail);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(R.string.tv_chitiet);
		
		TextView ObjText = (TextView) findViewById(R.id.show_object);
		TextView MessText = (TextView) findViewById(R.id.show_mess);
		TextView FineText = (TextView) findViewById(R.id.show_fine);
		TextView AddPtext = (TextView) findViewById(R.id.show_additionP);
		TextView AddP2text = (TextView) findViewById(R.id.show_additionP2);
		TextView Remetext = (TextView) findViewById(R.id.show_reme);
		
		
		VioID = getIntent().getExtras().getLong(Variables.TAG_VIOLATIOID);
		
		try {
			mDataBaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "Select * From Violation Where ID = " +VioID ;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);
		mResult.moveToFirst();
		
		if (mResult.getCount()!=0)
		{
			ObjText.setText(mResult.getString(mResult.getColumnIndex("Object")));
			MessText.setText(mResult.getString(mResult.getColumnIndex("Name")));
			FineText.setText(mResult.getString(mResult.getColumnIndex("Fines")));
			
			String temp = mResult.getString(mResult.getColumnIndex("Additional_Penalties"));
			if (temp != null && temp!="" && temp!="null")
			{
				AddPtext.setText(temp);
			}
			temp = mResult.getString(mResult.getColumnIndex("Other_Penalties"));
			if (temp != null && temp!="" && temp!="null")
			{
				AddP2text.setText(temp);
			}
			temp = mResult.getString(mResult.getColumnIndex("Remedial_Measures"));
			if (temp != null && temp!="" && temp!="null")
			{
				Remetext.setText(temp);
			}
		}
		
		
	}
	
	
	private boolean Checkbookmarked()
	{
		String sql = "Select * From LuuTru Where Violation_ID = " +VioID ;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);
		
		if (mResult.getCount()!=0)
		{
		return true;
		}
		
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_act_bookmark, menu);
		MenuItem item = menu.getItem(0);
		if (Checkbookmarked())
    	{
			item.setIcon(R.drawable.ico_bookmarked);
    	}
    	else {
    		item.setIcon(R.drawable.ico_bookmark);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_bookmark:
	    	if (Checkbookmarked())
	    	{
	           item.setIcon(R.drawable.ico_bookmark);
	           mDataBaseHelper.deleteFromTableLuuTru(VioID);
	    	}
	    	else {
	    	   item.setIcon(R.drawable.ico_bookmarked);
	    	   mDataBaseHelper.insertintoTableLuuTru(VioID);
			}
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
