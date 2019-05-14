package com.example.salmonaccountbook;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        final String beijing = "beijing5";
        final String login = "no";
        Button btn_register = findViewById(R.id.btn_register);
        final EditText et_name_register = findViewById(R.id.et_name_register);
        final EditText et_password_register = findViewById(R.id.et_password_register);
        final EditText et_password_affirm_register = findViewById(R.id.et_password_affirm_register);
        final EditText et_birthday = findViewById(R.id.et_birthday);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_name_register.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                }
                List<Person> people = DataSupport.where("username = ?",et_name_register.getText().toString()).find(Person.class);

                if(et_password_register.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                }
                else if(et_password_affirm_register.getText().toString().equals(et_password_register.getText())){
                    Toast.makeText(RegisterActivity.this,"两次密码输入不相同！",Toast.LENGTH_LONG).show();
                }
                else if(et_birthday.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"生日不能为空！",Toast.LENGTH_LONG).show();
                }
                else if(people.isEmpty()==false){
                    Toast.makeText(RegisterActivity.this,"该用户已注册！",Toast.LENGTH_LONG).show();
                }
                else if(et_password_register.getText().toString().length()<6){
                    Toast.makeText(RegisterActivity.this,"密码不能少于6位数！",Toast.LENGTH_LONG).show();
                }
                else{
                    Person person = new Person();
                    person.setUsername(et_name_register.getText().toString());
                    person.setPassword(et_password_register.getText().toString());
                    person.setBirthday(et_birthday.getText().toString());
                    person.setBeijing(beijing);
                    person.setDenglu(login);
                    person.setPlan(null);
                    person.setRemind(null);
                    person.save();
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });
    }
}
