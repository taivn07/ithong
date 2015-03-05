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
import com.example.ithonge.adapter.ListSearchAdapter;
import com.example.ithonge.utils.Variables;
import com.example.models.DatabaseHelper;
import com.example.models.ListKeyWordItem;
import com.example.models.ListResultItem;

public class ListResultAct extends Activity implements SearchView.OnQueryTextListener {
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

	// search menu
	private String[] items;
	private Menu menu;
	private SearchView mSearchView;
	private MenuItem searchMenuItem;
	private ListView mListViewSearch;
	private ArrayList<ListKeyWordItem> mListKeyWordItems;
	private ListSearchAdapter mListViewSearchAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result);
//<<<<<<< HEAD
//		tvResultCount = (TextView) findViewById(R.id.tv_result_count);
//		optionPosition = getIntent().getExtras().getInt(Variables.TAG_OPTION_POSITION);
//		// edit by superstramp date: Thursday, March 05 2015
//		// tvResultCount.setText(getResources().getString(R.string.tv_muc)+":   "+getResources().getStringArray(R.array.list_action_item)[optionPosition]);
//		vehiclePosition = getIntent().getExtras().getInt(Variables.TAG_VEHICLE_POSITION);
//		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
//		getActionBar().setTitle(getResources().getStringArray(R.array.list_vehicles)[vehiclePosition]);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//
//=======
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
		tvResultCount.setText("Có " + mListResultItems.size() + " kết quả được tìm thấy.");

		// Create List Search
		mListViewSearch = (ListView) findViewById(R.id.lv_list_search);
		// mListViewSearch.setVisibility(View.VISIBLE);
		mListKeyWordItems = getKeySearchFromDatabase();
		Log.e("dungna", "onQueryTextChange: " + mListKeyWordItems.size());
		mListViewSearchAdapter = new ListSearchAdapter(this, mListKeyWordItems);
		mListViewSearch.setAdapter(mListViewSearchAdapter);
		mListViewSearch.setTextFilterEnabled(true);
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
		while (!mResult.isLast()) {
			Violationame = mResult.getString(mResult.getColumnIndex("Name"));
			Fine = mResult.getString(mResult.getColumnIndex("Fines"));
			strMessage = mResult.getString(mResult.getColumnIndex("Object"));
			ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
			list.add(new ListResultItem(Violationame, Fine, strMessage,ViolationID));
			mResult.moveToNext();
		}
		return list;
	}

	// get all key search (database: Keyword table)
	private ArrayList<ListKeyWordItem> getKeySearchFromDatabase() {
		ArrayList<ListKeyWordItem> list = new ArrayList<ListKeyWordItem>();
		String sql = "Select * From Keywords";
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		// get result and save to mListResultItems
		mResult.moveToFirst();
		String name = null;
		String nameEn = null;
		int count = 0;
		while (!mResult.isAfterLast()) {
			name = mResult.getString(mResult.getColumnIndex("Keyword_Name"));
			nameEn = mResult.getString(mResult.getColumnIndex("Keyword_NameEN"));
			list.add(new ListKeyWordItem(nameEn, nameEn));
			mResult.moveToNext();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_action, menu);
		SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchMenuItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchMenuItem.getActionView();
		mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
		mSearchView.setSubmitButtonEnabled(true);
		mSearchView.setOnQueryTextListener(this);
		// remove line under Edittext search
		int searchPlateId = mSearchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
		View searchPlateView = mSearchView.findViewById(searchPlateId);
		if (searchPlateView != null) {
			searchPlateView.setBackgroundColor(getResources().getColor(R.color.bg_list_key_search));
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// mListResultItems.set(index, object)
		Log.e("dungna", "onQueryTextChange");
		mListViewSearchAdapter.getFilter().filter(newText);
		Log.e("dungna", "1onQueryTextChange");
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			Log.e("dungna", "af75555tsdfhaksjfsdkl ------------------------------");
			mListViewSearch.setVisibility(View.VISIBLE);
			Log.e("dungna", "be5555tsdfhaksjfsdkl ------------------------------");
			// onSearchRequested();
			return true;
		case R.id.action_refresh:
			Log.e("dungna", "85555tsdfhaksjfsdkl ------------------------------");
			// onSearchRequested();
			return true;
		case R.id.action_location_found:
			Log.e("dungna", "95555tsdfhaksjfsdkl ------------------------------");
			// onSearchRequested();
			return true;

		case android.R.id.button1:
			Log.e("dungna", "51555tsdfhaksjfsdkl ------------------------------");
			// onSearchRequested();
			return true;
		case android.R.id.button2:
			Log.e("dungna", "52555tsdfhaksjfsdkl ------------------------------");
			// onSearchRequested();
			return true;
		case android.R.id.button3:
			Log.e("dungna", "53555tsdfhaksjfsdkl ------------------------------");
			// onSearchRequested();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
