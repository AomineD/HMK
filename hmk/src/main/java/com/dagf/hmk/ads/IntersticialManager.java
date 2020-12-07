package com.dagf.hmk.ads;

import android.util.Log;

import com.dagf.hmk.HMK;
import com.dagf.hmk.HMKApplication;
import com.dagf.hmk.R;
import com.dagf.hmk.utils.AdListenerHMK;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;

public class IntersticialManager {

    private static InterstitialAd interstitialAd;
    public void loadInterstitialAd() {
        if(interstitialAd != null && interstitialAd.isLoaded()){
            return;
        }
        if(interstitialAd == null)
        interstitialAd = new InterstitialAd(HMKApplication.get());
        interstitialAd.setAdId(HMKApplication.get().getString(R.string.interstitial_ad));


        AdParam adParam = new AdParam.Builder().build();
        if(!interstitialAd.isLoaded())
        interstitialAd.loadAd(adParam);
    }

    public void showInterstitialWithFrec(AdListenerHMK adListener){

        if(frec < HMK.InterstitialFrecuency){
            frec++;
            adListener.onAdClosed();
        }else {
            if(interstitialAd == null || !interstitialAd.isLoaded()){
                loadInterstitialAd();
            }
            if (interstitialAd.isLoading())
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        adListener.onAdClosed();
                    }

                    @Override
                    public void onAdFailed(int i) {
                        super.onAdFailed(i);
                        adListener.onAdFailed("code " + i);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                        adListener.onAdOpen();
                    }

                    @Override
                    public void onAdLoaded() {
                        if (frec >= HMK.InterstitialFrecuency) {
                            adListener.onAdLoaded();
                            interstitialAd.show();
                            frec = 0;
                        }
                        super.onAdLoaded();
                    }
                });
            else if (interstitialAd.isLoaded()) {
                if (frec >= HMK.InterstitialFrecuency) {
                    adListener.onAdLoaded();
                    interstitialAd.show();
                    frec = 0;
                }
            }
        }
    }
    private static int frec = 0;
}
