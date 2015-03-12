package com.example.ithonge.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ithong.models.DatabaseHelper;
import com.example.ithong.models.ListKeyWordItem;
import com.example.ithong.models.ListResultItem;
import com.example.ithong.models.TableViolation;
import com.example.ithonge.R;
import com.example.ithonge.adapter.ListResultAdapter;
import com.example.ithonge.utils.Utils;

public class SearchOnlineFragment extends Fragment {

	// ListView Result
	// SO ~ Search Online
	private ListView mListSOResult;
	private ListResultAdapter mListSOAdapter;
	private ArrayList<ListResultItem> mListSOResultItemsForSave;
	private ArrayList<ListResultItem> mListSOResultItems;
	// Database/LogTag
	public static final String LOG_TAG = "SearchOnlineFragment";
	private DatabaseHelper mDatabaseHelper;
	private int optionPosition;
	private int vehiclePosition;
	private TextView tvSOResultCount;
	// search box
	private EditText edSearchQueryInput;

	public SearchOnlineFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search_online, container, false);
		// create view
		mListSOResult = (ListView) rootView.findViewById(R.id.lv_search_online_list_result);
		tvSOResultCount = (TextView) rootView.findViewById(R.id.tv_search_online_result_count);
		tvSOResultCount.setText("");
		mListSOResultItems = new ArrayList<ListResultItem>();
		mListSOResultItemsForSave = new ArrayList<ListResultItem>();
		edSearchQueryInput = (EditText) rootView.findViewById(R.id.ed_search_online_query);
		// edSearchQueryInput.setOnEditorActionListener(new
		// OnEditorActionListener() {
		//
		// public boolean onEditorAction(TextView v, int actionId, KeyEvent
		// event) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		// });

		edSearchQueryInput.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						String constraint = edSearchQueryInput.getText().toString();
						mListSOResultItems.clear();
						mListSOResultItems.addAll(getListSOResultItemsByTextInput(constraint));
						mListSOAdapter.notifyDataSetChanged();
						tvSOResultCount.setText("Tìm thấy " + mListSOResultItems.size() + " kết quả.");

					} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
						String constraint = edSearchQueryInput.getText().toString();
						mListSOResultItems.clear();
						mListSOResultItems.addAll(getListSOResultItemsByTextInput(constraint));
						mListSOAdapter.notifyDataSetChanged();
						tvSOResultCount.setText("Tìm thấy " + mListSOResultItems.size() + " kết quả.");
					}
					return true;
				}
				return false;
			}
		});
		try {
			mDatabaseHelper = new DatabaseHelper(getActivity());
		} catch (IOException e) {
			e.printStackTrace();
		}
		mListSOResult.setOnItemClickListener(new ListResutlItemOnClickListener());
		// start thread and get data from server. input: url
		if (Utils.checkNetworkConnection(getActivity())) {
			new getAllViolationFromServerTask().execute("test");
		} else {
			AlertDialog alert = Utils.showNetworkConnectDialog(getActivity(), "Lỗi Kết Nối",
					"Không thể kết nối mạng Internet. Xin vui lòng kiểm tra và thử lại");
			alert.show();
			startHomeFragment();
		}
		return rootView;
	}

	// search listview and response list new
	private ArrayList<ListResultItem> getListSOResultItemsByTextInput(String constraint) {
		ArrayList<ListResultItem> tempList = new ArrayList<ListResultItem>();
		if (constraint != null && constraint.length() > 0) {
			// search content in friend list
			for (ListResultItem item : mListSOResultItemsForSave) {
				if (item.getVioName().toLowerCase().contains(constraint.toString().toLowerCase())
						|| item.getVioNameEn().toLowerCase().contains(constraint.toString().toLowerCase())) {
					tempList.add(item);
				}
			}
		} else {
			Log.e(LOG_TAG, "please enter text on field.");
		}
		return tempList;
	}

	// when click list result item
	private class ListResutlItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent(getActivity(),
			// ListResultDetailAct.class);
			// intent.putExtra(Variables.TAG_VIOLATIOID,
			// mListSOResultItems.get(position).getVioID());
			// // intent.putExtra(Variables.TAG_OBJECT,
			// // mListSOResultItems.get(position).get);
			// intent.putExtra(Variables.TAG_VIOLATION_NAME,
			// mListSOResultItems.get(position).getVioName());
			// intent.putExtra(Variables.TAG_VIOLATION_NAME,
			// mListSOResultItems.get(position).getVioName());
			// intent.putExtra(Variables.TAG_VIOLATION_NAME,
			// mListSOResultItems.get(position).getVioName());
			// startActivity(intent);
		}
	}

	// start home Fragment from another fragment
	private void startHomeFragment() {
		Fragment homeFragment = new HomeFragment();
		// consider using Java coding conventions (upper first char class
		// names!!!)
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.frame_container, homeFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	// get all violation from database in server
	// input search_online = true
	private class getAllViolationFromServerTask extends AsyncTask<String, String, String> {
		private AlertDialog mAlertDialog;
		private ArrayList<TableViolation> listTableViolations;
		private ArrayList<ListResultItem> listResultItems;
		private ProgressDialog proDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			listTableViolations = new ArrayList<TableViolation>();
			listResultItems = new ArrayList<ListResultItem>();
			mAlertDialog = createAlertDialog(getActivity(), "", "");
			proDialog = new ProgressDialog(getActivity());
			proDialog.setCancelable(false);
			proDialog.setMessage("Đang tải dữ liệu, xin đợi trong giây lát.");
			proDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			String url = "http://anhdungbkc.esy.es/ithongtest.php?search_online=true";
			// Log.e(LOG_TAG, url);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response;
			String result = null;
			try {
				response = client.execute(httpGet);
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
				Log.e(LOG_TAG, result);
				if (Utils.CheckResultFromServer(result)) {
					listTableViolations = Utils.getListViolationFromJsonResponse(result);
					// update local database: Keyword table
					// mDatabaseHelper.insertIntoTableKeyword(listKeyUpdate);
					for (TableViolation item : listTableViolations) {
						listResultItems.add(new ListResultItem(item.getName(), item.getNameEN(), item.getFines(), item.getObject(), item
								.getViolation_ID()));
					}
					// Update layout with list Violation from Server
					Log.e("nguyenanhdung", "" + listResultItems.size());
					// publishProgress(Integer.toString(listResultItems.size()));
					return null;
				}
			} catch (Exception e) {
				Log.e(LOG_TAG, e.getMessage());
			}
			publishProgress(null);
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			if (values == null) {
				mAlertDialog.setTitle("Lỗi mạng.");
				mAlertDialog.setMessage("Không thể lấy dữ liệu từ server. Vui lòng kiểm tra kết nối hoặc thử lại sau.");
				mAlertDialog.show();
			} else {
				// mListSOResultItems.addAll(listResultItems);
				// Log.e(LOG_TAG, "count: " + mListSOResultItems.size());
				// mListSOAdapter = new ListResultAdapter(getActivity(),
				// mListSOResultItems);
				// mListSOResult.setAdapter(mListSOAdapter);
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mListSOResultItems.addAll(listResultItems);
			Log.e(LOG_TAG, "count: " + mListSOResultItems.size());
			mListSOAdapter = new ListResultAdapter(getActivity(), mListSOResultItems);
			mListSOResult.setAdapter(mListSOAdapter);
			mListSOResultItemsForSave.addAll(mListSOResultItems);
			tvSOResultCount.setText("Tìm thấy " + mListSOResultItems.size() + " kết quả.");
			proDialog.dismiss();
		}

		private AlertDialog createAlertDialog(Activity activity, String title, String message) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle(title);
			builder.setCancelable(false);
			// builder.setMessage(message);
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					startHomeFragment();
					dialog.dismiss();
				}
			});
			return builder.create();
		}
	}
}
