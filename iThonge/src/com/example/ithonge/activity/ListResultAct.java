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

import com.example.ithong.models.DatabaseHelper;
import com.example.ithong.models.ListKeyWordItem;
import com.example.ithong.models.ListResultItem;
import com.example.ithonge.R;
import com.example.ithonge.adapter.ListResultAdapter;
import com.example.ithonge.adapter.ListSearchAdapter;
import com.example.ithonge.utils.Utils;
import com.example.ithonge.utils.Variables;

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
	private SearchView mSearchView;
	private MenuItem searchMenuItem;
	private ListView mListViewSearch;
	private ArrayList<ListKeyWordItem> mListKeyWordItems;
	private ListSearchAdapter mListViewSearchAdapter;
	private int group_value;
	private int type_value;
	private int[] Mahoa_Type = new int[] { 64, 32, 16, 8, 4, 2, 1 };
	private int[] Mahoa_Group = new int[] { 128, 64, 32, 16, 8, 4, 2, 1 };

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
		getActionBar().setDisplayHomeAsUpEnabled(false);
		// init local variables
		tvResultCount = (TextView) findViewById(R.id.tv_result_count);

		mListResultItems = new ArrayList<ListResultItem>();
		try {
			mDatabaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			Log.e(LOG_TAG, "Can't Create Database. Please check and try again. Error: " + e.getMessage());
		}

		// create listview
		mListResultItems = getResultFromViolation(optionPosition, vehiclePosition);
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

	// Lua chon tim kiem tu co so du lieu
	private void SetData(int groupId, int vehicleID) {
		switch (vehicleID) {
		case 6:
			group_value = 0;
			type_value = 64;
			break;
		default:
			group_value = (int) Math.pow(2, groupId);
			type_value = (int) Math.pow(2, vehicleID);
			break;
		}
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
			textView.setText(tempKey.getNameEN().toString());
		}

	}

	private boolean Check_Group(int AgroupId) {
		if (group_value == 0)
			return true;
		if (AgroupId != 0)
			for (int i = 0; i < Mahoa_Group.length; i++) {
				if (AgroupId >= Mahoa_Group[i]) {
					AgroupId -= Mahoa_Group[i];
					if (Mahoa_Group[i] == group_value)
						return true;
				}
			}
		return false;
	}

	private boolean Check_Type(int AvehicleID) {
		if (AvehicleID != 0)
			for (int i = 0; i < Mahoa_Type.length; i++) {
				if (AvehicleID >= Mahoa_Type[i]) {
					AvehicleID -= Mahoa_Type[i];
					if (Mahoa_Type[i] == type_value)
						return true;
				}
			}
		return false;
	}

	// Name String filter

	// get result from position: group id(database: Groups table)
	private ArrayList<ListResultItem> getResultFromViolation(int groupId, int vehicleID) {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();
		SetData(groupId, vehicleID);
		String sql = "Select * From Violation";

		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		// get result and save to mListResultItems
		mResult.moveToFirst();
		String ViolatioName = null;
		String Fine = null;
		String strMessage = null;
		String violationNameEn = null;
		long ViolationID = 0;
		int AgroupID = 0;
		int AvehicleID = 0;
		if (mResult.getCount() != 0)
			while (!mResult.isAfterLast()) {
				AgroupID = mResult.getInt(mResult.getColumnIndex("Group_Value"));
				AvehicleID = mResult.getInt(mResult.getColumnIndex("Type_Value"));
				if (Check_Type(AvehicleID) && Check_Group(AgroupID)) {
					ViolatioName = mResult.getString(mResult.getColumnIndex("Name"));
					violationNameEn = mResult.getString(mResult.getColumnIndex("NameEN"));
					Fine = mResult.getString(mResult.getColumnIndex("Fines"));
					strMessage = mResult.getString(mResult.getColumnIndex("Object"));
					ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
					list.add(new ListResultItem(ViolatioName, violationNameEn, Fine, strMessage, ViolationID));
				}
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

		searchMenuItem.setOnActionExpandListener(new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				mListViewSearch.setVisibility(View.INVISIBLE);
				return true; // Return true to collapse action view
			}

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		mListViewSearch.setVisibility(View.INVISIBLE);
		ArrayList<ListResultItem> temp = new ArrayList<ListResultItem>();
		temp = getResultByTextInput(query);
		if ((temp != null) && (temp.size() != 0)) {
			mListResultItems.clear();
			mListResultItems.addAll(temp);
			mListResultAdapter.notifyDataSetChanged();
			Log.e("Dungna", "not null");
		}
		Log.e("Dungna", "OnqueryTextsubmit");
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// mListResultItems.set(index, object)
		mListViewSearch.setVisibility(View.VISIBLE);
		mListViewSearchAdapter.getFilter().filter(newText);
		Variables.currentListResultItems.clear();
		Variables.currentListResultItems.addAll(mListResultItems);
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

	// input text and search result from mListResult item
	private ArrayList<ListResultItem> getResultByTextInput(String textInput) {
		Log.e("Dungna", "11");
		ArrayList<ListResultItem> tempList = new ArrayList<ListResultItem>();
		for (ListResultItem item : mListResultItems) {
			if (item.getVioNameEn().toLowerCase().contains(textInput.toString().toLowerCase())) {
				tempList.add(item);
			}
		}
		Log.e("Dungna", "11: " + tempList.size());
		return tempList;
	}

	// comment by dungna, date: 03/10/2015
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e("Dungna", "not null");
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		Log.e("Dungna", "1not null");
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.e("Dungna", "2not null");
			ArrayList<ListResultItem> temp = new ArrayList<ListResultItem>();
			temp = getResultByTextInput(query);
			if ((temp != null) && (temp.size() != 0)) {
				mListResultItems = temp;
				mListResultAdapter.notifyDataSetChanged();
				tvResultCount.setText("Có " + mListResultItems.size() + " kết quả được tìm thấy.");
				Log.e("Dungna", "not null");
			}

		}
	}

	// Read more:
	// http://www.androidhub4you.com/2014/04/android-action-bar-search-inside.html#ixzz3TwqGjDtQ
	// end commnet
}
