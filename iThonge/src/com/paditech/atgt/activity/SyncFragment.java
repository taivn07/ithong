package com.paditech.atgt.activity;

import java.io.IOException;
import java.sql.SQLClientInfoException;
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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paditech.atgt.R;
import com.paditech.atgt.models.DatabaseHelper;
import com.paditech.atgt.models.TableKeyword;
import com.paditech.atgt.models.TableViolation;
import com.paditech.atgt.utils.Utils;
import com.paditech.atgt.utils.Variables;

public class SyncFragment extends Fragment {
	private ProgressBar mPgbarGetData;
	private TextView mTvGetData;
	private DatabaseHelper mDatabaseHelper;
	public static final String LOG_TAG = "SyncFragment";

	public SyncFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sync, container, false);
		mPgbarGetData = (ProgressBar) rootView.findViewById(R.id.progbar_get_data);
		mTvGetData = (TextView) rootView.findViewById(R.id.tv_get_data);
		try {
			mDatabaseHelper = new DatabaseHelper(getActivity());
		} catch (IOException e) {
			Log.e(LOG_TAG, e.getMessage().toString());
		}
		if (Utils.checkNetworkConnection(getActivity())) {
			new synchronizeDataFromServerTask().execute("test");
		} else {
			AlertDialog alert = Utils.showNetworkConnectDialog(getActivity(), "Lỗi Kết Nối",
					"Không thể kết nối mạng Internet. Xin vui lòng kiểm tra và thử lại");
			alert.show();
			startHomeFragment();
		}

		return rootView;
	}

	private void startHomeFragment() {
		getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

	private int getKeywordTableNumRow() {
		// String sql = "Select * From Keywords";
		String sql2 = "Update Keywords SET LastUpdated = '20150311115829' WHERE Keyword_ID = 1719";
		Cursor mResult = mDatabaseHelper.getResultFromSQL(sql2);
		int count = mResult.getCount();
		return count;
	}

	// get LastUpdate from local database and synchronize with server (using
	// LastUpdate)
	private class synchronizeDataFromServerTask extends AsyncTask<String, String, String> {
		private AlertDialog mAlertDialog;

		public synchronizeDataFromServerTask() {
			mPgbarGetData.setVisibility(View.VISIBLE);
			mTvGetData.setVisibility(View.VISIBLE);
			mAlertDialog = createAlertDialog(getActivity(), "Đồng bộ hoàn tất.", "Đồng bộ hoàn tất. Nhấn Cancel để thoát.");
		}

		@Override
		protected String doInBackground(String... params) {
			String lastUpdated = getLastUpdateFromViolationTable();
			if (lastUpdated != null) {
				// check lastUpdate and send request to server, get response
				// (json)
				HttpClient client = new DefaultHttpClient();
				String url = Variables.SERVER_HOST + "sync=true&last_update=" + lastUpdated;
				// Log.e(LOG_TAG, url);
				// Log.e(LOG_TAG, url);
				HttpGet httpGet = new HttpGet(url);
				HttpResponse response;
				String result = null;
				try {
					response = client.execute(httpGet);
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					// Log.e(LOG_TAG, result);
					if (Utils.CheckResultFromServer(result)) {
						ArrayList<TableKeyword> listKeyUpdate = new ArrayList<TableKeyword>();
						listKeyUpdate = Utils.getListKeywordFromJsonResponse(result);
						ArrayList<TableViolation> listViolationUpdate = new ArrayList<TableViolation>();
						listViolationUpdate = Utils.getListViolationFromJsonResponse(result);
						// update local database: Keyword table, Violation table
						if (listKeyUpdate.size() > 0) {
							mDatabaseHelper.insertIntoTableKeyword(listKeyUpdate);
						}
						if (listViolationUpdate.size() > 0) {
							mDatabaseHelper.insertIntoTableViolation(listViolationUpdate);
						}
						publishProgress(Integer.toString(listKeyUpdate.size() + listViolationUpdate.size()));
						return null;
					}
				} catch (Exception e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}
			publishProgress(null);
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			mPgbarGetData.setVisibility(View.INVISIBLE);
			mTvGetData.setVisibility(View.INVISIBLE);
			if (values == null) {
				mAlertDialog.setMessage("Không có dữ liệu để cập nhật. Nhấn Cancel để thoát.");
			} else {
				mAlertDialog.setMessage("Đã cập nhật " + values[0] + " trường vào cơ sở dữ liệu. Nhấn Cancel để thoát.");
			}
			mAlertDialog.show();
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
		}

		// get Lastupdate from local database: Keyword table
		private String getLastUpdateFromKeywordTable() {
			String lastUpdated = null;
			String sql = "SELECT  max(LastUpdated), Keyword_ID, Keyword_Name from Keywords";
			Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
			if (mResult != null && mResult.getCount() != 0) {
				Log.e(LOG_TAG, "" + mResult.getCount());
				mResult.moveToFirst();
				lastUpdated = mResult.getString(0);
			}
			return lastUpdated;
		}

		private String getLastUpdateFromViolationTable() {
			String lastUpdated = null;
			String sql = "SELECT  max(LastUpdated), ID from Violation";
			Cursor mResult = mDatabaseHelper.getResultFromSQL(sql);
			if (mResult != null && mResult.getCount() != 0) {
				Log.e(LOG_TAG, "" + mResult.getCount());
				mResult.moveToFirst();
				lastUpdated = mResult.getString(0);
			}
			return lastUpdated;
		}

		// create dialog then show when asynctask finished.
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
