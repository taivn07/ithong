package com.example.ithonge.activity;

import com.example.ithonge.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutFragment extends Fragment{
	
	WebView mWebview;
	public AboutFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_webview, container, false);
		
			mWebview = (WebView) rootView.findViewById(R.id.webview_view);
			
			WebViewClient viewClient = new WebViewClient();
			WebChromeClient chromeClient = new WebChromeClient();
			mWebview.setWebViewClient(viewClient);
			mWebview.setWebChromeClient(chromeClient);
			mWebview.setInitialScale(1);
		    mWebview.getSettings().setLoadWithOverviewMode(true);
		    mWebview.getSettings().setUseWideViewPort(true);
		    mWebview.getSettings().setJavaScriptEnabled(true);
			mWebview.loadUrl("file:///android_asset/gioithieu.htm");
			return rootView;
		}
		
	

	}

