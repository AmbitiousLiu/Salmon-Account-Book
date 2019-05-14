package com.example.salmonaccountbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        TextView tv_version = findViewById(R.id.tv_version);
        RelativeLayout layout_about = findViewById(R.id.layout_about);
        if(MainActivity.flag==0){
            layout_about.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_about.setBackgroundColor(Color.parseColor("#58000000"));
        }

        tv_version.setText("版本号："+BuildConfig.VERSION_NAME);

    }
}