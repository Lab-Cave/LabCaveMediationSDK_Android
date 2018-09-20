package com.labcave.labcavemediation.android.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.labcave.mediationlayer.LabCaveMediation;
import com.labcave.mediationlayer.LabCaveMediationBannerView;
import com.labcave.mediationlayer.MediationType;
import com.labcave.mediationlayer.delegates.LabCaveMediationListener;
import com.labcave.mediationlayer.mediationadapters.models.Info;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String APP_ID = "3475efb80cf7af9cffbecf706d93fad37ffdaeee";

  private LabCaveMediationBannerView bannerView;
  private final Handler UIHandler = new Handler(Looper.getMainLooper());

  private Button showBanner;
  private Button showInterstitial;
  private Button showRewarded;
  private Button showVideo;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bannerView = findViewById(R.id.banner);

    Button showTest = findViewById(R.id.showTest);
    showBanner = findViewById(R.id.showBanner);
    showInterstitial = findViewById(R.id.showInterstitial);
    showRewarded = findViewById(R.id.showRewarded);
    showVideo = findViewById(R.id.showVideo);

    showTest.setOnClickListener(this);
    showBanner.setOnClickListener(this);
    showInterstitial.setOnClickListener(this);
    showRewarded.setOnClickListener(this);
    showVideo.setOnClickListener(this);

    initLabcaveMediationLayer();
  }

  private void initLabcaveMediationLayer() {
    LabCaveMediation.INSTANCE.setAutoFetch(true);
    LabCaveMediation.INSTANCE.setLogging(true);
    LabCaveMediation.INSTANCE.addListener(new LabCaveMediationListener() {
      @Override public void onInit(boolean state) {

      }

      @Override public void onMediationTypeLoad(final MediationType type) {
        UIHandler.post(new Runnable() {
          @Override public void run() {
            switch (type) {
              case BANNER:
                showBanner.setVisibility(View.VISIBLE);
                break;

              case INTERSTITIAL:
                showInterstitial.setVisibility(View.VISIBLE);
                break;

              case REWARDED_VIDEO:
                showRewarded.setVisibility(View.VISIBLE);
                break;

              case VIDEO:
                showVideo.setVisibility(View.VISIBLE);
                break;
            }
          }
        });
      }

      @Override public void onClose(MediationType type, String name, String extra) {

      }

      @Override public void onClick(MediationType type, String name, String extra) {

      }

      @Override public void onError(String description, MediationType type, String extra) {

      }

      @Override public void onShow(MediationType type, String name, String extra, Info info) {

      }

      @Override public void onReward(@NonNull MediationType type, @NonNull String name, @NonNull String extra) {

      }
    });

    LabCaveMediation.INSTANCE.init(this, APP_ID);
  }

  @Override protected void onPause() {
    super.onPause();
    LabCaveMediation.INSTANCE.pause();
  }

  @Override protected void onResume() {
    super.onResume();
    LabCaveMediation.INSTANCE.resume();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.showTest:
        LabCaveMediation.INSTANCE.initTest(this, APP_ID);
        break;

      case R.id.showBanner:
        LabCaveMediation.INSTANCE.showBanner(bannerView, "banner");
        break;

      case R.id.showInterstitial:
        LabCaveMediation.INSTANCE.showInterstitial(MainActivity.this, "inter");
        break;

      case R.id.showRewarded:
        LabCaveMediation.INSTANCE.showRewardedVideo(MainActivity.this, "rewarded");
        break;

      case R.id.showVideo:
        LabCaveMediation.INSTANCE.showVideo(MainActivity.this, "video");
        break;
    }
  }
}