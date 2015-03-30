package com.paditech.atgt.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.paditech.atgt.R;
import com.paditech.atgt.adapter.ListResultAdapter;
import com.paditech.atgt.models.DatabaseHelper;
import com.paditech.atgt.models.ListResultItem;
import com.paditech.atgt.utils.ExpandableHeightListView;
import com.paditech.atgt.utils.Utils;
import com.paditech.atgt.utils.Variables;

public class MakeListViewTask extends AsyncTask<Void, Void, Void> {

	private Activity context;
	private ExpandableHeightListView mkListView;
	private ArrayList<ListResultItem> mkListResultItems;
	private ListResultAdapter mkListResultAdapter;
	private ProgressBar mkProgressBar;
	private DatabaseHelper mkDatabaseHelper;
	private long VioID;

	public MakeListViewTask(Activity context, long VioID) {
		this.context = context;
		this.VioID = VioID;
		mkListView = (ExpandableHeightListView) this.context.findViewById(R.id.lv_list_hvlq);
		mkProgressBar = (ProgressBar) this.context.findViewById(R.id.progressBar1);
		mkProgressBar.setVisibility(View.VISIBLE);
		try {
			mkDatabaseHelper = new DatabaseHelper(this.context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	private ArrayList<ListResultItem> GetDataFromViolationTable(long VioID) {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();
		String sql = "Select Violation.* From Violation,Relations Where Relations.Violation_ID =" + VioID
				+ " and Relations.R_Violation_ID = Violation.ID";
		Cursor mResult = mkDatabaseHelper.getResultFromSQL(sql);
		// get result and save to mListResultItems
		Log.e("SQL-----", "" + mResult.getCount());
		mResult.moveToFirst();
		String Violationame = null;
		String Fine = null;
		String strMessage = null;
		long ViolationID = 0;
		if (mResult.getCount() != 0)
			while (!mResult.isAfterLast()) {

				Violationame = Utils.ReNameFilter(mResult.getString(mResult.getColumnIndex("Name")));
				Fine = mResult.getString(mResult.getColumnIndex("Fines"));
				strMessage = mResult.getString(mResult.getColumnIndex("Object"));
				ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
				list.add(new ListResultItem(Violationame, null, Fine, strMessage, ViolationID));

				mResult.moveToNext();
			}
		return list;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		mkListResultItems = GetDataFromViolationTable(VioID);
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		mkListResultAdapter = new ListResultAdapter(context, mkListResultItems);
		mkListView.setOnItemClickListener(new ListResutlItemOnClickListener());
		mkListView.setAdapter(mkListResultAdapter);
		mkProgressBar.setVisibility(View.GONE);
		super.onPostExecute(result);
	}

	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Log.e("Count", "" + Variables.CLICK_TO_SHOW_ADS_COUNT);
			if (Variables.CLICK_TO_SHOW_ADS_COUNT == Variables.CLICK_TO_SHOW_ADS) {
				Utils.showFullAds(context);
				// Variables.CLICK_TO_SHOW_ADS_COUNT = 0;
			} else {
				Variables.CLICK_TO_SHOW_ADS_COUNT++;
			}
			Intent intent = new Intent(context, ResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID, mkListResultItems.get(position).getVioID());
			context.startActivity(intent);
			context.finish();
		}
	}
}
