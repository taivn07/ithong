package imobile.sample;

import jp.co.imobile.sdkads.android.ImobileSdkAd;
import android.app.Activity;
import android.os.Bundle;

public class DetailActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail2);

		// ミディアムレクタングルバナー広告の取得と表示
		if ( !(("".equals(SpotParams.RECTANGLE_BANNER_PUBLISHER_ID)) || ("".equals(SpotParams.RECTANGLE_BANNER_MEDIA_ID)) ||  ("".equals(SpotParams.RECTANGLE_BANNER_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotInline(this, SpotParams.RECTANGLE_BANNER_PUBLISHER_ID, SpotParams.RECTANGLE_BANNER_MEDIA_ID, SpotParams.RECTANGLE_BANNER_SPOT_ID);
			ImobileSdkAd.start(SpotParams.RECTANGLE_BANNER_SPOT_ID);
			ImobileSdkAd.showAd(this, SpotParams.RECTANGLE_BANNER_SPOT_ID, 10, 18, true);
		}
	}

	@Override
	protected void onDestroy(){
		// Activity廃棄時の後処理
		ImobileSdkAd.activityDestory();
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// 広告の取得を停止
		ImobileSdkAd.stopAll();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 広告の取得を再開
		ImobileSdkAd.startAll();
		super.onResume();
	}

}
