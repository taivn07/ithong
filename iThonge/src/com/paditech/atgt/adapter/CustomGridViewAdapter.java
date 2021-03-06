package com.paditech.atgt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CustomGridViewAdapter extends BaseAdapter {
	private Context context;
	private int[] listThumn;

	public CustomGridViewAdapter(Context context, int[] image) {
		this.context = context;
		this.listThumn = image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(context);
			// imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(listThumn[position]);
		return imageView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listThumn.length;
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
}
