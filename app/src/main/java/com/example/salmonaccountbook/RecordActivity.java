package com.example.salmonaccountbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    public String ie = "";
    public String type = "";
    public String date = "";
    public double money = 0;
    public String remarks = "无备注";
    public String username = "";
    private TextView tv_record_date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        LinearLayout layout_record = findViewById(R.id.layout_record);

        if(MainActivity.flag==0){
            layout_record.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_record.setBackgroundColor(Color.parseColor("#58000000"));
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        LinearLayout ll_record_choosedate = findViewById(R.id.ll_record_choosedate);

        RadioGroup rg_ie = findViewById(R.id.rg_ie);
        RadioGroup rg_type = findViewById(R.id.rg_type);
        rg_type.setOnCheckedChangeListener(this);
        rg_ie.setOnCheckedChangeListener(this);

        final EditText et_record_money = findViewById(R.id.et_record_money);
        final EditText et_record_remarks = findViewById(R.id.et_record_remarks);
        tv_record_date = findViewById(R.id.tv_record_date);

        Button btn_record_submit = findViewById(R.id.btn_record_submit);
        Button btn_record_cancel = findViewById(R.id.btn_record_cancel);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date0 = new Date(System.currentTimeMillis());
        date = simpleDateFormat.format(date0);
        tv_record_date.setText(date+" (点击可选择日期)");


        btn_record_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_record_choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, CalendarActivity.class);
                startActivityForResult(intent,1);
            }
        });

        btn_record_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.equals("")){
                    Toast.makeText(RecordActivity.this,"你还未选择日期！",Toast.LENGTH_LONG).show();
                }
                else if(ie.equals("")){
                    Toast.makeText(RecordActivity.this,"你还未选择收支！",Toast.LENGTH_LONG).show();
                }
                else  if(type.equals("")){
                    Toast.makeText(RecordActivity.this,"你还未选择类型！",Toast.LENGTH_LONG).show();
                }
                else if (et_record_money.getText().toString().equals("")){
                    Toast.makeText(RecordActivity.this,"你还未输入金额！",Toast.LENGTH_LONG).show();
                }
                else{
                    if(et_record_remarks.getText().toString().equals("")==false){
                        remarks = et_record_remarks.getText().toString();
                    }
                    username = LoginActivity.username;
                    money = Double.parseDouble(et_record_money.getText().toString());
                    List<Data> dataList = DataSupport.where("username = ? and date = ? and type = ? and ie = ? and money = ? and remarks = ?",username,date,type,ie,Double.toString(money),remarks).find(Data.class);
                    if (dataList.isEmpty()==false){
                        final AlertDialog dialog = new AlertDialog.Builder(RecordActivity.this)
                                .setTitle("提示")
                                .setMessage("你已经存储过至少一条一模一样的账目，如果删除其中一条的话，相同的几条账目会同时删除！（你可以更换备注来区别账目信息）确定要继续保存吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Data data = new Data();
                                        data.setIe(ie);
                                        data.setDate(Long.parseLong(date));
                                        data.setMoney(money);
                                        data.setType(type);
                                        data.setRemarks(remarks);
                                        data.setUsername(username);
                                        data.save();
                                        Toast.makeText(RecordActivity.this,"保存成功！",Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }
                    else{
                        Data data = new Data();
                        data.setIe(ie);
                        data.setDate(Long.parseLong(date));
                        data.setMoney(money);
                        data.setType(type);
                        data.setRemarks(remarks);
                        data.setUsername(username);
                        data.save();
                        Toast.makeText(RecordActivity.this,"保存成功！",Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    date = data.getStringExtra("choose_date");
                    tv_record_date.setText(date);
                }
                break;
            default:
        }
    }

    public void onCheckedChanged(RadioGroup group, int checkedId){
        switch (checkedId){
            case R.id.rb_income:
                ie = "income";
                break;
            case R.id.rb_expenditure:
                ie = "expenditure";
                break;
            case R.id.rb_fuzhuang:
                type = "服装";
                break;
            case R.id.rb_canyin:
                type = "餐饮";
                break;
            case R.id.rb_chuxing:
                type = "出行";
                break;
            case R.id.rb_qita:
                type = "其他";
                break;
        }
    }

}
