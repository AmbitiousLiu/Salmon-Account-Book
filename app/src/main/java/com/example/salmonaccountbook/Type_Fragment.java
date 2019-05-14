package com.example.salmonaccountbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.salmonaccountbook.LoginActivity.username;

public class Type_Fragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_type, container, false);



        LinearLayout ll_fuzhuang = view.findViewById(R.id.ll_fuzhuang);
        LinearLayout ll_chihe = view.findViewById(R.id.ll_chihe);
        LinearLayout ll_chuxing = view.findViewById(R.id.ll_chuxing);
        LinearLayout ll_qita = view.findViewById(R.id.ll_qita);




        ll_fuzhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TypeActivity.class);
                String name = "服装";
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        ll_chihe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TypeActivity.class);
                String name = "餐饮";
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        ll_chuxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TypeActivity.class);
                String name = "出行";
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        ll_qita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TypeActivity.class);
                String name = "其他";
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });


        return view;
    }
}
