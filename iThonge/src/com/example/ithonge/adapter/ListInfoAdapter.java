package com.example.ithonge.adapter;

import java.util.ArrayList;

import com.example.ithonge.R;
import com.example.models.ListInfoItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListInfoAdapter extends BaseAdapter {
	private ArrayList<ListInfoItem> listInfoItem;
	private LayoutInflater inflater;
	private Context context;

	public ListInfoAdapter(Context context, ArrayList<ListInfoItem> listInfo) {
		this.context = context;
		this.listInfoItem = listInfo;
		this.inflater = null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listInfoItem.size();
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
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_info_item, null);
		}
		
		TextView tvpos = (TextView) convertView.findViewById(R.id.lv_list_info_pos);
		tvpos.setText(""+(1+position));
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.lv_list_info_icon);
		imgIcon.setImageResource(listInfoItem.get(position).getIcon());
		TextView tvTitle = (TextView) convertView.findViewById(R.id.lv_list_info_title);
		tvTitle.setText(listInfoItem.get(position).getTitle());

		return convertView;
	}
}
