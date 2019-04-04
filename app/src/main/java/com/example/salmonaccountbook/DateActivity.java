package com.example.salmonaccountbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DateActivity extends AppCompatActivity {

    private TextView tv_date;
    private ListView lv_date_detail;

    private ArrayAdapter<Data> mAdapter;
    private List<Data> listItem = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);

        LinearLayout layout_date = findViewById(R.id.layout_date);

        if(MainActivity.flag==0){
            layout_date.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_date.setBackgroundColor(Color.parseColor("#58000000"));
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        final String username;
        username = LoginActivity.username;

        tv_date = findViewById(R.id.tv_date);
        lv_date_detail = findViewById(R.id.lv_date_detail);
        final ImageView iv_title_back = findViewById(R.id.iv_title_back);
        final ImageView iv_title_delete = findViewById(R.id.iv_title_delete);
        final CheckBox checkBox = findViewById(R.id.cb_iedetail_item_check);
        final TextView tv_date_income = findViewById(R.id.tv_date_income);
        final TextView tv_date_expenditure = findViewById(R.id.tv_date_expenditure);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        tv_date.setText(date);

        List<Data> dataList1 = DataSupport.where("username = ? and date = ? and ie = ?",username,date,"income")
                .find(Data.class);
        double total1 = 0;
        for(Data data:dataList1){
            total1 += data.getMoney();
        }
        DecimalFormat df1   = new DecimalFormat("#0.00");
        tv_date_income.setText("该类该日收入金钱数:￥"+df1.format(total1));

        List<Data> dataList2 = DataSupport.where("username = ? and date = ? and ie = ?",username,date,"expenditure")
                .find(Data.class);
        double total2 = 0;
        for(Data data:dataList2){
            total2 += data.getMoney();
        }
        DecimalFormat df2   = new DecimalFormat("#0.00");
        tv_date_expenditure.setText("该类该日支出金钱数:￥"+df2.format(total2));

        final List<Data> dataList = DataSupport.where("username = ? and date = ?",username,date).order("date desc").find(Data.class);
        mAdapter = new MyAdapter(DateActivity.this,R.layout.lv_detail_item,dataList);
        lv_date_detail.setAdapter(mAdapter);

        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_title_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!listItem.isEmpty()){
                    final AlertDialog dialog = new AlertDialog.Builder(DateActivity.this)
                            .setTitle("提示")
                            .setMessage("确认要删除所选中的信息吗？删除后不可恢复！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int a) {
                                    for(int i =0;i<listItem.size();i++){
                                        Log.d("用户信息：",listItem.get(i).getType()+listItem.get(i).getMoney()+listItem.get(i).getRemarks()+listItem.get(i).getDate());
                                        DataSupport.deleteAll(Data.class,"username = ? and type = ? and money = ? and remarks = ? and date = ?",
                                                username,listItem.get(i).getType(),listItem.get(i).getMoney()+"",listItem.get(i).getRemarks(),Long.toString(listItem.get(i).getDate()));
                                        Toast.makeText(DateActivity.this,"delete---> "+i,Toast.LENGTH_SHORT).show();
                                        onStart();
                                    }
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }else{
                    Toast.makeText(DateActivity.this,"你还没有选中任何一条账本信息哦~",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class MyAdapter extends ArrayAdapter<Data>{

        private List<Data> mDataList;
        private int mResourceId;
        public List<Boolean> mChecked;

        public MyAdapter(@NonNull Context context, int resource, List<Data> DataList) {
            super(context, resource,DataList);
            this.mResourceId = resource;
            this.mDataList = DataList;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Data data = getItem(position);//实例化
            View view = LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);
            TextView tv_detail_title = view.findViewById(R.id.tv_iedetail_item_title);
            TextView tv_detail_money = view.findViewById(R.id.tv_iedetail_item_money);
            TextView tv_detail_remark = view.findViewById(R.id.tv_iedetail_item_remarks);
            TextView tv_detail_date = view.findViewById(R.id.tv_iedetail_item_date);
            CheckBox cb_detail_check = view.findViewById(R.id.cb_iedetail_item_check);
            tv_detail_title.setText(data.getType());
            if(data.getIe().toString().equals("income")){
                tv_detail_money.setText("￥+"+Double.toString(data.getMoney()));
            }
            else if(data.getIe().toString().equals("expenditure")){
                tv_detail_money.setText("￥-"+Double.toString(data.getMoney()));
            }
            tv_detail_remark.setText(data.getRemarks());
            tv_detail_date.setText(Long.toString(data.getDate()));

            cb_detail_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    if(cb.isChecked()){
                        listItem.add(data);
                    }else{
                        listItem.remove(data);
                    }
                }
            });
            return view;
        }
    }
}
