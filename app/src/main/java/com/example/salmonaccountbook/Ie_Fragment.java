package com.example.salmonaccountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Ie_Fragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_ie, container, false);

        ImageView iv_income = view.findViewById(R.id.iv_income);
        ImageView iv_expenditure = view.findViewById(R.id.iv_expenditure);

        iv_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),IeActivity.class);
                String name = "income";
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        iv_expenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),IeActivity.class);
                String name = "expenditure";
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        return view;
    }
}
