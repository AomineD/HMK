package com.dagf.hmk.ads;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dagf.hmk.HMKApplication;
import com.dagf.hmk.R;
import com.dagf.hmk.utils.SplashAdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.AudioFocusType;
import com.huawei.hms.ads.splash.SplashView;

public class SplashManager {
    // Ad display timeout interval, in milliseconds.
    public static int AD_TIMEOUT = 6000;

    // Ad display timeout message flag.
    private static final int MSG_AD_TIMEOUT = 1001;

    // Callback handler used when the ad display timeout message is received.
    private Handler timeoutHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (appCompatActivity.hasWindowFocus() && listener != null) {
                //jump();
                listener.onAdClosed();
            }
            return false;
        }
    });

    private SplashView splashView;

    private SplashView.SplashAdLoadListener splashAdLoadListener = new SplashView.SplashAdLoadListener() {
        @Override
        public void onAdLoaded() {
            // Call this method when an ad is successfully loaded.
            Log.i(HMKApplication.TAG, "SplashAD onAdLoaded.");
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            // Call this method when an ad fails to be loaded.
            Log.i(HMKApplication.TAG, "SplashAdLoadListener onAdFailedToLoad, errorCode: " + errorCode);
            if(listener != null){
                listener.onAdFailedToLoad("ErrorCode: "+errorCode);
            }
        }

        @Override
        public void onAdDismissed() {
            // Call this method when the ad display is complete.
            Log.i(HMKApplication.TAG, "SplashAdLoadListener onAdDismissed.");

        }
    };


    private void configuring(){
        ViewGroup ve  = (ViewGroup) ((ViewGroup) appCompatActivity
                .findViewById(android.R.id.content)).getChildAt(0);
       RelativeLayout rel =  ((RelativeLayout)ve);
rel.removeAllViews();
        View v = LayoutInflater.from(appCompatActivity).inflate(R.layout.splash_layout, rel, false);
        rel.addView(v);

    }

    public SplashManager(AppCompatActivity activity, SplashAdListener adListener){
this.appCompatActivity = activity;
this.listener = adListener;
        configuring();
    }

private AppCompatActivity appCompatActivity;
    private SplashAdListener listener;
    public void loadAd() {
        Log.i(HMKApplication.TAG, "Start to load ad");

        AdParam adParam = new AdParam.Builder().build();
        splashView = appCompatActivity.findViewById(R.id.splash_ad_view);
        //splashView.setAdDisplayListener(adDisplayListener);

        // Set a default app launch image.
        splashView.setSloganResId(R.drawable.default_slogan);
        splashView.setLogo(appCompatActivity.findViewById(R.id.logo_area));

        // Set a logo image.
        splashView.setLogoResId(R.mipmap.ic_launcher);
        // Set logo description.
        splashView.setMediaNameResId(R.string.media_name);
        // Set the audio focus type for a video splash ad.
        splashView.setAudioFocusType(AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE);

        splashView.load(appCompatActivity.getString(R.string.ad_id_splash), ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, adParam, splashAdLoadListener);
        Log.i(HMKApplication.TAG, "End to load ad");

        // Remove the timeout message from the message queue.
        timeoutHandler.removeMessages(MSG_AD_TIMEOUT);
        // Send a delay message to ensure that the app home screen can be displayed when the ad display times out.
        timeoutHandler.sendEmptyMessageDelayed(MSG_AD_TIMEOUT, AD_TIMEOUT);
    }
}
