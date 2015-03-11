package com.example.ithonge.activity;

import com.example.ithonge.R;

import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QTXPFragment extends Fragment {
	WebView mWebview;

	public QTXPFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_webview, container,
				false);
		mWebview = (WebView) rootView.findViewById(R.id.webview_view);
		getActivity().getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.actionbar_bg));
		getActivity().getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		getActivity().getActionBar().setTitle("Quy trình xử phạt");

		WebViewClient viewClient = new WebViewClient();
		WebChromeClient chromeClient = new WebChromeClient();
		mWebview.setWebViewClient(viewClient);
		mWebview.setWebChromeClient(chromeClient);
		mWebview.loadUrl("file:///android_asset/qtxp.htm");
		return rootView;
	}
	
//	@Override
//	protected void onCreateView(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.activity_webview);
//		mWebview = (WebView) findViewById(R.id.webview_view);
//		getActionBar().setBackgroundDrawable(
//				getResources().getDrawable(R.color.actionbar_bg));
//		getActionBar().setIcon(
//				new ColorDrawable(getResources().getColor(
//						android.R.color.transparent)));
//		getActionBar().setTitle("Quy trình xử phạt");
//
//		WebViewClient viewClient = new WebViewClient();
//		WebChromeClient chromeClient = new WebChromeClient();
//		mWebview.setWebViewClient(viewClient);
//		mWebview.setWebChromeClient(chromeClient);
//		mWebview.loadUrl("file:///android_asset/qtxp.htm");
//
//	}

	

}
