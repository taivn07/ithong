package com.example.ithonge.adapter;

import java.util.ArrayList;

import com.example.ithonge.R;
import com.example.models.ListActionItem;
import com.example.models.ListResultItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListResultAdapter extends BaseAdapter {
	private ArrayList<ListResultItem> mListResultItems;
	private Context context;
	private LayoutInflater inflater;

	public ListResultAdapter(Context context, ArrayList<ListResultItem> listItems) {
		this.context = context;
		this.mListResultItems = listItems;
		inflater = null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListResultItems.size();
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
			convertView = inflater.inflate(R.layout.list_result_item, null);
		}
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgv_list_result_icon);
		imgIcon.setImageResource(mListResultItems.get(position).getIcon());
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_list_result_title);
		tvTitle.setText(mListResultItems.get(position).getTitle());
		TextView tvMessage = (TextView) convertView.findViewById(R.id.tv_list_result_message);
		tvMessage.setText(mListResultItems.get(position).getMessage());

		return convertView;
	}

}
