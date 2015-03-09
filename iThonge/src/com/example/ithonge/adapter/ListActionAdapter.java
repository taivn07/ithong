package com.example.ithonge.adapter;

import java.util.ArrayList;

import com.example.ithonge.R;
import com.example.models.ListActionItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListActionAdapter extends BaseAdapter {
	private ArrayList<ListActionItem> listActionItems;
	private LayoutInflater inflater;
	private Context context;

	public ListActionAdapter(Context context, ArrayList<ListActionItem> listAct) {
		this.context = context;
		this.listActionItems = listAct;
		this.inflater = null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listActionItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_action_item, null);
		}
		ImageView imgIcon = (ImageView) convertView
				.findViewById(R.id.lv_list_action_icon);
		imgIcon.setImageResource(listActionItems.get(position).getIcon());
		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.lv_list_action_title);
		tvTitle.setText(listActionItems.get(position).getTitle());

		return convertView;
	}
}
