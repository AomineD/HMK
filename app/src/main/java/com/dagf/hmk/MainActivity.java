package com.dagf.hmk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dagf.hmk.ads.BannerManager;
import com.dagf.hmk.ads.NativeHMKLayout;
import com.dagf.hmk.utils.AdListenerHMK;
import com.dagf.hmk.utils.AdSizeBanner;
import com.dagf.hmk.utils.NativeType;
import com.dagf.hmk.utils.RatingDialogHMK;
import com.dagf.hmk.utils.SplashAdListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingDialogHMK ratingDialogHMK = new RatingDialogHMK(this, "appmarket://details?id=com.zhiliaoapp.musically", "wineberryof@gmail.com");

        ratingDialogHMK.frecuency_rating = 2;

        ratingDialogHMK.showRating();

  /*
  findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          in();
      }
  });

        NativeHMKLayout lay1 = findViewById(R.id.native_ad);
        NativeHMKLayout lay2 = findViewById(R.id.native_ad_2);
        NativeHMKLayout lay3 = findViewById(R.id.native_ad_3);

HMK.showNativeIn(lay1, NativeType.IMAGE);
HMK.showNativeIn(lay2, NativeType.SMALL);*/

HMK.showSplashAd(this, new SplashAdListener(){
    @Override
    public void onAdClosed() {
        super.onAdClosed();
        config();
    }

    @Override
    public void onAdFailedToLoad(String erno) {
        super.onAdFailedToLoad(erno);
    }
});
      //  HMK.showNativeIn(lay3, NativeType.VIDEO);
    }

    private void in(){
        HMK.showInterstitialAd(new AdListenerHMK(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Toast.makeText(MainActivity.this, "CLOSED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void config(){
   /*     HMK.putBannerH(findViewById(R.id.adcontainer), AdSizeBanner.Smart, new AdListenerHMK(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("MAIN", "onAdLoaded: CARGO" );
            }

            @Override
            public void onAdFailed(String erno) {
                super.onAdFailed(erno);
                Log.e("MAIN", "onAdFailed: "+erno );
            }
        });

        NativeHMKLayout lay1 = findViewById(R.id.native_ad);
        NativeHMKLayout lay2 = findViewById(R.id.native_ad_2);
        NativeHMKLayout lay3 = findViewById(R.id.native_ad_3);

        HMK.showNativeIn(lay1, NativeType.IMAGE);
        HMK.showNativeIn(lay2, NativeType.SMALL);*/
        //Toast.makeText(this, "se fue", Toast.LENGTH_SHORT).show();
    }
}