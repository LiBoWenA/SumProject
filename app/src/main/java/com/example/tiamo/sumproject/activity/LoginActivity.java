package com.example.tiamo.sumproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.MainActivity;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.LoginBean;
import com.example.tiamo.sumproject.bean.MainBean;
import com.example.tiamo.sumproject.persenter.IPersenter;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements IView {

    EditText loginEdPhone,loginEdCode,loginEdPwd;
    Button loginBtn;
    TextView intentBtn;
    IPersenterImpl iPersenter;
    private String path = "http://172.17.8.100/small/user/v1/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取资源ID
        init();

    }

    private void init() {
        iPersenter = new IPersenterImpl(this);
        //edtext框的资源ID
        loginEdPhone = findViewById(R.id.login_phone);
        loginEdPwd = findViewById(R.id.login_pwd);
        loginEdCode = findViewById(R.id.login_code);
        //获取需要点击按钮的id
        loginBtn = findViewById(R.id.login_but);
        intentBtn = findViewById(R.id.btn_login);
        //点击按钮进行注册
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginEdCode.getText().toString().equals("")&&loginEdCode.getText().toString().length()<4) {
                    Toast.makeText(LoginActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                }else{
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", loginEdPhone.getText().toString());
                    map.put("pwd", loginEdPwd.getText().toString());
                    iPersenter.showRequestData(path, map, LoginBean.class);
                }
            }
        });
        //点击已有登录进行跳转
        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof LoginBean){
            LoginBean bean = (LoginBean) data;
            //判断是否注册成功，成功跳转
            if (bean.getStatus().equals("0000")){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,bean.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
