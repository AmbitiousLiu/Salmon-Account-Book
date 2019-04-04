package com.example.salmonaccountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static String username;

    private TextView tv_register;
    private TextView tv_forget;
    private Button btn_login;

    private EditText et_name_login;
    private EditText et_password_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        tv_register = findViewById(R.id.tv_register);
        tv_forget = findViewById(R.id.tv_forget);
        btn_login = findViewById(R.id.btn_login);

        et_name_login = findViewById(R.id.et_name_login);
        et_password_login = findViewById(R.id.et_password_login);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> people = DataSupport.where("username = ?", et_name_login.getText().toString()).find(Person.class);
                if (people.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "用户不存在！", Toast.LENGTH_LONG).show();
                    et_name_login.setText("");
                    et_password_login.setText("");
                } else {
                    if (people.get(0).getPassword().toString().equals(et_password_login.getText().toString())) {
                        username = et_name_login.getText().toString();
                        Person person = new Person();
                        person.setDenglu("yes");
                        person.updateAll("username = ?", username);
                        onBackPressed();
                    } else {
                        Toast.makeText(LoginActivity.this, "密码不正确！", Toast.LENGTH_LONG).show();
                        et_password_login.setText("");
                    }
                }
            }
        });
    }
    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            MainActivity.instance_main.finish();
            onBackPressed();
        }
        return false;
    }

}
