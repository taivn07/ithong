package com.example.ithonge.activity;

import java.util.ArrayList;

import com.example.ithonge.R;
import com.example.ithonge.adapter.ListActionAdapter;
import com.example.models.ListActionItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

public class ShowInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showinfo);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}
}
