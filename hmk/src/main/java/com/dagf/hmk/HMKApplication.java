package com.dagf.hmk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.common.ApiException;

public class HMKApplication extends Application {
    public static String TAG = "MAIN-HMK";
    private static final String key_save = "KADASDASq";

    public static void tokenSaved(){
        preferences.edit().putBoolean(key_save, true).apply();
    }

    public static boolean isTokenSaved(){
        return preferences.getBoolean(key_save, false);
    }

    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("hmk", MODE_PRIVATE);


        HwAds.init(this);

        if(!isTokenSaved()){
getToken();
            Log.e(TAG, "getToken: initializing");
//            Toast.makeText(this, "INITIALIZING", Toast.LENGTH_SHORT).show();
        }
        c = this;
    }

    private static Context c;
    public static Context get(){
        return c;
    }

    public void getToken() {
        if(isTokenSaved()){
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    // read from agconnect-services.json
                    String appId = AGConnectServicesConfig.fromContext(getApplicationContext()).getString("client/app_id");
                    String token = HmsInstanceId.getInstance(getApplicationContext()).getToken(appId, "HCM");
                    Log.e(TAG, "get token:" + token);
               //     Toast.makeText(HMKApplication.this, "SI BIEN!!!", Toast.LENGTH_SHORT).show();
                    // showLog("get token:" + token);
                } catch (ApiException e) {
                 //   Toast.makeText(HMKApplication.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "get token failed, " + e);
                    //    showLog("get token failed, " + e);
                }
            }
        }.start();
    }
}
