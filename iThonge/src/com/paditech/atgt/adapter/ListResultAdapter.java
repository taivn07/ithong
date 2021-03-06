package com.paditech.atgt.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paditech.atgt.R;
import com.paditech.atgt.models.ListResultItem;
import com.paditech.atgt.utils.Utils;

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
		TextView btt = (TextView) convertView.findViewById(R.id.tv_list_result_icon);
		btt.setText("" + (position + 1));
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_list_result_title);
		tvTitle.setText(mListResultItems.get(position).getVioName());
		TextView tvFine = (TextView) convertView.findViewById(R.id.tv_list_result_fine);
		tvFine.setText(mListResultItems.get(position).getfine());
		TextView tvMessage = (TextView) convertView.findViewById(R.id.tv_list_result_message);
		if (mListResultItems.get(position).getMessage() != null 
				&& Utils.ltrim(mListResultItems.get(position).getMessage()) != "" 
				&& !Utils.ltrim(mListResultItems.get(position).getMessage()).equals("null")) {
			tvMessage.setText(context.getResources().getString(R.string.tv_apdung) + " "+ mListResultItems.get(position).getMessage());
		} else {
			tvMessage.setText(context.getResources().getString(R.string.tv_apdungnull));
		}

		return convertView;
	}

}
