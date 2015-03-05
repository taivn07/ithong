package com.example.ithonge.activity;

import java.io.IOException;

import com.example.ithonge.R;
import com.example.ithonge.utils.Variables;
import com.example.models.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ListResultDetailAct extends Activity{
  private DatabaseHelper mDataBaseHelper;
  private long RowID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result_detail);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(R.string.tv_chitiet);
		
		TextView ObjText = (TextView) findViewById(R.id.show_object);
		TextView MessText = (TextView) findViewById(R.id.show_mess);
		TextView FineText = (TextView) findViewById(R.id.show_fine);
		TextView AddPtext = (TextView) findViewById(R.id.show_additionP);
		TextView AddP2text = (TextView) findViewById(R.id.show_additionP2);
		TextView Remetext = (TextView) findViewById(R.id.show_reme);
		
		
		RowID = getIntent().getExtras().getLong(Variables.TAG_VIOLATIOID);
		
		try {
			mDataBaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "Select * From Violation Where ID = " +RowID ;
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
}
