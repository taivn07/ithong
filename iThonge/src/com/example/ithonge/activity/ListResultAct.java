package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.ParcelFileDescriptor.OnCloseListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.WindowManager;
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

public class ListResultAct extends Activity implements SearchView.OnQueryTextListener{
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
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		optionPosition = getIntent().getExtras().getInt(Variables.TAG_OPTION_POSITION);
		vehiclePosition = getIntent().getExtras().getInt(Variables.TAG_VEHICLE_POSITION);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(
				getResources().getStringArray(R.array.list_vehicles)[vehiclePosition] + "  >>  "
						+ getResources().getStringArray(R.array.list_action_item)[optionPosition]);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// init local variables
		tvResultCount = (TextView) findViewById(R.id.tv_result_count);
		
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
		mListViewSearchAdapter = new ListSearchAdapter(this, mListKeyWordItems);
		mListViewSearch.setAdapter(mListViewSearchAdapter);
		mListViewSearch.setTextFilterEnabled(true);
		mListViewSearch.setOnItemClickListener(new ListKeyWordOnItemClickListener());

	}

	// when click list result item
	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ListResultAct.this, ListResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID, mListResultItems.get(position).getVioID());
			startActivity(intent);
		}
	}

	// when click list keyword item
	private class ListKeyWordOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			mListViewSearch.setVisibility(View.INVISIBLE);
			// SearchView searchView= (SearchView)
			// findViewById(R.id.searchView1);
			int tvid = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
			TextView textView = (TextView) mSearchView.findViewById(tvid);
			ListKeyWordItem tempKey = (ListKeyWordItem) mListViewSearchAdapter.getItem(position);
			textView.setText(tempKey.getName().toString());
		}

	}

	// get result from position: group id(database: Groups table)
	private ArrayList<ListResultItem> getResultFromGroupId(int groupId, int vehicleID) {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();
		int group_value = (int) Math.pow(2, groupId);
		int type_value = (int) Math.pow(2, vehicleID);
		String sql = "Select * From Violation Where Group_Value = " + group_value + " and Type_Value = " + type_value;
		Log.e(LOG_TAG, sql);
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		// get result and save to mListResultItems
		Log.e("dungna", "" + mResult.getCount());
		mResult.moveToFirst();
		String Violationame = null;
		String Fine = null;
		String strMessage = null;
		long ViolationID = 0;
		if (mResult.getCount()!=0)
		while (!mResult.isAfterLast()) {
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
		while (!mResult.isAfterLast()) {
			name = mResult.getString(mResult.getColumnIndex("Keyword_Name"));
			nameEn = mResult.getString(mResult.getColumnIndex("Keyword_NameEN"));
			list.add(new ListKeyWordItem(name, nameEn));
			mResult.moveToNext();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_action, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchMenuItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchMenuItem.getActionView();

		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		mSearchView.setSubmitButtonEnabled(true);
		mSearchView.setOnQueryTextListener(this);

	  
	       searchMenuItem.setOnActionExpandListener(new OnActionExpandListener()
	        {

	            @Override
	            public boolean onMenuItemActionCollapse(MenuItem item)
	            {
	            	mListViewSearch.setVisibility(View.INVISIBLE);  
	                return true; // Return true to collapse action view
	            }

	            @Override
	            public boolean onMenuItemActionExpand(MenuItem item)
	            {	            
	                // TODO Auto-generated method stub
	                return true;
	            }
	        });
	        
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		mListViewSearch.setVisibility(View.INVISIBLE);
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// mListResultItems.set(index, object)
		mListViewSearchAdapter.getFilter().filter(newText);
		Log.e("dungna", "1onQueryTextChange");
		return false;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			mListViewSearch.setVisibility(View.VISIBLE);
			return true;
			// case R.id.action_refresh:
			// return true;
			// case R.id.action_location_found:
			// return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
