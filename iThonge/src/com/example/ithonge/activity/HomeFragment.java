package com.example.ithonge.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ithonge.R;
import com.example.ithonge.adapter.CustomGridViewAdapter;

public class HomeFragment extends Fragment {
	private GridView mGridView;
	private int[] mThumn;

	public HomeFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		mGridView = (GridView) rootView.findViewById(R.id.grid_transport);
		mThumn = new int[] { R.drawable.btn_oto, R.drawable.btn_xedap, R.drawable.btn_moto, R.drawable.btn_oto, R.drawable.btn_xedap,
				R.drawable.btn_moto };
		mGridView.setAdapter(new CustomGridViewAdapter(getActivity(), mThumn));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
			}
		});
		return rootView;
	}
}