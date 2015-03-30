package com.paditech.atgt.activity;

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
import android.widget.RelativeLayout;

import com.paditech.atgt.R;

public class HelpFragment extends Fragment {
	WebView mWebview;
	RelativeLayout relativeLayout;

	public HelpFragment() {
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
		mWebview.getSettings().setBuiltInZoomControls(false);
		mWebview.getSettings().setUseWideViewPort(true);
		mWebview.setPadding(0, 0, 0, 0);
		mWebview.setInitialScale(getScale());
		mWebview.loadUrl("file:///android_asset/help.html");
		relativeLayout.addView(mWebview);
		return rootView;
	}

	private int getScale() {
		Display display = ((WindowManager) getActivity().getSystemService(
				getActivity().WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		Double val = new Double(width) / new Double(651);
		val = val * 100d;
		return val.intValue();
	}
}
