package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.ithong.models.DatabaseHelper;
import com.example.ithong.models.ListActionItem;
import com.example.ithonge.R;
import com.example.ithonge.adapter.ListActionAdapter;
import com.example.ithonge.utils.Utils;
import com.example.ithonge.utils.Variables;

public class ListActionFragment extends Fragment implements OnTouchListener {
	private ListView mListAct;
	private ListActionAdapter mListActAdapter;
	private ArrayList<ListActionItem> mListActItems;
	private int[] mListActIcons;
	private String[] mListActTitles;
	public Context context;
	private DatabaseHelper mDatabaseHelper;
	private int vehiclePosition;

	public ListActionFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_list_action, container, false);
		getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Bundle bundle = this.getArguments();
		vehiclePosition = bundle.getInt(Variables.TAG_VEHICLE_POSITION);
		getActivity().getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActivity().getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActivity().setTitle(getResources().getStringArray(R.array.list_vehicles)[vehiclePosition]);
		// init local variable
		try {
			mDatabaseHelper = new DatabaseHelper(getActivity());
		} catch (IOException e) {
			e.printStackTrace();
		}
		mListAct = (ListView) rootView.findViewById(R.id.lv_list_action);
		mListActIcons = new int[] { R.drawable.hv1, R.drawable.hv2, R.drawable.hv3, R.drawable.hv4, R.drawable.hv5, R.drawable.hv6,
				R.drawable.hv7, R.drawable.hv8 };
		mListActTitles = getResources().getStringArray(R.array.list_action_item);
		mListAct.setOnItemClickListener(new ListActOnItemClickListener());
		mListActItems = new ArrayList<ListActionItem>();
		// get list Action from Database and create listview
		mListActItems = getListActionFromDB(vehiclePosition + 1);
		mListActAdapter = new ListActionAdapter(getActivity(), mListActItems);
		mListAct.setAdapter(mListActAdapter);

		// dungna 03/13/2015
		if (Variables.tapCount % 10 == 0) {
			Utils.showFullAds(getActivity());
		}
		rootView.setOnTouchListener(this);
		return rootView;
	}

	private ArrayList<ListActionItem> getListActionFromDB(int vehicleID) {
		ArrayList<ListActionItem> list = new ArrayList<ListActionItem>();
		String sql = "Select * From Group_Type Where TransportID = " + vehicleID;
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
		mResult.moveToFirst();

		do {
			int groupID = mResult.getInt(mResult.getColumnIndex("GroupID"));
			Log.e("ListActionAct", "groupID: " + groupID);
			if (groupID != 0) {
				ListActionItem item = new ListActionItem(mListActIcons[groupID - 1], mListActTitles[groupID - 1], (groupID - 1));
				list.add(item);
			}
			mResult.moveToNext();
		} while (!mResult.isAfterLast());

		return list;
	}

	private class ListActOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListResultFragment fragment2 = new ListResultFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(Variables.TAG_VEHICLE_POSITION, vehiclePosition);
			bundle.putInt(Variables.TAG_OPTION_POSITION, position);
			fragment2.setArguments(bundle);
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment2);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}

	// dungna 03/15/2015
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Variables.tapCount++;
			Log.e("dungna: Count", "" + Variables.tapCount);
			break;
		}
		// dungna 03/13/2015
		if (Variables.tapCount % 10 == 0) {
			Utils.showFullAds(getActivity());
		}
		return true;
	}
}
