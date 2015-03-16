package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ithong.models.DatabaseHelper;
import com.example.ithong.models.NavDrawerItem;
import com.example.ithonge.R;
import com.example.ithonge.adapter.NavDrawerListAdapter;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerListAdapter mDAdapter;
	private ArrayList<NavDrawerItem> listNavDrawerItems;
	private DatabaseHelper mDatabaseHelper;

	private CharSequence mTitle;
	private String[] mDrawerTitles;
	// private TypedArray mDrawerIcons;
	private int[] mDrawerIcons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			mDatabaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mTitle = getTitle();
		mDrawerTitles = getResources().getStringArray(R.array.drawer_titles);
		mDrawerIcons = new int[] { R.drawable.ic_action_main, R.drawable.ico_synchronize, R.drawable.ico_search, R.drawable.ico_tutorial,
				R.drawable.ico_about, R.drawable.ico_bookmark, R.drawable.ic_action_exit };
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_sliding_menu);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
		// set up the drawer's list view with items and click listener
		listNavDrawerItems = new ArrayList<NavDrawerItem>();
		for (int i = 0; i < mDrawerTitles.length; i++) {
			listNavDrawerItems.add(new NavDrawerItem(mDrawerTitles[i], mDrawerIcons[i]));
		}
		mDAdapter = new NavDrawerListAdapter(this, listNavDrawerItems);
		mDrawerList.setAdapter(mDAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ico_menu, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(R.string.choose);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case 1:
			// create intent to perform web search for this planet
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
			// catch event that there's no activity to handle intent
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment = new HomeFragment();

			break;
		case 1:
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment = new SyncFragment();

			break;
		case 2:
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			// fragment = new SearchOnlineFragment();

			fragment = new SearchAllFragment();
			getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

			break;
		case 3:
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment = new HelpFragment();

			break;
		case 4:
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment = new AboutFragment();

			break;
		case 5:
			// getFragmentManager().popBackStack(null,
			// FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment = new BookmarkFragment(mDatabaseHelper);

			break;
		case 6:
			finish();

			break;

		default:
			break;
		}

		if (fragment != null) {
			// comment 16/03/2015 by dungna
			// FragmentManager fragmentManager = getFragmentManager();
			// FragmentTransaction fragmentTransaction =
			// fragmentManager.beginTransaction();
			// fragmentTransaction.replace(R.id.frame_container, fragment);
			// fragmentTransaction.commit();

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();

			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(mDrawerTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}
	}

	@Override
	public void onBackPressed() {
		// comment 16/03/2015 by dungna
		// if (getFragmentManager().getBackStackEntryCount() == 0) {
		// } else {
		// if (getFragmentManager().getBackStackEntryCount() == 1)
		// setTitle(mDrawerTitles[0]);
		// super.onBackPressed();
		// }

		if (getFragmentManager().getBackStackEntryCount() == 1) {
			// Do nothing
		} else {
			if (getFragmentManager().getBackStackEntryCount() == 2)
				setTitle(mDrawerTitles[0]);
			super.onBackPressed();
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// if(event.getAction() == MotionEvent.ACTION_DOWN){
	// Variables.tapCount++;
	// Log.e("dungna: Count", ""+Variables.tapCount);
	// }
	// return super.onTouchEvent(event);
	// }
}
