package com.example.ithonge.activity;

import com.example.ithonge.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OtoInfoActivity extends Activity {

	MyAdapter mMyAdapter;
	ListView listView;
	String[] mTitle;
	int[] mThumb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
//	    listView = (ListView) findViewById(R.layout.custom_row);
//	mTitle = new String[] {getResources().getString(R.string.hethongbaohieu),
//				getResources().getString(R.string.nguoithamgiagiaothong),
//				getResources().getString(R.string.luuthong),
//				getResources().getString(R.string.Giayto),
//				getResources().getString(R.string.Phuongtien),
//				getResources().getString(R.string.Tocdo),
//				getResources().getString(R.string.Khac),
//								};
//		mThumb = new int[] {R.drawable.hv1,R.drawable.hv2,R.drawable.hv3, R.drawable.hv4,
//				R.drawable.hv5,R.drawable.hv6, R.drawable.hv8};
//		
//		mMyAdapter = new MyAdapter(this, mTitle, mThumb);
//		listView.setAdapter(mMyAdapter);
//		listView.setOnItemClickListener(this);
	
	}
	
    }