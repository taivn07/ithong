package com.example.ithonge.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ithonge.R;

public class QTXPAct extends FragmentActivity {
	WebView mWebview;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_webview);
		mWebview = (WebView) findViewById(R.id.webview_view);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.actionbar_bg));
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		getActionBar().setTitle("Quy trình xử phạt");

		WebViewClient viewClient = new WebViewClient();
		WebChromeClient chromeClient = new WebChromeClient();
		mWebview.setWebViewClient(viewClient);
		mWebview.setWebChromeClient(chromeClient);
		mWebview.setInitialScale(1);
		mWebview.getSettings().setLoadWithOverviewMode(true);
		mWebview.getSettings().setUseWideViewPort(true);
		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.loadUrl("file:///android_asset/qtxp.htm");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
