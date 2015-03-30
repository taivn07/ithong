package com.paditech.atgt.activity;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.paditech.atgt.R;
import com.paditech.atgt.adapter.ListResultAdapter;
import com.paditech.atgt.models.DatabaseHelper;
import com.paditech.atgt.models.ListResultItem;
import com.paditech.atgt.utils.Variables;

public class BookmarkFragment extends Fragment {

	private ListView mListResult;
	private ListResultAdapter mListResultAdapter;
	private DatabaseHelper mDatabaseHelper;
	private ArrayList<ListResultItem> mListResultItems;

	public BookmarkFragment(DatabaseHelper databaseHelper) {
		this.mDatabaseHelper = databaseHelper;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

		mListResultItems = new ArrayList<ListResultItem>();

		// create listview
		mListResultItems = getListBookmarkFromDB();

		mListResult = (ListView) rootView.findViewById(R.id.lv_list_bookmark_result);
		mListResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		mListResultAdapter = new ListResultAdapter(getActivity(), mListResultItems);
		mListResult.setAdapter(mListResultAdapter);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		mListResultItems = getListBookmarkFromDB();
		mListResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		mListResultAdapter = new ListResultAdapter(getActivity(), mListResultItems);
		mListResult.setAdapter(mListResultAdapter);
	};

	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), ResultDetailAct.class);
			intent.putExtra(Variables.TAG_VIOLATIOID, mListResultItems.get(position).getVioID());
			getActivity().startActivity(intent);
		}
	}

	private ArrayList<ListResultItem> getListBookmarkFromDB() {
		ArrayList<ListResultItem> list = new ArrayList<ListResultItem>();
		String sql = "Select Violation.* From Violation,LuuTru Where LuuTru.Violation_ID = Violation.ID";

		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);

		mResult.moveToFirst();
		String Violationame = null;
		String ViolationNameEn = null;
		String Fine = null;
		String strMessage = null;
		long ViolationID = 0;

		if (mResult.getCount() != 0)
			while (!mResult.isAfterLast()) {
				Violationame = mResult.getString(mResult.getColumnIndex("Name"));
				ViolationNameEn = mResult.getString(mResult.getColumnIndex("NameEN"));
				Fine = mResult.getString(mResult.getColumnIndex("Fines"));
				strMessage = mResult.getString(mResult.getColumnIndex("Object"));
				ViolationID = mResult.getInt(mResult.getColumnIndex("ID"));
				list.add(new ListResultItem(Violationame, ViolationNameEn, Fine, strMessage, ViolationID));
				mResult.moveToNext();
			}
		return list;
	}
}
