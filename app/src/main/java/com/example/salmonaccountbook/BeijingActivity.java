package com.example.salmonaccountbook;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BeijingActivity extends AppCompatActivity {

    ImageView beijing_1;
    ImageView beijing_2;
    ImageView beijing_3;
    ImageView beijing_4;
    ImageView beijing_5;
    ImageView beijing_6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beijing);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        beijing_1 = findViewById(R.id.iv_beijing1);
        beijing_2 = findViewById(R.id.iv_beijing2);
        beijing_3 = findViewById(R.id.iv_beijing3);
        beijing_4 = findViewById(R.id.iv_beijing4);
        beijing_5 = findViewById(R.id.iv_beijing5);
        beijing_6 = findViewById(R.id.iv_beijing6);

        LinearLayout layout_beijing = findViewById(R.id.layout_beijing);

        if(MainActivity.flag==0){
            layout_beijing.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_beijing.setBackgroundColor(Color.parseColor("#58000000"));
        }

        beijing_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setBeijing("beijing1");
                person.updateAll("username = ?",LoginActivity.username);
                Toast.makeText(BeijingActivity.this,"背景修改成功！",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        beijing_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setBeijing("beijing2");
                person.updateAll("username = ?",LoginActivity.username);
                Toast.makeText(BeijingActivity.this,"背景修改成功！",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        beijing_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setBeijing("beijing3");
                person.updateAll("username = ?",LoginActivity.username);
                Toast.makeText(BeijingActivity.this,"背景修改成功！",Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

        beijing_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setBeijing("beijing4");
                person.updateAll("username = ?",LoginActivity.username);
                Toast.makeText(BeijingActivity.this,"背景修改成功！",Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

        beijing_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setBeijing("beijing5");
                person.updateAll("username = ?",LoginActivity.username);
                Toast.makeText(BeijingActivity.this,"背景修改成功！",Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

        beijing_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setBeijing("beijing6");
                person.updateAll("username = ?",LoginActivity.username);
                Toast.makeText(BeijingActivity.this,"背景修改成功！",Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

    }
}