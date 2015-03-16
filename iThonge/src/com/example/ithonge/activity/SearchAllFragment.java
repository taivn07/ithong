package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.example.ithong.models.DatabaseHelper;
import com.example.ithong.models.ListResultItem;
import com.example.ithonge.R;
import com.example.ithonge.adapter.ListResultAdapter;
import com.example.ithonge.utils.Utils;
import com.example.ithonge.utils.Variables;

public class SearchAllFragment extends Fragment{
	private SearchView searchView;
	private ArrayList<ListResultItem> listResultItems;
	private ArrayList<ListResultItem> searchlistResultItems;
	private ListResultAdapter listResultAdapter;
	private ListView resultListView;
	private DatabaseHelper databaseHelper;
	private ProgressBar progressSearch;
	private TextView tvResultCount;

	public SearchAllFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		try {
			databaseHelper = new DatabaseHelper(getActivity());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listResultItems = null;
		super.onCreate(savedInstanceState);
	}
	
	private ArrayList<ListResultItem> getListItemformDatabase() {
		ArrayList<ListResultItem> tempList = new ArrayList<ListResultItem>();
		String sql = "Select * From Violation Where Type_Value <>0 or Group_Value <>0";
		Cursor mResult = databaseHelper.getResultFromSQL(sql);
		mResult.moveToFirst();
		
		String ViolatioName = null;
		String Fine = null;
		String strMessage = null;
		long ViolationID = 0;
		Log.e("Annn",""+mResult.getCount());
		if (mResult.getCount() != 0)
			while (!mResult.isAfterLast()) {				
					ViolatioName = Utils.ReNameFilter(mResult.getString(mResult.getColumnIndex("Name")));
					Fine = mResult.getString(mResult.getColumnIndex("Fines"));
					strMessage = mResult.getString(mResult.getColumnIndex("Object"));
					ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
					tempList.add(new ListResultItem(ViolatioName, Utils.unAccent(ViolatioName), Fine, strMessage, ViolationID));
				mResult.moveToNext();
			}
		return tempList;
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search_all, container, false);
	
		searchView = (SearchView) rootView.findViewById(R.id.search_all_View);
		searchView.setQueryHint("Search...");
		searchView.setEnabled(false);
		
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				getAllViolationFromDatabase gf= new getAllViolationFromDatabase(getActivity());
				gf.execute(query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				
				return false;
			}
		});
		resultListView = (ListView)  rootView.findViewById(R.id.lv_list_result_search_all);
		progressSearch = (ProgressBar) rootView.findViewById(R.id.progressBarSearch);
		tvResultCount = (TextView) rootView.findViewById(R.id.tv_search_all_result_count);
		getAllViolationFromDatabase gf= new getAllViolationFromDatabase(getActivity());
		gf.execute("");
		
		
		return rootView;
	}
	
	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			 Intent intent = new Intent(getActivity(),
			 ResultDetailAct.class);
			 intent.putExtra(Variables.TAG_VIOLATIOID,
			 searchlistResultItems.get(position).getVioID());
			 startActivity(intent);
		}
	}

	// get all violation from database

	private class getAllViolationFromDatabase extends AsyncTask<String, Void, Void> {	
		private Activity context;
		
		public getAllViolationFromDatabase(Activity context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			progressSearch.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			String searchtext = params[0];
			if (listResultItems != null)
			{
			searchlistResultItems = getListItemAfterSearch(searchtext);
			}
			else {
				listResultItems = getListItemformDatabase();
				searchlistResultItems = listResultItems;
			}

			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			listResultAdapter = new ListResultAdapter(this.context, searchlistResultItems);
			tvResultCount.setText("Có " + searchlistResultItems.size() + " kết quả được tìm thấy.");
			resultListView.setOnItemClickListener(new ListResutlItemOnClickListener());
			resultListView.setAdapter(listResultAdapter);
			progressSearch.setVisibility(View.INVISIBLE);
			searchView.setEnabled(true);
			super.onPostExecute(result);
		}
		
		private ArrayList<ListResultItem> getListItemAfterSearch(String searchtext) {
			ArrayList<ListResultItem> tempList = new ArrayList<ListResultItem>();
			String unAccentsearchtext = (String) Utils.unAccent(searchtext.toLowerCase());
			int i = 0;
			if (listResultItems.size()!=0)
				while (i<listResultItems.size()) {
					if (listResultItems.get(i).getVioNameEn().toString().toLowerCase().contains(unAccentsearchtext))
					{
						listResultItems.get(i).setVioName(Utils.highlight(unAccentsearchtext,listResultItems.get(i).getVioName(),listResultItems.get(i).getVioNameEn()));
						tempList.add(listResultItems.get(i));
					}
					i++;
				}
			return tempList;
		}	
	}
}
