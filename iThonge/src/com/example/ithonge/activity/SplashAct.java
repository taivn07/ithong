package com.example.ithonge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.example.ithonge.R;

public class SplashAct extends Activity {
//	private ProgressBar pgbarLoadingData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// pgbarLoadingData = (ProgressBar) findViewById(R.id.pgbar_load_data);
		new CountDownTimer(2000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// pgbarLoadingData.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish() {
				// pgbarLoadingData.setVisibility(View.GONE);
				Intent intent = new Intent(SplashAct.this, MainActivity.class);
				startActivity(intent);
			}
		}.start();
	}
}
