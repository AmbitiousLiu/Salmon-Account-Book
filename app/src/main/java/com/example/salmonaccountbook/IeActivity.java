package com.example.salmonaccountbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class IeActivity extends AppCompatActivity {
    public static int my = 0;
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH)+1;
    int year = calendar.get(Calendar.YEAR);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ie);

        LinearLayout layout_ie = findViewById(R.id.layout_ie);

        if(MainActivity.flag==0){
            layout_ie.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_ie.setBackgroundColor(Color.parseColor("#58000000"));
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    protected void onStart(){
        super.onStart();
        final String username;
        username = LoginActivity.username;
        final long month_date_min = year*10000+month*100+1;
        final long month_date_max = year*10000+month*100+31;
        final long year_date_min = year*10000+100+1;
        final long year_date_max = year*10000+12*100+31;

        final TextView tv_ie = findViewById(R.id.tv_ie);
        final TextView tv_ie_year = findViewById(R.id.tv_ie_year);
        final TextView tv_ie_month = findViewById(R.id.tv_ie_month);
        ImageView ll_look_iedetail = findViewById(R.id.ll_look_iedetail);
        ImageView ll_look_linechart = findViewById(R.id.ll_look_linechart);
        final TextView tv_ie_totalamount = findViewById(R.id.tv_ie_totalamount);
        final ImageView iv_title_back = findViewById(R.id.iv_title_back);
        final ImageView iv_title_delete = findViewById(R.id.iv_title_delete);


        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");


        if("income".equals(name)){
            tv_ie.setBackgroundResource(R.drawable.income);
            tv_ie_year.setBackgroundResource(R.drawable.nianshou);
            tv_ie_month.setBackgroundResource(R.drawable.yueshou);
        }

        if("expenditure".equals(name)){
            tv_ie.setBackgroundResource(R.drawable.expenditure);
            tv_ie_year.setBackgroundResource(R.drawable.nianzhi);
            tv_ie_month.setBackgroundResource(R.drawable.yuezhi);
        }
        List<Data> dataList;
        if(IeActivity.my==0){
            dataList = DataSupport.where("username = ? and ie = ? and date >= ? and date <= ?",username,name ,Long.toString(month_date_min),Long.toString(month_date_max)).order("date desc").find(Data.class);
        }else{
            dataList = DataSupport.where("username = ? and ie = ?",username,name).order("date desc").find(Data.class);
        }
        double total = 0;
        for(Data data:dataList){
            total += data.getMoney();
        }
        DecimalFormat df   = new DecimalFormat("#0.00");
        tv_ie_totalamount.setText("￥"+df.format(total));


        ll_look_iedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(IeActivity.this,IedetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ie",name);
                bundle.putInt("year",year);
                bundle.putInt("month",month);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
        ll_look_linechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(IeActivity.this,LineChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ie",name);
                bundle.putInt("year",year);
                bundle.putInt("month",month);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        tv_ie_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my = 1;
                List<Data> dataList = DataSupport.where("username = ? and ie = ? and date >= ? and date <= ?",username,name,Long.toString(year_date_min),Long.toString(year_date_max))
                        .find(Data.class);
                double total = 0;
                for(Data data:dataList){
                    total += data.getMoney();
                }
                DecimalFormat df   = new DecimalFormat("#0.00");
                tv_ie_totalamount.setText("￥"+df.format(total));
            }
        });

        tv_ie_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my = 0;
                List<Data> dataList = DataSupport.where("username = ? and ie = ? and date >= ? and date <= ?",username,name,Long.toString(month_date_min),Long.toString(month_date_max))
                        .find(Data.class);
                double total = 0;
                for(Data data:dataList){
                    total += data.getMoney();
                }
                DecimalFormat df   = new DecimalFormat("#0.00");
                tv_ie_totalamount.setText("￥"+df.format(total));
            }
        });

        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        registerForContextMenu(iv_title_delete);
        iv_title_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = getLayoutInflater().inflate(R.layout.ie_choose,null);
                Button btn_y_choose_down = view1.findViewById(R.id.y_choose_down);
                final TextView text_y = view1.findViewById(R.id.y_choose_text);
                Button btn_y_choose_up = view1.findViewById(R.id.y_choose_up);
                Button btn_m_choose_down = view1.findViewById(R.id.m_choose_down);
                final TextView text_m = view1.findViewById(R.id.m_choose_text);
                Button btn_m_choose_up = view1.findViewById(R.id.m_choose_up);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(IeActivity.this)
                        .setTitle("请选择时间")
                        .setView(view1)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onStart();
                            }
                        });
                dialog.create().show();
                text_y.setText(String.valueOf(year)+"年");
                text_m.setText(String.valueOf(month)+"月");
                System.out.println(String.valueOf(year));
                btn_y_choose_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(year>2010&&year<=2030){
                            year -= 1;
                            text_y.setText(String.valueOf(year)+"年");
                        }

                    }
                });
                btn_y_choose_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(year>=2010&&year<2030){
                            year += 1;
                            text_y.setText(String.valueOf(year)+"年");
                        }

                    }
                });
                btn_m_choose_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(month>1&&month<=12){
                            month -= 1;
                            text_m.setText(String.valueOf(month)+"月");
                        }


                    }
                });
                btn_m_choose_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(month>=1&&month<12){
                            month += 1;
                            text_m.setText(String.valueOf(month)+"月");
                        }


                    }
                });

            }

        });
    }
}