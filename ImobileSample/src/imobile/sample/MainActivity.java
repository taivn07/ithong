package imobile.sample;

import jp.co.imobile.sdkads.android.FailNotificationReason;
import jp.co.imobile.sdkads.android.ImobileSdkAd;
import jp.co.imobile.sdkads.android.ImobileSdkAdListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 広告の取得
		// テキストポップアップ広告
		if ( !(("".equals(SpotParams.TEXT_POPUP_PUBLISHER_ID)) || ("".equals(SpotParams.TEXT_POPUP_MEDIA_ID)) ||  ("".equals(SpotParams.TEXT_POPUP_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotFullScreen(this, SpotParams.TEXT_POPUP_PUBLISHER_ID, SpotParams.TEXT_POPUP_MEDIA_ID, SpotParams.TEXT_POPUP_SPOT_ID);
			ImobileSdkAd.start(SpotParams.TEXT_POPUP_SPOT_ID);
			ImobileSdkAd.showAd(MainActivity.this, SpotParams.TEXT_POPUP_SPOT_ID);
		}
		// インタースティシャル広告
		if ( !(("".equals(SpotParams.INTERSTITIAL_PUBLISHER_ID)) || ("".equals(SpotParams.INTERSTITIAL_MEDIA_ID)) ||  ("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotFullScreen(this, SpotParams.INTERSTITIAL_PUBLISHER_ID, SpotParams.INTERSTITIAL_MEDIA_ID, SpotParams.INTERSTITIAL_SPOT_ID);
		}
		// ウォール広告
		if ( !(("".equals(SpotParams.WALL_PUBLISHER_ID)) || ("".equals(SpotParams.WALL_MEDIA_ID)) ||  ("".equals(SpotParams.WALL_SPOT_ID))) ) {
			ImobileSdkAd.registerSpotFullScreen(this, SpotParams.WALL_PUBLISHER_ID, SpotParams.WALL_MEDIA_ID, SpotParams.WALL_SPOT_ID);
		}

		// リスナーの設定
		// インタースティシャル広告(ウォール広告でも実装方法は変わりませんが、ウォール広告では先読みをしていないため、onAdReadyCompletedは呼び出されません)
		final TextView txtStatus = (TextView)findViewById(R.id.txtStatus);
		final TextView txtMessage = (TextView)findViewById(R.id.txtMessage);
		if (!("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) {
			ImobileSdkAd.setImobileSdkAdListener(SpotParams.INTERSTITIAL_SPOT_ID, new ImobileSdkAdListener() {
	        	@Override
	        	public void onAdReadyCompleted() {
	        		txtStatus.setText("広告準備完了");
					Log.v("App 正常 コールバック","広告の準備ができました");
	        	}
	        	@Override
	        	public void onAdShowCompleted() {
	        		txtMessage.setText("広告表示完了");
	        		if(!ImobileSdkAd.isShowAd(SpotParams.INTERSTITIAL_SPOT_ID)) {
	            		txtStatus.setText("広告準備中");
	        		}
	        		Log.v("App 正常 コールバック","広告が表示されました");
	        	}
	        	@Override
	        	public void onAdCliclkCompleted() {
	        		txtMessage.setText("広告クリック");
					Log.v("App 正常 コールバック","広告がクリックされました");
	        	}
	        	@Override
	        	public void onAdCloseCompleted() {
	        		txtMessage.setText("広告クローズ");
					Log.v("App 正常 コールバック","広告が閉じられました");
	        	}
	        	@Override
	        	public void onFailed(FailNotificationReason reason) {
	        		txtStatus.setText("広告取得失敗");
	        		txtMessage.setText("" + reason);
					Log.v("App エラー コールバック",reason.toString());
	        	}
	        });
		}

		// 広告の取得開始
		// インタースティシャル広告
		if (!("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) {
			ImobileSdkAd.start(SpotParams.INTERSTITIAL_SPOT_ID);
		}
		txtStatus.setText("広告取得中");
		// ウォール広告
		if (!("".equals(SpotParams.WALL_SPOT_ID))) {
			ImobileSdkAd.start(SpotParams.WALL_SPOT_ID);
		}

		// 広告の表示
		// インタースティシャル広告の表示
        Button btnShowAd = (Button)findViewById(R.id.btnShowAd);
        btnShowAd.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (!("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) {
					ImobileSdkAd.showAd(MainActivity.this, SpotParams.INTERSTITIAL_SPOT_ID);
				}
			}
        });

		// 次の画面へ
        Button btnNext = (Button)findViewById(R.id.btnNext);
		if (!("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) {
	        btnNext.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					// インタースティシャル広告の表示
					if (!("".equals(SpotParams.INTERSTITIAL_SPOT_ID))) {
						ImobileSdkAd.showAd(MainActivity.this, SpotParams.INTERSTITIAL_SPOT_ID, new ImobileSdkAdListener() {
				        	//広告が閉じられた場合
							@Override
				        	public void onAdCloseCompleted() {
								Intent intent = new Intent(MainActivity.this, DetailActivity.class);
								startActivity(intent);
				        	}
				        });
					}
				}
	        });
		} else {
	        btnNext.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, DetailActivity.class);
					startActivity(intent);
				}
	        });
		}

		// バナー表示画面へ
        Button btnBanner = (Button)findViewById(R.id.btnBanner);
        btnBanner.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, DetailActivity2.class);
					startActivity(intent);
			}
        });

        // ウォール広告の表示
        Button btnWall = (Button)findViewById(R.id.btnWall);
        btnWall.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (!("".equals(SpotParams.WALL_SPOT_ID))) {
					ImobileSdkAd.showAd(MainActivity.this, SpotParams.WALL_SPOT_ID);
				}
			}
        });

	}

	@Override
	protected void onDestroy() {
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
