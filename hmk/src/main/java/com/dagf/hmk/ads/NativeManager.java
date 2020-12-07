package com.dagf.hmk.ads;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dagf.hmk.HMKApplication;
import com.dagf.hmk.R;
import com.dagf.hmk.utils.AdListenerHMK;
import com.dagf.hmk.utils.AdVideoEvent;
import com.dagf.hmk.utils.NativeType;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.VideoOperator;
import com.huawei.hms.ads.nativead.DislikeAdListener;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;

public class NativeManager {
    private Context c;
    private AdListenerHMK adListener;
    private AdVideoEvent adVideoEvent;

    public NativeManager() {
        this.c = HMKApplication.get();
    }

    public NativeManager(AdListenerHMK ad, AdVideoEvent adVideoEvent) {
        this.c = HMKApplication.get();
        this.adListener = ad;
        this.adVideoEvent = adVideoEvent;
    }


    private int getLayout(NativeType nativeType) {

        if (nativeType == NativeType.SMALL) {
            adId = c.getString(R.string.ad_id_native_small);
            return R.layout.native_small_template;
        } else {
            if(nativeType == NativeType.IMAGE){
                adId = c.getString(R.string.ad_id_native);
            }else{
                adId = c.getString(R.string.ad_id_native_video);
            }
            return R.layout.native_video_template;
        }


    }


    private String adId = "";
    public void showNativeIn(NativeHMKLayout nativeLayout, NativeType nativeType) {

        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(c, c.getString(R.string.ad_id_native));

        builder.setNativeAdLoadedListener(new NativeAd.NativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                // Display native ad.
                if(adListener != null){
                    adListener.onAdLoaded();
                }
                showNativeAd(nativeAd, nativeType, nativeLayout);
            }
        }).setAdListener(new AdListener() {
            @Override
            public void onAdFailed(int errorCode) {
                // Call this method when an ad fails to be loaded.
                if(adListener != null){
                    adListener.onAdFailed("Code: "+errorCode);
                }
            }
        });

        NativeAdConfiguration adConfiguration = new NativeAdConfiguration.Builder()
                .setChoicesPosition(NativeAdConfiguration.ChoicesPosition.BOTTOM_RIGHT) // Set custom attributes.
                .build();

        NativeAdLoader nativeAdLoader = builder.setNativeAdOptions(adConfiguration).build();

        nativeAdLoader.loadAd(new AdParam.Builder().build());
    }

    private void showNativeAd(NativeAd globalNativeAd, NativeType nativeType, NativeHMKLayout nativeLayout) {

        // Obtain NativeView.
        final NativeView nativeView = (NativeView) LayoutInflater.from(c).inflate(getLayout(nativeType), null);

        // Register and populate a native ad material view.
        initNativeAdView(globalNativeAd, nativeView);
        globalNativeAd.setDislikeAdListener(new DislikeAdListener() {
            @Override
            public void onAdDisliked() {
                // Call this method when an ad is closed.
            //    updateStatus(getString(R.string.ad_is_closed), true);
                nativeLayout.removeView(nativeView);
            }
        });

        // Add NativeView to the app UI.
        nativeLayout.removeAllViews();
        nativeLayout.addView(nativeView);
    }

    private void initNativeAdView(NativeAd nativeAd, NativeView nativeView) {
        // Register a native ad material view.
        nativeView.setTitleView(nativeView.findViewById(R.id.ad_title));
        nativeView.setMediaView((MediaView) nativeView.findViewById(R.id.ad_media));
        nativeView.setAdSourceView(nativeView.findViewById(R.id.ad_source));
        nativeView.setCallToActionView(nativeView.findViewById(R.id.ad_call_to_action));

        // Populate a native ad material view.
        ((TextView) nativeView.getTitleView()).setText(nativeAd.getTitle());
        nativeView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (null != nativeAd.getAdSource()) {
            ((TextView) nativeView.getAdSourceView()).setText(nativeAd.getAdSource());
        }
        nativeView.getAdSourceView()
                .setVisibility(null != nativeAd.getAdSource() ? View.VISIBLE : View.INVISIBLE);

        if (null != nativeAd.getCallToAction()) {
            ((Button) nativeView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        nativeView.getCallToActionView()
                .setVisibility(null != nativeAd.getCallToAction() ? View.VISIBLE : View.INVISIBLE);

        // Obtain a video controller.
        VideoOperator videoOperator = nativeAd.getVideoOperator();

        // Check whether a native ad contains video materials.
        if (videoOperator.hasVideo()) {
            // Add a video lifecycle event listener.
            videoOperator.setVideoLifecycleListener(videoLifecycleListener);
        }

        // Register a native ad object.
        nativeView.setNativeAd(nativeAd);
    }

    private VideoOperator.VideoLifecycleListener videoLifecycleListener = new VideoOperator.VideoLifecycleListener() {
        @Override
        public void onVideoStart() {
            if(adVideoEvent != null){
                adVideoEvent.onVideoStart();
            }
        }

        @Override
        public void onVideoPlay() {
            if(adVideoEvent != null){
                adVideoEvent.onVideoPlay();
            }
        }

        @Override
        public void onVideoEnd() {
            // If there is a video, load a new native ad only after video playback is complete.
            if(adVideoEvent != null){
                adVideoEvent.onVideoEnd();
            }
        }
    };

}
