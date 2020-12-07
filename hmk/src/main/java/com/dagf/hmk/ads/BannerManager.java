package com.dagf.hmk.ads;

import android.view.View;
import android.widget.LinearLayout;

import com.dagf.hmk.R;
import com.dagf.hmk.utils.AdListenerHMK;
import com.dagf.hmk.utils.AdSizeBanner;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;

import java.util.ArrayList;

public class BannerManager {



    public BannerAdSize bannerAdSize (AdSizeBanner adSizeBanner){
        BannerAdSize adSize = null;
        switch (adSizeBanner) {
            case Comun:
                adSize = BannerAdSize.BANNER_SIZE_320_50;
                break;
            case Large:
                adSize = BannerAdSize.BANNER_SIZE_320_100;
                break;
            case Rectangule:
                adSize = BannerAdSize.BANNER_SIZE_300_250;
                break;
            case Smart:
                adSize = BannerAdSize.BANNER_SIZE_SMART;
                break;
            case SmartBig:
                adSize = BannerAdSize.BANNER_SIZE_360_57;
                break;
            case Big:
                adSize = BannerAdSize.BANNER_SIZE_360_144;
                break;
            default:
                break;
        }
        return adSize;
    }

    public static final int REFRESH_TIME = 30;
    private ArrayList<LinearLayout> bannersContainers = new ArrayList<>();
    public AdListenerHMK adListener;
    public void addBannerIn(LinearLayout v, AdSizeBanner adSizeBanner){
        // Call new BannerView(Context context) to create a BannerView class.
       BannerView bannerView = new BannerView(v.getContext());

        // Set an ad slot ID.
        bannerView.setAdId(v.getContext().getString(R.string.banner_ad_id));

        // Set the background color and size based on user selection.
        bannerView.setBannerAdSize(bannerAdSize(adSizeBanner));

      //  int color = getBannerViewBackground(colorRadioGroup.getCheckedRadioButtonId());
       // bannerView.setBackgroundColor(color);

        addBannerContainer(v);

        v.addView(bannerView);
        if(adListener != null)
        bannerView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adListener.onAdClosed();
            }

            @Override
            public void onAdFailed(int i) {
                super.onAdFailed(i);
                adListener.onAdFailed("code: "+i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                adListener.onAdOpen();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adListener.onAdLoaded();
            }
        });
        bannerView.setBannerRefresh(REFRESH_TIME);
        bannerView.loadAd(new AdParam.Builder().build());
    }

    private void addBannerContainer(LinearLayout v) {
        boolean contain = false;
    for(int i=0; i < bannersContainers.size(); i++){
if(bannersContainers.get(i).getId() == v.getId()){
    contain = true;
}


    }
        if(!contain){
            bannersContainers.add(v);
        }
    }

    public void removeCertainBannerIn(LinearLayout v){
        for(int i=0; i < bannersContainers.size(); i++){
            if(bannersContainers.get(i).getId() == v.getId()){
             bannersContainers.get(i).removeAllViews();
            }


        }
    }

}
