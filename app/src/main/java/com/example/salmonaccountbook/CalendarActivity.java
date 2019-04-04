package com.example.salmonaccountbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        Button btn_record_choose = findViewById(R.id.btn_record_choose);
        final TextView tv_record_show = findViewById(R.id.tv_record_show);
        CalendarView cv_record_calendar = findViewById(R.id.cv_record_calendar);
        LinearLayout layout_calendar = findViewById(R.id.layout_calendar);

        if(MainActivity.flag==0){
            layout_calendar.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_calendar.setBackgroundColor(Color.parseColor("#58000000"));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date0 = new Date(System.currentTimeMillis());
        tv_record_show.setText(simpleDateFormat.format(date0));

        cv_record_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tv_record_show.setText(Integer.toString(year*10000+(month+1)*100+dayOfMonth));
            }
        });

        btn_record_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this,RecordActivity.class);
                String choose_date = tv_record_show.getText().toString();
                intent.putExtra("choose_date",choose_date);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}



