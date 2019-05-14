package com.example.salmonaccountbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PersonalActivity extends AppCompatActivity {
    public static PersonalActivity instance_personal = null;

    protected void onCreate(Bundle savedInstanceState) {
        instance_personal = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        LinearLayout layout_personal = findViewById(R.id.layout_personal);

        if(MainActivity.flag==0){
            layout_personal.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_personal.setBackgroundColor(Color.parseColor("#58000000"));
        }

        final String username;
        username = LoginActivity.username;

//        RelativeLayout rl_setting = findViewById(R.id.rl_setting);
        final Button btn_alter  = findViewById(R.id.btn_alter);
        final EditText et_password_new = findViewById(R.id.et_password_new);
        final EditText et_password_ensure = findViewById(R.id.et_password_ensure);
        final ImageView look_newpassword = findViewById(R.id.iv_look_newpassword);
        final ImageView look_ensurepassword = findViewById(R.id.iv_look_ensurepassword);
//        final EditText et_password_birthday = findViewById(R.id.et_password_birthday);
        final List<Person> people = DataSupport.where("username = ?",username).find(Person.class);
//        et_password_new.setText(people.get(0).getPassword().toString());
        et_password_new.setFocusable(false);
        et_password_new.setFocusableInTouchMode(false);
//        et_password_ensure.setText(people.get(0).getPassword().toString());
        et_password_ensure.setFocusable(false);
        et_password_ensure.setFocusableInTouchMode(false);
//        et_password_birthday.setText(people.get(0).getBirthday().toString());
//        et_password_birthday.setFocusable(false);
//        et_password_birthday.setFocusableInTouchMode(false);

//        rl_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PersonalActivity.this,SettingActivity.class);
//                intent.putExtra("username",username);
//                startActivity(intent);
//            }
//        });

        et_password_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_password_new.setFocusable(true);
                et_password_new.setFocusableInTouchMode(true);
                et_password_new.requestFocus();
            }
        });
        et_password_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_password_ensure.setFocusable(true);
                et_password_ensure.setFocusableInTouchMode(true);
                et_password_ensure.requestFocus();
            }
        });
        look_newpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_password_new.getInputType() == 144){
                    et_password_new.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    look_newpassword.setImageResource(R.drawable.look);
                }else {
                    et_password_new.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    look_newpassword.setImageResource(R.drawable.look_2);
                }
                //Toast.makeText(PersonalActivity.this,"type"+et_password_new.getInputType(),Toast.LENGTH_SHORT).show();
            }
        });
        look_ensurepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_password_ensure.getInputType() == 144){
                    et_password_ensure.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    look_ensurepassword.setImageResource(R.drawable.look);
                }else {
                    et_password_ensure.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    look_ensurepassword.setImageResource(R.drawable.look_2);
                }
                //Toast.makeText(PersonalActivity.this,"type"+et_password_new.getInputType(),Toast.LENGTH_SHORT).show();
            }
        });
//        et_password_birthday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                et_password_birthday.setFocusable(true);
//                et_password_birthday.setFocusableInTouchMode(true);
//                et_password_birthday.requestFocus();
//            }
//        });

        btn_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_password_new.getText().toString().equals("")){
                    Toast.makeText(PersonalActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                }else if(et_password_new.getText().toString().length()<6){
                    Toast.makeText(PersonalActivity.this,"密码不能少于6位数！",Toast.LENGTH_LONG).show();
                }else if(et_password_new.getText().toString().equals(et_password_ensure.getText().toString())){
                    Person person = new Person();
                    person.setPassword(et_password_new.getText().toString());
//                person.setBirthday(et_password_birthday.getText().toString());
                    person.updateAll("username = ?",LoginActivity.username);
                    Toast.makeText(PersonalActivity.this,"信息修改成功！",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Toast.makeText(PersonalActivity.this,"两次输入密码不一样！",Toast.LENGTH_LONG).show();
                    et_password_new.setText("");
                    et_password_ensure.setText("");
                }
            }
        });
    }
}
