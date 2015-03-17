//package com.example.ithonge.adapter;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import com.example.ithonge.R;
//import com.example.models.ListKeyWordItem;
//
//public class ListSearchAdapter extends BaseAdapter implements Filterable {
//	private Context context;
//	private ArrayList<ListKeyWordItem> listKeyWords;
//	private ArrayList<ListKeyWordItem> listKeyFilters;
//	private SearchItemFilter searchItemFilter;
//
//	public ListSearchAdapter(Context context, ArrayList<ListKeyWordItem> listKeyWords) {
//		this.context = context;
//		this.listKeyWords = listKeyWords;
//		this.listKeyFilters = listKeyWords;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return listKeyFilters.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = inflater.inflate(R.layout.list_search_item, parent, false);
//			TextView tvSearchItem = (TextView) convertView.findViewById(R.id.tv_list_search_item);
//			tvSearchItem.setText(listKeyFilters.get(position).getNameEN());
//		}
//		return convertView;
//	}
//
//	private class SearchItemFilter extends Filter {
//
//		@Override
//		protected FilterResults performFiltering(CharSequence constraint) {
//			// FilterResults filterResults = new FilterResults();
//			// if (constraint != null && constraint.length() > 0) {
//			// ArrayList<ListKeyWordItem> listTemp = new
//			// ArrayList<ListKeyWordItem>();
//			// for (ListKeyWordItem key : listKeyWords) {
//			// if
//			// (key.getNameEN().toLowerCase().contains(constraint.toString().toLowerCase()))
//			// {
//			// listTemp.add(key);
//			// }
//			// }
//			// filterResults.count = listTemp.size();
//			// filterResults.values = listTemp;
//			// } else {
//			// filterResults.count = listKeyWords.size();
//			// filterResults.values = listKeyWords;
//			// }
//			// return filterResults;
//			FilterResults filterResults = new FilterResults();
//			if (constraint != null && constraint.length() > 0) {
//				ArrayList<ListKeyWordItem> tempList = new ArrayList<ListKeyWordItem>();
//
//				// search content in friend list
//				for (ListKeyWordItem ListKeyWordItem : listKeyWords) {
//					if (ListKeyWordItem.getNameEN().toLowerCase().contains(constraint.toString().toLowerCase())) {
//						tempList.add(ListKeyWordItem);
//					}
//				}
//
//				filterResults.count = tempList.size();
//				filterResults.values = tempList;
//			} else {
//				filterResults.count = listKeyWords.size();
//				filterResults.values = listKeyWords;
//			}
//
//			return filterResults;
//		}
//
//		@Override
//		protected void publishResults(CharSequence constraint, FilterResults results) {
//			listKeyFilters = (ArrayList<ListKeyWordItem>) results.values;
//			notifyDataSetChanged();
//		}
//
//	}
//
//	@Override
//	public Filter getFilter() {
//		if (searchItemFilter == null) {
//			searchItemFilter = new SearchItemFilter();
//		}
//		return searchItemFilter;
//	}
//
//}

package com.paditech.atgt.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.paditech.atgt.R;
import com.paditech.atgt.models.ListKeyWordItem;

/**
 * Display friend list
 * 
 * @author eranga herath(erangaeb@gmail.com)
 */
public class ListSearchAdapter extends BaseAdapter implements Filterable {

	private Context context;
	private FriendFilter friendFilter;
	private Typeface typeface;
	private ArrayList<ListKeyWordItem> friendList;
	private ArrayList<ListKeyWordItem> filteredList;

	/**
	 * Initialize context variables
	 * 
	 * @param context
	 *            friend list context
	 * @param friendList
	 *            friend list
	 */
	public ListSearchAdapter(Context context,
			ArrayList<ListKeyWordItem> friendList) {
		this.context = context;
		this.friendList = friendList;
		this.filteredList = friendList;
		// typeface = Typeface.createFromAsset(context.getAssets(),
		// "fonts/vegur_2.otf");

		getFilter();
	}

	/**
	 * Get size of ListKeyWordItem list
	 * 
	 * @return ListKeyWordItemList size
	 */
	@Override
	public int getCount() {
		return filteredList.size();
	}

	/**
	 * Get specific item from ListKeyWordItem list
	 * 
	 * @param i
	 *            item index
	 * @return list item
	 */
	@Override
	public Object getItem(int i) {
		return filteredList.get(i);
	}

	/**
	 * Get ListKeyWordItem list item id
	 * 
	 * @param i
	 *            item index
	 * @return current item id
	 */
	@Override
	public long getItemId(int i) {
		return i;
	}

	/**
	 * Create list row view
	 * 
	 * @param position
	 *            index
	 * @param view
	 *            current list item view
	 * @param parent
	 *            parent
	 * @return view
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unnecessary
		// calls
		// to findViewById() on each row.
		final ViewHolder holder;
		final ListKeyWordItem ListKeyWordItem = (ListKeyWordItem) getItem(position);

		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.list_search_item, parent,
					false);
			holder = new ViewHolder();
			// holder.iconText = (TextView) view.findViewById(R.id.icon_text);
			holder.name = (TextView) view
					.findViewById(R.id.tv_list_search_item);
			// holder.iconText.setTypeface(typeface, Typeface.BOLD);
			// holder.iconText.setTextColor(context.getResources().getColor(R.color.white));
			// holder.name.setTypeface(typeface, Typeface.NORMAL);

			view.setTag(holder);
		} else {
			// get view holder back
			holder = (ViewHolder) view.getTag();
		}

		// bind text with view holder content view for efficient use
		// holder.iconText.setText("#");
		holder.name.setText(filteredList.get(position).getName());
		// view.setBackgroundResource(R.drawable.friend_list_selector);

		return view;
	}

	/**
	 * Get custom filter
	 * 
	 * @return filter
	 */
	@Override
	public Filter getFilter() {
		if (friendFilter == null) {
			friendFilter = new FriendFilter();
		}

		return friendFilter;
	}

	/**
	 * Keep reference to children view to avoid unnecessary calls
	 */
	static class ViewHolder {
		TextView iconText;
		TextView name;
	}

	/**
	 * Custom filter for friend list Filter content in friend list according to
	 * the search text
	 */
	private class FriendFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<ListKeyWordItem> tempList = new ArrayList<ListKeyWordItem>();

				// search content in friend list
				for (ListKeyWordItem item : friendList) {
					if (item.getNameEN().toLowerCase()
							.contains(constraint.toString().toLowerCase())) {
						tempList.add(item);
					}
				}

				filterResults.count = tempList.size();
				filterResults.values = tempList;
			} else {
				filterResults.count = friendList.size();
				filterResults.values = friendList;
			}

			return filterResults;
		}

		/**
		 * Notify about filtered list to ui
		 * 
		 * @param constraint
		 *            text
		 * @param results
		 *            filtered result
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredList = (ArrayList<ListKeyWordItem>) results.values;
			notifyDataSetChanged();
		}
	}

}
