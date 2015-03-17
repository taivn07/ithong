package com.paditech.atgt.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.paditech.atgt.R;

public class AboutFragment extends Fragment {

	WebView mWebview;
	RelativeLayout relativeLayout;

	public AboutFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_webview, container,
				false);
		relativeLayout = (RelativeLayout) rootView.findViewById(R.id.web_view);
		mWebview = new WebView(getActivity());

		WebViewClient viewClient = new WebViewClient();
		WebChromeClient chromeClient = new WebChromeClient();
		mWebview.setWebViewClient(viewClient);
		mWebview.setWebChromeClient(chromeClient);
		mWebview.setLayoutParams(new ViewGroup.LayoutParams(
		        ViewGroup.LayoutParams.FILL_PARENT,
		        ViewGroup.LayoutParams.FILL_PARENT));
		mWebview.loadUrl("file:///android_asset/gioithieu.htm");
		relativeLayout.addView(mWebview);
		return rootView;
	}

}
