package com.dagf.hmk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.dagf.hmk.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Timer;
import java.util.TimerTask;

public class RatingDialogHMK extends BottomSheetDialogFragment {

    private FragmentManager fgM;
    private String url_store = "", emaildev ="";

    private String key_save_ = "KJAQMVSZX";
    private String key_never = "NASDMASD";
    private SharedPreferences preferences;

    public int frecuency_rating = 3;

    public boolean canShow(){
       return (preferences.getInt(key_save_, 0) >= frecuency_rating - 1) && !preferences.getBoolean(key_never, false);
    }

    public RatingDialogHMK(AppCompatActivity cont, String url_to, String email_developer){
        this.fgM = cont.getSupportFragmentManager();
        this.url_store = url_to;
        this.emaildev = email_developer;
        preferences =cont.getPreferences(Context.MODE_PRIVATE);
       // Log.e("MAINb", "RatingDialogHMK: "+(preferences != null) );
    }

    public void showRating(){
        if(canShow()){
            show(fgM, "tagas");
            preferences.edit().putInt(key_save_, 0).apply();
        }else if(!preferences.getBoolean(key_never, false)){
            int act = preferences.getInt(key_save_, 0);
            act++;
            preferences.edit().putInt(key_save_, act).apply();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rating_bottom, container, false);
        return v;
    }

    private RelativeLayout animation_positive, animation_negative;
    private LinearLayout general_rel;
    private RatingBar ratingBar;
    private CardView never, later, submit, send_email;
    private TextView title_app_rate;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

general_rel = view.findViewById(R.id.lin_general);
animation_negative = view.findViewById(R.id.animation_negative);
animation_positive = view.findViewById(R.id.animation_positive);
String rtitl = getString(R.string.doyoulike)+" "+getString(R.string.app_name)+"?";


ratingBar = view.findViewById(R.id.rating);
never = view.findViewById(R.id.never_btn);
later = view.findViewById(R.id.later_btn);
submit = view.findViewById(R.id.rate_btn);
send_email = view.findViewById(R.id.tell_us_btn);
title_app_rate = view.findViewById(R.id.title_app_rating);

        title_app_rate.setText(rtitl);
configButtons();
    }

    private void configButtons() {
    never.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            preferences.edit().putBoolean(key_never, true).apply();
            dismiss();
        }
    });

    later.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    });
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkAll();

        }
    });

    send_email.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sendto = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+emaildev));
            sendto.putExtra(Intent.EXTRA_SUBJECT, "Contactar con el soporte de "+getString(R.string.app_name));
            sendto.putExtra(Intent.EXTRA_TEXT, "Hola, quisiera que mejorarán...");
            startActivity(sendto);
            dismiss();
        }
    });

    }

    private void checkAll() {
    if(Math.round(ratingBar.getRating()) <= 3){
    animation_negative.setVisibility(View.VISIBLE);
    general_rel.setVisibility(View.GONE);
    }else{
        animation_positive.setVisibility(View.VISIBLE);
        general_rel.setVisibility(View.GONE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(!url_store.isEmpty()){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_store)));
                    preferences.edit().putBoolean(key_never, true).apply();
                    dismiss();
                }
            }
        }, 2000);
    }

    }
}

