package com.dagf.hmk;

import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dagf.hmk.ads.BannerManager;
import com.dagf.hmk.ads.IntersticialManager;
import com.dagf.hmk.ads.NativeHMKLayout;
import com.dagf.hmk.ads.NativeManager;
import com.dagf.hmk.ads.SplashManager;
import com.dagf.hmk.utils.AdListenerHMK;
import com.dagf.hmk.utils.AdSizeBanner;
import com.dagf.hmk.utils.AdVideoEvent;
import com.dagf.hmk.utils.NativeType;
import com.dagf.hmk.utils.SplashAdListener;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.BannerAdSize;

public class HMK {
public static int InterstitialFrecuency = 2;

    private static BannerManager bannerManager;
    private static IntersticialManager intersticialManager;
    public static void putBannerH(LinearLayout in, AdSizeBanner adSize){
if(bannerManager == null)
        bannerManager = new BannerManager();

        bannerManager.addBannerIn(in, adSize);

    }

    public static void putBannerH(LinearLayout in, AdSizeBanner adSize, AdListenerHMK adListener){

        if(bannerManager == null)
            bannerManager = new BannerManager();
bannerManager.adListener = adListener;
        bannerManager.addBannerIn(in, adSize);

    }

    public static void showInterstitialAd(AdListenerHMK adListener){
        if(intersticialManager == null){
            intersticialManager = new IntersticialManager();
        }
        intersticialManager.showInterstitialWithFrec(adListener);
    }


    public static void showNativeIn(NativeHMKLayout layout, NativeType type){
        NativeManager nativeManager = new NativeManager();

        nativeManager.showNativeIn(layout, type);
    }

    public static void showNativeIn(NativeHMKLayout layout, NativeType type, AdVideoEvent adVideoEvent, AdListenerHMK adListenerHMK){
        NativeManager nativeManager = new NativeManager(adListenerHMK, adVideoEvent);

        nativeManager.showNativeIn(layout, type);
    }

    public static void showSplashAd(AppCompatActivity activity, SplashAdListener listener){
        SplashManager splashManager = new SplashManager(activity, listener);

        splashManager.loadAd();
    }
}
