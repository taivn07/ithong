package imobile.sample;

import jp.co.imobile.sdkads.android.ImobileIconParams;
import jp.co.imobile.sdkads.android.ImobileSdkAd;
import jp.co.imobile.sdkads.android.ImobileSdkAdListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;

public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);

		// インタースティシャル広告の取得
		if ( !(("".equals(SpotParams.INTERSTITIAL_PUBLISHER_ID)) || ("".equals(SpotParams.INTERSTITIAL_MEDIA_ID)) ||  ("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotFullScreen(this, SpotParams.INTERSTITIAL_PUBLISHER_ID, SpotParams.INTERSTITIAL_MEDIA_ID, SpotParams.INTERSTITIAL_SPOT_ID);
			ImobileSdkAd.start(SpotParams.INTERSTITIAL_SPOT_ID);
		}

		// バナー広告の取得と表示
		if ( !(("".equals(SpotParams.BANNER_PUBLISHER_ID)) || ("".equals(SpotParams.BANNER_MEDIA_ID)) ||  ("".equals(SpotParams.BANNER_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotInline(this, SpotParams.BANNER_PUBLISHER_ID, SpotParams.BANNER_MEDIA_ID, SpotParams.BANNER_SPOT_ID);
			ImobileSdkAd.start(SpotParams.BANNER_SPOT_ID);
			ImobileSdkAd.showAd(this, SpotParams.BANNER_SPOT_ID, 0, 20, true);
		}

		// ビッグバナーの取得と表示
		if ( !(("".equals(SpotParams.BIG_BANNER_PUBLISHER_ID)) || ("".equals(SpotParams.BIG_BANNER_MEDIA_ID)) ||  ("".equals(SpotParams.BIG_BANNER_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotInline(this, SpotParams.BIG_BANNER_PUBLISHER_ID, SpotParams.BIG_BANNER_MEDIA_ID, SpotParams.BIG_BANNER_SPOT_ID);
			ImobileSdkAd.start(SpotParams.BIG_BANNER_SPOT_ID);
			ViewGroup adBottomLayout = (ViewGroup)findViewById(R.id.adBigBannerLayout);
			ImobileSdkAd.showAd(this, SpotParams.BIG_BANNER_SPOT_ID, adBottomLayout);
		}

		// アイコンの取得と表示
		if ( !(("".equals(SpotParams.ICON_PUBLISHER_ID)) || ("".equals(SpotParams.ICON_MEDIA_ID)) ||  ("".equals(SpotParams.ICON_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotInline(this, SpotParams.ICON_PUBLISHER_ID, SpotParams.ICON_MEDIA_ID, SpotParams.ICON_SPOT_ID);
			ImobileSdkAd.start(SpotParams.ICON_SPOT_ID);
			ImobileIconParams iconParam = new ImobileIconParams();
			iconParam.setIconNumber(3);
			ViewGroup adIconLayout = (ViewGroup)findViewById(R.id.adIconLayout);
			ImobileSdkAd.showAd(this, SpotParams.ICON_SPOT_ID, adIconLayout, iconParam);
		}
	}

	// バックキー押下で広告を表示
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ( !("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) {
				ImobileSdkAd.showAdForce(this, SpotParams.INTERSTITIAL_SPOT_ID, new ImobileSdkAdListener() {
		        	// 広告が閉じられた場合
					@Override
		        	public void onAdCloseCompleted() {
		        		DetailActivity.this.finish();
		        	}
		        });
			} else {
        		DetailActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// Activity廃棄時の後処理
		ImobileSdkAd.activityDestory();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		//広告の取得を停止
		ImobileSdkAd.stopAll();
		super.onPause();
	}

	@Override
	protected void onResume() {
		//広告の取得を再開
		ImobileSdkAd.startAll();
		super.onResume();
	}
}
