package com.example.searchdemo;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

@SuppressLint("NewApi")
public class MainAct extends Activity implements SearchView.OnQueryTextListener {
	private SearchView searchView;
	private MenuItem searchMenuItem;
	private FriendListAdapter friendListAdapter;
	private ArrayList<User> friendList;
	private ListView mFriendListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		friendList = new ArrayList<User>();
		friendList.add(new User("nguyen anh dung"));
		friendList.add(new User("hatnamika"));
		friendList.add(new User("superstramp"));
		friendList.add(new User("volcano"));
		friendList.add(new User("holden"));
		friendList.add(new User("phoebe"));
		friendList.add(new User("jean"));
		friendList.add(new User("Mr. Mika"));
		
		mFriendListView = (ListView)findViewById(R.id.lv_list_search);
		friendListAdapter = new FriendListAdapter(this, friendList);
		mFriendListView.setAdapter(friendListAdapter);
		mFriendListView.setTextFilterEnabled(true);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchMenuItem = menu.findItem(R.id.search);
		searchView = (SearchView) searchMenuItem.getActionView();

		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);

		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		friendListAdapter.getFilter().filter(newText);
		 //friendListAdapter.getFilter().filter(newText);
		return false;
	}
}
