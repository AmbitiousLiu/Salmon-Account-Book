package com.example.salmonaccountbook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ForgetActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        final EditText et_name_forget = findViewById(R.id.et_name_forget);
        final EditText et_birthday_forget = findViewById(R.id.et_birthday_forget);
        final Button btn_forget = findViewById(R.id.btn_forget);

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_name_forget.getText().toString().equals("")){
                    Toast.makeText(ForgetActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                }
                else if (et_birthday_forget.getText().toString().equals("")){
                    Toast.makeText(ForgetActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                }
                else{
                    List<Person> people = DataSupport.where("username = ? and birthday = ?",et_name_forget.getText().toString(),et_birthday_forget.getText().toString()).find(Person.class);
                    if (people.isEmpty()){
                        Toast.makeText(ForgetActivity.this,"用户名或生日错误！",Toast.LENGTH_LONG).show();
                        et_name_forget.setText("");
                        et_birthday_forget.setText("");
                    }
                    else{

                        final AlertDialog dialog1 = new AlertDialog.Builder(ForgetActivity.this)
                                .setTitle("密码")
                                .setMessage(people.get(0).getPassword().toString())
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        onBackPressed();
                                    }
                                })
                                .create();
                        dialog1.show();
                    }
                }
            }
        });

    }
}
