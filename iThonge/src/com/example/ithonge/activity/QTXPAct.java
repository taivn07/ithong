package com.example.ithonge.activity;

import java.io.File;

import com.example.ithonge.R;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Chronometer;

public class QTXPAct extends FragmentActivity{
	WebView mWebview;
@Override
protected void onCreate(Bundle arg0) {
	super.onCreate(arg0);
	setContentView(R.layout.activity_webview);
	mWebview = (WebView) findViewById(R.id.webview_view);
	getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.actionbar_bg));
	getActionBar().setTitle("Quy trình xử phạt");
	WebViewClient viewClient = new WebViewClient();
	WebChromeClient chromeClient = new WebChromeClient();
	mWebview.setWebViewClient(viewClient);
	mWebview.setWebChromeClient(chromeClient);
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
