package com.paditech.atgt.activity;

import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.paditech.atgt.R;

public class QTXPFragment extends Fragment {
	WebView mWebview;
	RelativeLayout relativeLayout;

	public QTXPFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_webview, container,
				false);
		relativeLayout = (RelativeLayout) rootView.findViewById(R.id.web_view);
		mWebview = new WebView(getActivity());
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
		mWebview.setLayoutParams(new ViewGroup.LayoutParams(
		        ViewGroup.LayoutParams.FILL_PARENT,
		        ViewGroup.LayoutParams.FILL_PARENT));
		relativeLayout.addView(mWebview);
		return rootView;
	}
	
}
