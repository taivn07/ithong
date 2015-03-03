package com.example.ithonge.activity;

import com.example.ithonge.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	String[] Str;
	int[] Img;
	Context myContext;
	public MyAdapter(Context context, String[] str, int[] img) {
		// TODO Auto-generated constructor stub
		this.Str = str;
		this.Img = img;
		myContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Str[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = null;
		if (convertView ==null)
		{
			LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.custom_row, parent, false);
		}
		else
		{
		     row = convertView;
		}
		
		TextView titleTextView = (TextView) row.findViewById(R.id.textView1);
		ImageView titleImageView = (ImageView) row.findViewById(R.id.imageView1);
		
		titleTextView.setText(Str[position]);
		titleImageView.setImageResource(Img[position]);
		return row;
	}

}
