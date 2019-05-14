package com.example.salmonaccountbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date_Fragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fm_date, container, false);

        Button btn_fm_date = view.findViewById(R.id.btn_fm_date);
        final TextView tv_showdate = view.findViewById(R.id.tv_showdate);
        final TextView tv_showdate_top = view.findViewById(R.id.tv_showdate_top);
        CalendarView cv_calendar = view.findViewById(R.id.cv_calendar);
        LinearLayout layout_fm_date = view.findViewById(R.id.layout_fm_date);

//        if(MainActivity.flag==0){
//            layout_fm_date.setBackgroundColor(Color.parseColor("#46ffffff"));
//        }else if(MainActivity.flag==1){
//            layout_fm_date.setBackgroundColor(Color.parseColor("#58000000"));
//        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date0 = new Date(System.currentTimeMillis());
        tv_showdate.setText(simpleDateFormat.format(date0));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
        tv_showdate_top.setText(simpleDateFormat2.format(date0));

        cv_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tv_showdate.setText(Integer.toString(year*10000+(month+1)*100+dayOfMonth));
                tv_showdate_top.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        });

        btn_fm_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_showdate.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"您还未选择日期！",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(getActivity(),DateActivity.class);
                    String date = tv_showdate.getText().toString();
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}