package com.paditech.atgt.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import jp.co.imobile.sdkads.android.FailNotificationReason;
import jp.co.imobile.sdkads.android.ImobileSdkAd;
import jp.co.imobile.sdkads.android.ImobileSdkAdListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.JsonReader;
import android.util.Log;

import com.paditech.atgt.models.TableKeyword;
import com.paditech.atgt.models.TableViolation;

public class Utils {
	public static final String LOG_TAG = "Utils";

	public static String ReNameFilter(String Name) {
		String FName = Name;
		int i = FName.length();
		if (FName.charAt(i - 1) == ')') {
			while (FName.charAt(i - 1) != '(') {
				i--;
			}
			FName = FName.substring(0, i - 1);
		}
		return FName;
	}

	// check network connection - dungna
	public static boolean checkNetworkConnection(Context context) {
		boolean result = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
				result = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
					result = true;
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Network Error: " + e.getMessage().toString());
			result = false;
		}
		return result;
	}

	// show dialog when network not connect
	public static AlertDialog showNetworkConnectDialog(Activity activity, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		return builder.create();
	}

	// get object(ArrayListResultItem, ... ) from server json response
	public static ArrayList<TableKeyword> getListKeywordFromJsonResponse(String response) {
		ArrayList<TableKeyword> listKeywords = new ArrayList<TableKeyword>();
		if (response != null) {
			try {
				JSONObject keyObjectRoot = new JSONObject(response);
				// JSONArray keyArrayRoot =
				// keyObjectRoot.getJSONArray(Variables.TAG_KEYWORD_ALL);
				// JSONObject keyObject = keyArrayRoot.getJSONObject(0);
				JSONObject keyObject = keyObjectRoot.getJSONObject(Variables.TAG_KEYWORD_ALL);
				int code = keyObject.getInt(Variables.TAG_KEYWORD_CODE);
				Log.e(LOG_TAG, "Code: " + code + ".  Count: ");
				// Log.e(LOG_TAG, "Code: " + code + ".  Count: " +
				// keyArrayRoot.toString());
				if (code == 1) {
					JSONArray keyArray = keyObject.getJSONArray(Variables.TAG_KEYWORD);
					for (int i = 0; i < keyArray.length(); i++) {
						JSONObject keys = keyArray.getJSONObject(i);
						int id = keys.getInt(Variables.TAG_KEYWORD_ID);
						String name = keys.getString(Variables.TAG_KEYWORD_NAME);
						String nameEN = keys.getString(Variables.TAG_KEYWORD_NAME_EN);
						int sort = keys.getInt(Variables.TAG_KEYWORD_SORT);
						String lastUpdated = convertDatetoString(keys.getString(Variables.TAG_KEYWORD_LAST_UPDATE));
						// add to list result
						listKeywords.add(new TableKeyword(id, name, nameEN, sort, lastUpdated));
					}
					Log.e(LOG_TAG, "Code: " + code + ".  Count: " + listKeywords.size());
					return listKeywords;
				} else {
					return null;
				}
			} catch (Exception e) {
				Log.e(LOG_TAG, e.getMessage().toString());
				return null;
			}
		}
		return null;
	}

	public static ArrayList<TableViolation> getListViolationFromJsonResponse(String response) {
		ArrayList<TableViolation> listViolations = new ArrayList<TableViolation>();
		if (response != null) {
			try {

				JSONObject violationObjectRoot = new JSONObject(response);
				JSONObject violationObject = violationObjectRoot.getJSONObject(Variables.TAG_VIOLATION_ALL);
				int code = violationObject.getInt(Variables.TAG_KEYWORD_CODE);
				Log.e(LOG_TAG, "Code: " + code + ".  Count: " + listViolations.size());
				if (code == 1) {
					JSONArray violationArray = violationObject.getJSONArray(Variables.TAG_VIOLATION);
					for (int i = 0; i < violationArray.length(); i++) {
						JSONObject violations = violationArray.getJSONObject(i);

						int Violation_ID = violations.getInt(Variables.TAG_VIOLATION_ID);
						String Object = violations.getString(Variables.TAG_VIOLATION_OBJECT);
						String Name = violations.getString(Variables.TAG_VIOLATION_NAME);
						String NameEN = violations.getString(Variables.TAG_VIOLATION_NAME_EN);
						int LawID = violations.getInt(Variables.TAG_VIOLATION_LAW_ID);
						int Bookmark_ID = violations.getInt(Variables.TAG_VIOLATION_BOOKMARK_ID);
						int IsWarning = violations.getInt(Variables.TAG_VIOLATION_IS_WARNING);
						int IsPoppular = violations.getInt(Variables.TAG_VIOLATION_ISPOPPULAR);
						String MainContent = violations.getString(Variables.TAG_VIOLATION_MAIN_CONTENT);
						String Fines = violations.getString(Variables.TAG_VIOLATION_FINES);
						String Additional_Penalties = violations.getString(Variables.TAG_VIOLATION_ADDITIONAL_PENALTIES);
						String Remedial_Measures = violations.getString(Variables.TAG_VIOLATION_REMEDIAL_MEASURES);
						String Other_Penalties = violations.getString(Variables.TAG_VIOLATION_OTHER_PENALTIES);
						int Group_Value = violations.getInt(Variables.TAG_VIOLATION_GROUP_VALUE);
						int Type_Value = violations.getInt(Variables.TAG_VIOLATION_TYPE_VALUE);
						String LawTitle = violations.getString(Variables.TAG_VIOLATION_LAW_TITLE);
						int Disabled = violations.getInt(Variables.TAG_VIOLATION_DISABLED);
						String LastUpdated = convertDatetoString(violations.getString(Variables.TAG_VIOLATION_LAST_UPDATED));

						// add to list result
						TableViolation item = new TableViolation(Violation_ID, Object, Name, NameEN, LawID, Bookmark_ID, IsWarning,
								IsPoppular, MainContent, Fines, Additional_Penalties, Remedial_Measures, Other_Penalties, Group_Value,
								Type_Value, LawTitle, Disabled, LastUpdated);
						listViolations.add(item);
					}
					// Log.e(LOG_TAG, "Code: " + code + ".  Count: " +
					// listKeywords.size());
					return listViolations;
				} else {
					return null;
				}
			} catch (Exception e) {
				Log.e(LOG_TAG, e.getMessage().toString());
				return null;
			}
		}
		return null;
	}

	// check result from server is null (check code = 0 or 1)
	public static boolean CheckResultFromServer(String strInput) throws JSONException {
		boolean result = false;
		if (strInput == null) {
			result = false;
		} else {
			JSONObject keyObject = new JSONObject(strInput);
			int code = keyObject.getInt(Variables.TAG_KEYWORD_CODE);
			Log.e(LOG_TAG, "Code: " + code);
			if (code == 1) {
				result = true;
			}
		}
		return result;
	}

	// convert String to date
	public static String convertDatetoString(String strDate) {
		if (strDate != null) {
			String result = strDate;
			result = result.replace(" ", "");
			result = result.replace("-", "");
			result = result.replace(":", "");
			result = result.replace("/", "");
			result = result.replace("Z", "");
			return result;
		}
		return null;
	}

	// Bo khoang trang o 2 ben String
	public static String ltrim(String source) {
		return source.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
	}

	// Bo khoang trang ben phai String
	public static String righttrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

	// Bo khoang trang ben trai String
	public static String lefttrim(String source) {
		return source.replaceAll("^\\s+", "");
	}

	// check and show full screen ads
	public static void showFullAds(Activity activity) {
		if (!(("".equals(Variables.FULL_PUBLISHER_ID)) || ("".equals(Variables.FULL_MEDIA_ID))) || ("".equals(Variables.FULL_SPOT_ID))) {
			ImobileSdkAd.registerSpotFullScreen(activity, Variables.FULL_PUBLISHER_ID, Variables.FULL_MEDIA_ID, Variables.FULL_SPOT_ID);
			ImobileSdkAd.setImobileSdkAdListener(Variables.FULL_SPOT_ID, new ImobileSdkAdListener() {
				@Override
				public void onFailed(FailNotificationReason arg0) {
					Log.e("Utils", "" + Variables.CLICK_TO_SHOW_ADS_COUNT);
					Variables.CLICK_TO_SHOW_ADS_COUNT--;
					Log.e("Utils", "" + Variables.CLICK_TO_SHOW_ADS_COUNT);
					super.onFailed(arg0);
				}

				@Override
				public void onAdShowCompleted() {
					Variables.CLICK_TO_SHOW_ADS_COUNT = 0;
					super.onAdShowCompleted();
				}
			});
			ImobileSdkAd.start(Variables.FULL_SPOT_ID);
			ImobileSdkAd.showAd(activity, Variables.FULL_SPOT_ID);
		}
	}

	// Chuyen chu co dau sang chu khong dau
	public static CharSequence unAccent(CharSequence s) {
		CharSequence temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
	}

	// highlight search
	public static CharSequence highlight(CharSequence search, CharSequence originalText, CharSequence originalTextEn) {
		// ignore case and accents
		// the same thing should have been done for the search text
		CharSequence normalizedText = originalTextEn.toString().toLowerCase();

		int start = normalizedText.toString().indexOf(search.toString());
		Spannable highlighted = new SpannableString(originalText);
		highlighted.setSpan(new ForegroundColorSpan(Color.BLACK), 0, originalText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (start < 0) {
			// not found, nothing to to
			return originalText;
		} else {
			// highlight each appearance in the original text
			// while searching in normalized text

			while (start >= 0) {
				int spanStart = Math.min(start, originalText.length());
				int spanEnd = Math.min(start + search.length(), originalText.length());

				highlighted.setSpan(new ForegroundColorSpan(Color.RED), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				start = normalizedText.toString().indexOf(search.toString(), spanEnd);
			}

			return highlighted;
		}
	}

}
