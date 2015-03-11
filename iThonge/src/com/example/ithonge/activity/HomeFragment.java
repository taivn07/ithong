package com.example.ithonge.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		mGridView = (GridView) rootView.findViewById(R.id.grid_transport);

		mThumn = new int[] { R.drawable.btn_oto, R.drawable.btn_moto,
				R.drawable.btn_maykeo, R.drawable.btn_xedap,
				R.drawable.btn_nguoidibo, R.drawable.btn_hanhlangduongbo,
				R.drawable.btn_viphamkhac, R.drawable.btn_qtxp };
		mGridView.setAdapter(new CustomGridViewAdapter(getActivity(), mThumn));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				startListItemAct(position);
			}
		});
		return rootView;
	}

	private void startListItemAct(int position) {
		if (position == 7) {
			QTXPFragment fragment2 = new QTXPFragment();
		    FragmentManager fragmentManager = getFragmentManager();
		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    fragmentTransaction.replace(R.id.frame_container, fragment2);
		    fragmentTransaction.addToBackStack(null);
		    fragmentTransaction.commit();
		} else if (position == 6) {
			ListResultFragment fragment2 = new ListResultFragment();
			Bundle bundle =new Bundle(); 
			bundle.putInt(Variables.TAG_VEHICLE_POSITION, position); 
			bundle.putInt(Variables.TAG_OPTION_POSITION, 0); 
			fragment2.setArguments(bundle);
		    FragmentManager fragmentManager = getFragmentManager();
		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    fragmentTransaction.replace(R.id.frame_container, fragment2);
		    fragmentTransaction.addToBackStack(null);
		    fragmentTransaction.commit();
		} else {
			ListActionFragment fragment2 = new ListActionFragment();
			Bundle bundle =new Bundle(); 
			bundle.putInt(Variables.TAG_VEHICLE_POSITION, position); 
			fragment2.setArguments(bundle);
		    FragmentManager fragmentManager = getFragmentManager();
		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    fragmentTransaction.replace(R.id.frame_container, fragment2);
		    fragmentTransaction.addToBackStack(null);
		    fragmentTransaction.commit();
		}

	}

}
