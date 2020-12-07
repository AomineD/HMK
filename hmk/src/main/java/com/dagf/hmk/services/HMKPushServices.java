package com.dagf.hmk.services;

import android.os.Bundle;
import android.util.Log;

import com.dagf.hmk.HMKApplication;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.HmsMessaging;

import static com.dagf.hmk.HMKApplication.TAG;

public class HMKPushServices extends HmsMessageService {
    @Override
    public void onMessageDelivered(String s, Exception e) {
        super.onMessageDelivered(s, e);
    }



    @Override
    public void onNewToken(String s) {

        Log.e(TAG, "onNewToken: "+s );
        HmsMessaging.getInstance(getApplicationContext())
                .subscribe("MAIN")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            HMKApplication.tokenSaved();
                            Log.i(TAG, "subscribe Complete");

                        } else {
                            Log.i(TAG, "Erno push "+ task.getException().getMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "onFailure: "+e.getMessage() );
            }
        });
        super.onNewToken(s);
    }

    @Override
    public void onTokenError(Exception e) {
        Log.e(TAG, "onTokenError: "+e.getMessage());
        super.onTokenError(e);
    }

    @Override
    public void onTokenError(Exception e, Bundle bundle) {
        super.onTokenError(e, bundle);
        Log.e(TAG, "onTokenError: "+e.getMessage() );
    }
}