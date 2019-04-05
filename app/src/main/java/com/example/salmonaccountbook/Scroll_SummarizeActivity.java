package com.example.salmonaccountbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.salmonaccountbook.LoginActivity.username;

public class Scroll_SummarizeActivity extends AppCompatActivity {

    private BarChart mBarChart;
    private BarChart mBarChart1;
    private BarCharts mBarCharts;
    private Button mbtn;
    private ImageView imageView;

    Calendar calendar = Calendar.getInstance();//获取日历
    int month = calendar.get(Calendar.MONTH)+1;
    int year = calendar.get(Calendar.YEAR);
    long month_date_min ;

    private String[] color = {"#C4FF8E", "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D",
            "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#FFF88D", "#FFD38C",
            "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D",
            "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#FFF88D", "#FFD38C",
            "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D",
            "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C", "#FFF88D", "#FFD38C",
            "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E", "#FFF88D", "#FFD38C",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD", "#C4FF8E",
            "#FFF88D", "#FFD38C", "#8CEBFF", "#FF8F9D", "#6BF3AD"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_summarize);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        imageView = findViewById(R.id.analysis);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Scroll_SummarizeActivity.this,SummarizeActivity.class);
                startActivity(intent);
            }
        });
//        mBarCharts = new BarCharts();         // 创建多张图表
//         mBarChart = (BarChart) findViewById(R.id.spreadBarChart);
//         mBarChart1 = (BarChart) findViewById(R.id.spreadBarChart1);
//
//         mBarCharts.showBarChart(mBarChart, getBarData(31, 62,"income"), true);
//         mBarCharts.showBarChart(mBarChart1, getBarData(31, 62,"expenditure"), true);

    }

    @Override
    public void onStart(){
        super.onStart();
        mbtn = findViewById(R.id.btn_scroll);
        mBarCharts = new BarCharts();         // 创建多张图表
        mBarChart = (BarChart) findViewById(R.id.spreadBarChart);
        mBarChart1 = (BarChart) findViewById(R.id.spreadBarChart1);


        month_date_min = year*10000+month*100+1;

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = getLayoutInflater().inflate(R.layout.ie_choose,null);
                Button btn_y_choose_down = view1.findViewById(R.id.y_choose_down);
                final TextView text_y = view1.findViewById(R.id.y_choose_text);
                Button btn_y_choose_up = view1.findViewById(R.id.y_choose_up);
                Button btn_m_choose_down = view1.findViewById(R.id.m_choose_down);
                final TextView text_m = view1.findViewById(R.id.m_choose_text);
                Button btn_m_choose_up = view1.findViewById(R.id.m_choose_up);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(Scroll_SummarizeActivity.this)
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
        mBarCharts.showBarChart(mBarChart, getBarData(31, 62,"income"), true,"income");
        mBarCharts.showBarChart(mBarChart1, getBarData(31, 62,"expenditure"), true,"expenditure");
    }

    public BarData getBarData(int count, float range,String str) {

        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xValues.add("" + (i + 1) + "d");// 设置每个柱壮图的文字描述
        }
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            //float value = (float) (Math.random() * range/*100以内的随机数*/) + 3;
            //yValues.add(new BarEntry(value, i));
            long date = month_date_min;
            date += i;
            List<Data> dataList1 = DataSupport.where("username = ? and date = ? and ie = ?",username,String.valueOf(date),str)
                    .find(Data.class);
            float total1 = 0;
            for(Data data:dataList1){
                total1 += data.getMoney();
            }
            yValues.add(new BarEntry(i + 1,total1));
        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试图");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
//            colors.add(Color.parseColor("#75bfe2"));
            colors.add(Color.parseColor(color[i]));
        }
        barDataSet.setColors(colors);
        // 设置栏阴影颜色
        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        barDataSet.setValueTextColor(Color.parseColor("#FF8F9D"));
        // 绘制值
        barDataSet.setDrawValues(true);
        BarData barData = new BarData(barDataSets);
        return barData;
    }
}
