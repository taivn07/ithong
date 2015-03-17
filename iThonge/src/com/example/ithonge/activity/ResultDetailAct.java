package com.example.ithonge.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ithong.models.DatabaseHelper;
import com.example.ithonge.R;
import com.example.ithonge.utils.Utils;
import com.example.ithonge.utils.Variables;

public class ResultDetailAct extends Activity {
	private DatabaseHelper mDataBaseHelper;
	private long VioID;
	private ScrollView mScrollView;
	private TextView ObjText;
	private TextView MessText;
	private TextView FineText;

	private LinearLayout tableresultaddLayout;
	private boolean mainbookmarked, bookmarked;

	// Facebook connect
	// private Facebook mFacebook;
	// AsyncFacebookRunner mAsyncRunner;
	// private GestureDetector gestureDetector;
	// View.OnTouchListener gestureListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_result_detail);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setTitle(R.string.tv_chitiet);

		tableresultaddLayout = (LinearLayout) findViewById(R.id.table_resultadd);
		ObjText = (TextView) findViewById(R.id.show_object);
		MessText = (TextView) findViewById(R.id.show_mess);
		FineText = (TextView) findViewById(R.id.show_fine);

		VioID = getIntent().getExtras().getLong(Variables.TAG_VIOLATIOID);

		try {
			mDataBaseHelper = new DatabaseHelper(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Log.e("VioID", "" + VioID);
		SetView(VioID);

	}

	// Tao title de show cac muc va cac muc
	private TextView MakeTitle(String title) {
		TextView TitleMake;
		TitleMake = new TextView(this);
		TitleMake.setText(title);
		TitleMake.setTypeface(null, Typeface.BOLD);
		TitleMake.setTextColor(getResources().getColor(R.color.white));
		TitleMake.setBackground(getResources().getDrawable(R.color.blue));
		TitleMake.setGravity(Gravity.CENTER);
		TitleMake.setTextSize(18);
		return TitleMake;
	}

	private TextView MakeShowText(String mess, int size, boolean isBold, int textColorID, int backgroundColorID, int paddingTop) {
		TextView TitleMake;
		TitleMake = new TextView(this);
		TitleMake.setText(mess);
		if (isBold)
			TitleMake.setTypeface(null, Typeface.BOLD);
		TitleMake.setTextColor(getResources().getColor(textColorID));
		TitleMake.setBackground(getResources().getDrawable(backgroundColorID));
		TitleMake.setTextSize(size);
		TitleMake.setPadding(15, paddingTop, 0, 5);
		return TitleMake;
	}

	private void MakeButtonViewMore(long VioID, int Type) {
		Button ViewmoreBtt;
		ViewmoreBtt = new Button(this);
		ViewmoreBtt.setId(Type);
		ViewmoreBtt.setText(getResources().getString(R.string.tv_xemvanban));
		ViewmoreBtt.setTextColor(getResources().getColor(R.color.white));
		ViewmoreBtt.setTextSize(14);
		ViewmoreBtt.setBackground(getResources().getDrawable(R.color.green));
		ViewmoreBtt.setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(30, 0, 0, 10);
		ViewmoreBtt.setLayoutParams(params);
		String Bookmarkname = "";
		String sql = "Select * From Bookmark_Detail Where Violation_ID = " + VioID + " and Type_ID =" + Type;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);
		mResult.moveToFirst();
		if (mResult.getCount() != 0) {
			Bookmarkname = mResult.getString(mResult.getColumnIndex("Bookmark_Code"));
		}

		if (Bookmarkname != "") {
			ViewmoreBtt.setOnClickListener(new OnclickZ(Bookmarkname));
			tableresultaddLayout.addView(ViewmoreBtt);
		}
	}

	// --------------------------------

	// Tao rieng click cho tung button-------------
	private class OnclickZ implements OnClickListener {
		private String bookmarkname;

		public OnclickZ(String bookmarkname) {
			// TODO Auto-generated constructor stub
			this.bookmarkname = bookmarkname;
		}

		@Override
		public void onClick(View v) {
			// check to show ads
			if (Variables.CLICK_TO_SHOW_ADS_COUNT == Variables.CLICK_TO_SHOW_ADS) {
				Utils.showFullAds(ResultDetailAct.this);
				Variables.CLICK_TO_SHOW_ADS_COUNT = 0;
			} else {
				Variables.CLICK_TO_SHOW_ADS_COUNT++;
			}
			Intent intent = new Intent(v.getContext(), MoreInfo_act.class);
			intent.putExtra(Variables.TAG_BOOKMARKNAME, bookmarkname);
			startActivity(intent);

		}

	}

	// ------------------------------------------

	private void SetView(long VioID) {

		String sql = "Select * From Violation Where ID = " + VioID;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);
		mResult.moveToFirst();
		Log.e("SQl", "" + sql);
		Log.e("cc", "" + mResult.getCount());

		if (mResult.getCount() != 0) {
			String tempObj = mResult.getString(mResult.getColumnIndex("Object"));
			if (tempObj != null && !tempObj.equals("null")) {
				ObjText.setText(mResult.getString(mResult.getColumnIndex("Object")));
			} else {
				ObjText.setText(getResources().getString(R.string.tv_apdungnull));
			}
			MessText.setText(mResult.getString(mResult.getColumnIndex("Name")));
			FineText.setText(mResult.getString(mResult.getColumnIndex("Fines")));
			MakeButtonViewMore(VioID, 1);

			String temp = mResult.getString(mResult.getColumnIndex("Additional_Penalties"));
			if (temp != null && Utils.ltrim(temp) != "" && temp != "null") {
				tableresultaddLayout.addView(MakeTitle(getResources().getString(R.string.tv_hpbs)));
				tableresultaddLayout.addView(MakeShowText(temp, 16, false, R.color.black, R.color.white, 10));
				MakeButtonViewMore(VioID, 2);
			}
			temp = mResult.getString(mResult.getColumnIndex("Other_Penalties"));
			if (temp != null && Utils.ltrim(temp) != "" && temp != "null") {
				tableresultaddLayout.addView(MakeTitle(getResources().getString(R.string.tv_hpbskhac)));
				tableresultaddLayout.addView(MakeShowText(temp, 16, false, R.color.black, R.color.white, 10));
				MakeButtonViewMore(VioID, 4);
			}
			temp = mResult.getString(mResult.getColumnIndex("Remedial_Measures"));
			if (temp != null && Utils.ltrim(temp) != "" && temp != "null") {
				tableresultaddLayout.addView(MakeTitle(getResources().getString(R.string.tv_hpbt)));
				tableresultaddLayout.addView(MakeShowText(temp, 16, false, R.color.black, R.color.white, 10));
				MakeButtonViewMore(VioID, 3);
			}
		}

		mScrollView = (ScrollView) findViewById(R.id.show_result);
		mScrollView.fullScroll(ScrollView.FOCUS_UP);
		mScrollView.smoothScrollTo(0, 0);

		MakeListViewTask mk = new MakeListViewTask(this, VioID);
		mk.execute();

	}

	private boolean Checkbookmarked() {
		String sql = "Select * From LuuTru Where Violation_ID = " + VioID;
		Cursor mResult = mDataBaseHelper.getResultFromSQL(sql);

		if (mResult.getCount() != 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_act_bookmark, menu);
		MenuItem item = menu.getItem(0);
		if (Checkbookmarked()) {
			item.setIcon(R.drawable.ico_bookmarked);
			mainbookmarked = true;
		} else {
			item.setIcon(R.drawable.ico_bookmark);
			mainbookmarked = false;
		}
		bookmarked = mainbookmarked;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		if (bookmarked != mainbookmarked) {
			if (mainbookmarked)
				mDataBaseHelper.deleteFromTableLuuTru(VioID);
			if (!mainbookmarked)
				mDataBaseHelper.insertintoTableLuuTru(VioID);
		}
		Log.e("backpress", "" + Variables.CLICK_BACK_PRESS_TO_SHOW_ADS_COUNT);
		if (Variables.CLICK_BACK_PRESS_TO_SHOW_ADS_COUNT == Variables.CLICK_BACK_PRESS_TO_SHOW_ADS) {
			Utils.showFullAds(this);
			Variables.CLICK_BACK_PRESS_TO_SHOW_ADS_COUNT = 0;
		} else {
			Variables.CLICK_BACK_PRESS_TO_SHOW_ADS_COUNT++;
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_bookmark:
			bookmarked = !bookmarked;
			if (!bookmarked) {
				item.setIcon(R.drawable.ico_bookmark);
			} else {
				item.setIcon(R.drawable.ico_bookmarked);
			}
			return true;
			// dungna: 16/03/2015
		case R.id.action_fb_share:
			share_facebook();
			return true;

		case R.id.action_twitter_share:
			share_twitter();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void share_facebook() {
		Bitmap bitmapToShare = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

		File pictureStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File noMedia = new File(pictureStorage, ".nomedia");
		if (!noMedia.exists())
			noMedia.mkdirs();
		SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
		String fileName = s.format(new Date(0));
		// String fileName = tsTemp.toString();
		File file = new File(noMedia, fileName + ".jpg");
		View view = findViewById(R.id.show_result);// your layout id
		view.getRootView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		Bitmap bitmap = view.getDrawingCache();
		saveBitmapAsFile(bitmap, file);

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.setType("image/jpeg");
		List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
		if (!resInfos.isEmpty()) {
			for (ResolveInfo resInfo : resInfos) {
				String packageName = resInfo.activityInfo.packageName;
				if (packageName.contains("com.facebook.katana")) {

					final ActivityInfo activity = resInfo.activityInfo;
					final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
					shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
					shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					shareIntent.setComponent(name);
					// Log.e("Dungna", "after dhsdhfsdkf");
					startActivity(shareIntent);
					break;
				}
			}
		}

	}

	public void share_twitter() {
		Bitmap bitmapToShare = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

		File pictureStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File noMedia = new File(pictureStorage, ".nomedia");
		if (!noMedia.exists())
			noMedia.mkdirs();
		SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
		String fileName = s.format(new Date(0));
		// String fileName = tsTemp.toString();
		File file = new File(noMedia, fileName + ".jpg");
		View view = findViewById(R.id.show_result);// your layout id
		view.getRootView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		Bitmap bitmap = view.getDrawingCache();
		saveBitmapAsFile(bitmap, file);

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.setType("image/jpeg");
		List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
		if (!resInfos.isEmpty()) {
			for (ResolveInfo resInfo : resInfos) {
				String packageName = resInfo.activityInfo.packageName;
				if (packageName.contains("com.twitter.android")) {

					final ActivityInfo activity = resInfo.activityInfo;
					final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
					shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
					shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					shareIntent.setComponent(name);
					// Log.e("Dungna", "after dhsdhfsdkf");
					startActivity(shareIntent);
					break;
				}
			}
		}

	}

	public void saveBitmapAsFile(Bitmap bitmap, File file) {
		try {
			file.createNewFile();
			FileOutputStream picOut = new FileOutputStream(file);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), (int) (bitmap.getHeight() / 1.2));
			boolean saved = bitmap.compress(CompressFormat.JPEG, 100, picOut);
			if (saved) {
				// Toast.makeText(getApplicationContext(),
				// "Image saved to your device Pictures " + "directory!",
				// Toast.LENGTH_SHORT).show();
			} else {
				Log.e("ResultDetail", "Images not to save!");
			}
			picOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
