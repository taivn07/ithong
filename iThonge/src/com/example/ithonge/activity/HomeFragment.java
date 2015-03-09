package com.example.ithonge.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ithonge.R;
import com.example.ithonge.adapter.CustomGridViewAdapter;
import com.example.ithonge.utils.Variables;

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

		mThumn = new int[] { R.drawable.btn_oto, R.drawable.btn_moto, R.drawable.btn_maykeo, R.drawable.btn_xedap,
				R.drawable.btn_nguoidibo, R.drawable.btn_hanhlangduongbo, R.drawable.btn_viphamkhac, R.drawable.btn_qtxp };
		mGridView.setAdapter(new CustomGridViewAdapter(getActivity(), mThumn));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				startListItemAct(position);
			}
		});
		return rootView;
	}

	private void startListItemAct(int position) {
		if (position == 7) {
			Intent intent = new Intent(getActivity(), QTXPAct.class);
			getActivity().startActivity(intent);
		} else 
		if (position ==6)
		{
			Intent intent = new Intent(getActivity(), ListResultAct.class);
			intent.putExtra(Variables.TAG_OPTION_POSITION, 6);
			intent.putExtra(Variables.TAG_VEHICLE_POSITION, position);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(getActivity(), ListActionAct.class);
			intent.putExtra(Variables.TAG_VEHICLE_POSITION, position);
			getActivity().startActivity(intent);
		}
		
	}
	
}
