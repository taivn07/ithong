package com.paditech.atgt.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.paditech.atgt.R;
import com.paditech.atgt.adapter.ListResultAdapter;
import com.paditech.atgt.adapter.ListSearchAdapter;
import com.paditech.atgt.models.DatabaseHelper;
import com.paditech.atgt.models.ListKeyWordItem;
import com.paditech.atgt.models.ListResultItem;
import com.paditech.atgt.utils.Utils;
import com.paditech.atgt.utils.Variables;

public class ListResultFragment extends Fragment implements SearchView.OnQueryTextListener {
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_list_result, container, false);

		getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Bundle bundle = this.getArguments();
		optionPosition = bundle.getInt(Variables.TAG_OPTION_POSITION);
		vehiclePosition = bundle.getInt(Variables.TAG_VEHICLE_POSITION);
		getActivity().getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActivity().getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		if (vehiclePosition < 6) {
			getActivity().setTitle(
					getResources().getStringArray(R.array.list_vehicles)[vehiclePosition] + "  >>  "
							+ getResources().getStringArray(R.array.list_action_item)[optionPosition]);
		} else {
			getActivity().setTitle(getResources().getStringArray(R.array.list_vehicles)[vehiclePosition]);
		}

		Variables.currentTitle = getActivity().getActionBar().getTitle().toString();
		// init local variables
		tvResultCount = (TextView) rootView.findViewById(R.id.tv_result_count);

		mListResultItems = new ArrayList<ListResultItem>();
		try {
			mDatabaseHelper = new DatabaseHelper(getActivity());
		} catch (IOException e) {
			Log.e(LOG_TAG, "Can't Create Database. Please check and try again. Error: " + e.getMessage());
		}

		// create listview
		mListResultItems = getResultFromViolation(optionPosition, vehiclePosition);
		mListResult = (ListView) rootView.findViewById(R.id.lv_list_result);
		mListResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		mListResultAdapter = new ListResultAdapter(getActivity(), mListResultItems);
		mListResult.setAdapter(mListResultAdapter);

		tvResultCount.setText("Có " + mListResultItems.size() + " kết quả được tìm thấy.");

		// Create List Search
		mListViewSearch = (ListView) rootView.findViewById(R.id.lv_list_search);
		// mListViewSearch.setVisibility(View.VISIBLE);
		mListKeyWordItems = getKeySearchFromDatabase();
		mListViewSearchAdapter = new ListSearchAdapter(getActivity(), mListKeyWordItems);
		mListViewSearch.setAdapter(mListViewSearchAdapter);
		mListViewSearch.setTextFilterEnabled(true);
		mListViewSearch.setOnItemClickListener(new ListKeyWordOnItemClickListener());

		// rootView.setOnTouchListener(this);
		return rootView;
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
			// check to show ads
			Log.e("Count", "" + Variables.CLICK_TO_SHOW_ADS_COUNT);
			if (Variables.CLICK_TO_SHOW_ADS_COUNT == Variables.CLICK_TO_SHOW_ADS) {
				Utils.showFullAds(getActivity());
				//Variables.CLICK_TO_SHOW_ADS_COUNT = 0;
			} else {
				Variables.CLICK_TO_SHOW_ADS_COUNT++;
			}
			Intent intent = new Intent(getActivity(), ResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID, mListResultItems.get(position).getVioID());
			// intent.putExtra(Variables.TAG_OBJECT,
			// mListResultItems.get(position).get);
			intent.putExtra(Variables.TAG_VIOLATION_NAME, mListResultItems.get(position).getVioName());
			intent.putExtra(Variables.TAG_VIOLATION_NAME, mListResultItems.get(position).getVioName());
			intent.putExtra(Variables.TAG_VIOLATION_NAME, mListResultItems.get(position).getVioName());
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
					ViolatioName = mResult.getString(mResult.getColumnIndex("Name"))
							+ mResult.getString(mResult.getColumnIndex("LastUpdated"));
					ViolatioName = Utils.ReNameFilter(mResult.getString(mResult.getColumnIndex("Name")));
					violationNameEn = (String) Utils.unAccent(ViolatioName);
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.main_activity_action, menu);
		// mListViewSearch.setVisibility(View.INVISIBLE);

		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		searchMenuItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchMenuItem.getActionView();

		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
				mListViewSearch.setVisibility(View.VISIBLE);
				// TODO Auto-generated method stub
				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		mListViewSearch.setVisibility(View.INVISIBLE);
		searchMenuItem.collapseActionView();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mListViewSearchAdapter.getFilter().filter(newText);
		Variables.currentListResultItems.clear();
		Variables.currentListResultItems.addAll(mListResultItems);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			mListViewSearch.setVisibility(View.VISIBLE);
			return true;

		default:
			mListViewSearch.setVisibility(View.INVISIBLE);
			return super.onOptionsItemSelected(item);
		}
	}
}
