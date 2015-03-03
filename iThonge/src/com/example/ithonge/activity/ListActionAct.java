package com.example.ithonge.activity;

import java.util.ArrayList;

import com.example.ithonge.R;
import com.example.ithonge.adapter.ListActionAdapter;
import com.example.models.ListActionItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListActionAct extends Activity {
	private ListView mListAct;
	private ListActionAdapter mListActAdapter;
	private ArrayList<ListActionItem> mListActItems;
	private int[] mListActIcons;
	private String[] mListActTitles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_action);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mListAct = (ListView) findViewById(R.id.list);
		mListAct.setOnItemClickListener(new ListActOnItemClickListener());
		mListActIcons = new int[] { R.drawable.hv1, R.drawable.hv2, R.drawable.hv3, R.drawable.hv4, R.drawable.hv5, R.drawable.hv6,
				R.drawable.hv7, R.drawable.hv8};
		mListActTitles = getResources().getStringArray(R.array.list_action_item);
		mListActItems = new ArrayList<ListActionItem>();
		for (int i = 0; i < mListActTitles.length; i++) {
			mListActItems.add(new ListActionItem(mListActIcons[i], mListActTitles[i]));
		}
		mListActAdapter = new ListActionAdapter(this, mListActItems);
		mListAct.setAdapter(mListActAdapter);

	}

	private class ListActOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(view.getContext(), ShowInfoActivity.class);
			startActivityForResult(intent, 0);

		}

	}
}
