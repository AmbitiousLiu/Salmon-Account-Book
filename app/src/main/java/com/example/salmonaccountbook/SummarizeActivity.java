package com.example.salmonaccountbook;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.salmonaccountbook.LoginActivity.username;

public class SummarizeActivity extends AppCompatActivity {

    private BarChart barChart1;
    private BarChart barChart2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summarize);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        barChart1 = findViewById(R.id.barChart1);
        barChart2 = findViewById(R.id.barChart2);

        Calendar calendar = Calendar.getInstance();//获取日历
        int year = calendar.get(Calendar.YEAR);


        final long year_date_min = year*10000+100+1;
        final long year_date_max = year*10000+12*100+31;

        BarChartManager barChartManager1 = new BarChartManager(barChart1,"income");
        BarChartManager barChartManager2 = new BarChartManager(barChart2,"expenditure");

        //设置x轴的数据
        ArrayList<String> xValues0 = new ArrayList<>();
//        xValues0.add("早晨");
//        xValues0.add("上午");
//        xValues0.add("中午");
//        xValues0.add("下午");
//        xValues0.add("晚上");
        for(int i=0;i<12;i++){
            xValues0.add(""+(i+1)+"月");
        }

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            xValues.add((float) i);
        }

        //设置y轴的数据()
        List<List<Float>> yValues0 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<Float> yValue = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                //yValue.add((float) (Math.random() * 8)+2);
                //写数据
                int month=j+1;
                long month_date_min = year*10000+month*100+1;
                long month_date_max = year*10000+month*100+31;
                String type="";
                switch(i){
                    case 0: type = "服装";break;
                    case 1: type = "餐饮";break;
                    case 2: type = "出行";break;
                    case 3: type = "其他";break;
                }
                List<Data> dataList = DataSupport.where("username = ? and ie = ? and date >= ? " +
                        "and date <= ? and type = ?",username,"income",Long.toString(month_date_min),Long.toString(month_date_max),type).find(Data.class);

                float total1 = 0;
                for(Data data:dataList){
                    total1 += data.getMoney();
                }
                yValue.add((float) total1);
            }
            yValues0.add(yValue);
        }


        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<Float> yValue = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                //yValue.add((float) (Math.random() * 8)+2);
                //写数据
                int month=j+1;
                long month_date_min = year*10000+month*100+1;
                long month_date_max = year*10000+month*100+31;
                String type="";
                switch(i){
                    case 0: type = "服装";break;
                    case 1: type = "餐饮";break;
                    case 2: type = "出行";break;
                    case 3: type = "其他";break;
                }
                List<Data> dataList = DataSupport.where("username = ? and ie = ? and date >= ? " +
                        "and date <= ? and type = ?",username,"expenditure",Long.toString(month_date_min),Long.toString(month_date_max),type).find(Data.class);

                float total1 = 0;
                for(Data data:dataList){
                    total1 += data.getMoney();
                }
                yValue.add((float) total1);
            }
            yValues.add(yValue);
        }


        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("服饰");
        names.add("饮食");
        names.add("出行");
        names.add("其他");

        //创建多条柱状的图表
        //barChartManager1.showBarChart(xValues, yValues.get(0), names.get(1), colors.get(3));
        barChartManager1.showBarChart(xValues0, yValues0,names);
        barChartManager2.showBarChart(xValues0, yValues,names);

    }

}
