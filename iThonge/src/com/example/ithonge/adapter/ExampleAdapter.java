package com.example.ithonge.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ithonge.R;

public class ExampleAdapter extends CursorAdapter {

	private String[] items;

	private TextView text;

	public ExampleAdapter(Context context, Cursor cursor, String[] items) {

		super(context, cursor, false);

		this.items = items;

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		text.setText(items[cursor.getPosition()]);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.list_search_item, parent, false);

		text = (TextView) view.findViewById(R.id.tv_list_search_item);

		return view;

	}

}