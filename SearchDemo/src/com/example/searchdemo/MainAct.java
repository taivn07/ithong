package com.example.searchdemo;

import java.util.ArrayList;

import jp.co.imobile.sdkads.android.ImobileSdkAd;
import jp.co.imobile.sdkads.android.ImobileSdkAdListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

@SuppressLint("NewApi")
public class MainAct extends Activity implements SearchView.OnQueryTextListener {
	private SearchView searchView;
	private MenuItem searchMenuItem;
	private FriendListAdapter friendListAdapter;
	private ArrayList<User> friendList;
	private ListView mFriendListView;

	// インタースティシャルスポット
	static final String INTERSTITIAL_PUBLISHER_ID = "34816";
	static final String INTERSTITIAL_MEDIA_ID = "135179";
	static final String INTERSTITIAL_SPOT_ID = "342418";
	// ウォールスポット
	static final String WALL_PUBLISHER_ID = "34816";
	static final String WALL_MEDIA_ID = "135179";
	static final String WALL_SPOT_ID = "342419";
	// スマートフォンバナースポット
	// static final String BANNER_PUBLISHER_ID = "34816";
	// static final String BANNER_MEDIA_ID = "135179";
	// static final String BANNER_SPOT_ID = "342414";
	// スマートフォンビッグバナースポット
	static final String BIG_BANNER_PUBLISHER_ID = "34816";
	static final String BIG_BANNER_MEDIA_ID = "135179";
	static final String BIG_BANNER_SPOT_ID = "342415";
	// ミディアムレクタングルスポット
	static final String RECTANGLE_BANNER_PUBLISHER_ID = "34816";
	static final String RECTANGLE_BANNER_MEDIA_ID = "135179";
	static final String RECTANGLE_BANNER_SPOT_ID = "342416";
	// アイコンスポット
	static final String ICON_PUBLISHER_ID = "34816";
	static final String ICON_MEDIA_ID = "135179";
	static final String ICON_SPOT_ID = "342417";
	// テキストポップアップスポット
	static final String TEXT_POPUP_PUBLISHER_ID = "34816";
	static final String TEXT_POPUP_MEDIA_ID = "135179";
	static final String TEXT_POPUP_SPOT_ID = "342420";

	// For mobile Ad test
	static final String BANNER_PUBLISHER_ID = "35067";
	static final String BANNER_MEDIA_ID = "144083";
	static final String BANNER_SPOT_ID = "370041";

	// For mobile Ad test
	static final String FULL_PUBLISHER_ID = "35067";
	static final String FULL_MEDIA_ID = "144083";
	static final String FULL_SPOT_ID = "370043";

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

		mFriendListView = (ListView) findViewById(R.id.lv_list_search);
		friendListAdapter = new FriendListAdapter(this, friendList);
		mFriendListView.setAdapter(friendListAdapter);
		mFriendListView.setTextFilterEnabled(true);

		// 1.広告スポットの登録
		ImobileSdkAd.registerSpotFullScreen(MainAct.this, FULL_PUBLISHER_ID, FULL_MEDIA_ID, FULL_SPOT_ID);
		// 2.広告取得開始
		ImobileSdkAd.start(FULL_SPOT_ID);
		Button btnShowAd = (Button) findViewById(R.id.btnShowAd);
		btnShowAd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 3.広告表示
				ImobileSdkAd.showAd(MainAct.this, FULL_SPOT_ID);
			}
		});

	}

	// 戻るボタン（Backキー）押下で広告を表示

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		ImobileSdkAd.showAdForce(this, FULL_SPOT_ID, new ImobileSdkAdListener() {
			// 4.広告が閉じられた場合（閉じるボタン、もしくはクリックボタンがタップされた場合）
			@Override
			public void onAdCloseCompleted() {
				// 5.広告が閉じられた場合、Activityを終了させます
				MainAct.this.finish();
			}
		});
		//super.onBackPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 3. 広告表示
			ImobileSdkAd.showAdForce(this, FULL_SPOT_ID, new ImobileSdkAdListener() {
				// 4.広告が閉じられた場合（閉じるボタン、もしくはクリックボタンがタップされた場合）
				@Override
				public void onAdCloseCompleted() {
					// 5.広告が閉じられた場合、Activityを終了させます
					MainAct.this.finish();
				}
			});
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		ImobileSdkAd.activityDestory();
		super.onDestroy();
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
	protected void onPause() {
		// 広告の取得を停止
		ImobileSdkAd.stopAll();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 広告の取得を再開
		ImobileSdkAd.startAll();
		super.onResume();
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
		// friendListAdapter.getFilter().filter(newText);
		return false;
	}
}
