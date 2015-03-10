package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.ithonge.utils.Variables;

public class SearchResultAct extends Activity {

	// ListView Result
	private ListView mListSearchResult;
	private ListResultAdapter mListSearchResultAdapter;
	private ArrayList<ListResultItem> mListSearchResultItems;
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
		setContentView(R.layout.activity_list_search_result);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		//Variables.currentTitle = getActionBar().getTitle().toString();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// init local variables
		tvResultCount = (TextView) findViewById(R.id.tv_search_result_count);
		mListSearchResultItems = new ArrayList<ListResultItem>();
		try {
			mDatabaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			Log.e(LOG_TAG, "Can't Create Database. Please check and try again. Error: " + e.getMessage());
		}

		// create listview
		// mListSearchResultItems = getResultFromGroupId(optionPosition,
		// vehiclePosition);
		mListSearchResult = (ListView) findViewById(R.id.lv_list_search_result);
		mListSearchResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		// mListResultAdapter = new ListResultAdapter(this, mListResultItems);
		// mListResult.setAdapter(mListResultAdapter);
		// tvResultCount.setText("Có " + mListResultItems.size() +
		// " kết quả được tìm thấy.");
		handleIntent(getIntent());
	}

	// when click list result item
	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SearchResultAct.this, ListResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID, Variables.currentListResultItems.get(position).getVioID());
			startActivity(intent);
		}
	}

	// input text and search result from mListResult item
	private ArrayList<ListResultItem> getResultByTextInput(String textInput) {
		Log.e("Dungna", "11");
		ArrayList<ListResultItem> tempList = new ArrayList<ListResultItem>();
		for (ListResultItem item : Variables.currentListResultItems) {
			if (item.getVioNameEn().toLowerCase().contains(textInput.toString().toLowerCase())) {
				tempList.add(item);
			}
		}
		Log.e("Dungna", "11: " + tempList.size());
		return tempList;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e("Dungna", "not null");
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			getActionBar().setTitle(Variables.currentTitle + " >> " + query);
			Variables.currentTitle = getActionBar().getTitle().toString();
			Log.e("Dungna", "2not null");
			ArrayList<ListResultItem> temp = new ArrayList<ListResultItem>();
			temp = getResultByTextInput(query);
			if ((temp != null) && (temp.size() != 0)) {
				mListSearchResultItems = temp;
				mListSearchResultAdapter = new ListResultAdapter(this, mListSearchResultItems);
				mListSearchResult.setAdapter(mListSearchResultAdapter);
				tvResultCount.setText("Có " + mListSearchResultItems.size() + " kết quả được tìm thấy.");
			} else {
				tvResultCount.setText("Không tìm thấy kết quả nào.");
			}
		}
	}
}