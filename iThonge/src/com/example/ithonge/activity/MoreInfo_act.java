package com.example.ithonge.activity;

import com.example.ithonge.R;
import com.example.ithonge.utils.Variables;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MoreInfo_act extends Activity {
	WebView mWebview;
	String bookmarkname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		bookmarkname = getIntent().getExtras().getString(Variables.TAG_BOOKMARKNAME);
		mWebview = (WebView) findViewById(R.id.webview_view);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		getActionBar().setTitle("Nghị định 171");

		WebViewClient viewClient = new WebViewClient();
		WebChromeClient chromeClient = new WebChromeClient();
		mWebview.setWebViewClient(viewClient);
		mWebview.setWebChromeClient(chromeClient);
		mWebview.loadUrl("file:///android_asset/NghiDinh171.htm#"+bookmarkname);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
}
