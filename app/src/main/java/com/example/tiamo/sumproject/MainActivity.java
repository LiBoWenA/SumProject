package com.example.tiamo.sumproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.activity.IntentActivity;
import com.example.tiamo.sumproject.activity.LoginActivity;
import com.example.tiamo.sumproject.bean.MainBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView{

    private String path = "http://172.17.8.100/small/user/v1/login";
    Button loginButton;
    TextView registertext;
    ImageView imageView;
    EditText edPhone,edPwd;
    IPersenterImpl iPersenter;
    CheckBox ck_pwd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //获取资源ID
        init();
    }

    private void init() {
        iPersenter = new IPersenterImpl(this);
        ck_pwd = findViewById(R.id.check_pwd);
        loginButton = findViewById(R.id.btn_intent_activity);
        registertext = findViewById(R.id.main_register);
        imageView = findViewById(R.id.show_hide_eye);
        edPhone = findViewById(R.id.main_phone);
        edPwd = findViewById(R.id.main_pwd);
        //点击复选框记住账号和密码
        ck_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edPhone.getText().toString();
                String pwd = edPwd.getText().toString();
                if (ck_pwd.isChecked()){
                    editor.putString("phone",phone);
                    editor.putString("pass",pwd);
                    editor.putBoolean("pwd_ck",true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
            }
        });
        //点击按钮进行跳转
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map = new HashMap<>();
                map.put("phone",edPhone.getText().toString());
                map.put("pwd",edPwd.getText().toString());
                iPersenter.showRequestData(path,map,MainBean.class);
            }
        });
        //点击注册进行跳转
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        //点击进行密码的显示隐藏
        if (imageView == null || imageView == null) {
            return;
        }
        if (imageView.isSelected()) {
            imageView.setSelected(false);
            edPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            imageView.setSelected(true);
            edPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }

        //点击记住密码
        boolean pwd_true = sharedPreferences.getBoolean("pwd_ck",false);
        if (pwd_true){
            ck_pwd.setChecked(true);
            String name = sharedPreferences.getString("phone", null);
            String pass = sharedPreferences.getString("pass", null);
            edPhone.setText(name);
            edPwd.setText(pass);
        }else{
            ck_pwd.setChecked(false);
        }
    }

    //解绑销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.onDestory();
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof MainBean){
            MainBean mainBean = (MainBean) data;
            //如果登录成功则进行跳转
            if (mainBean.getMessage().equals("登录成功")){
                Intent intent = new Intent(MainActivity.this,IntentActivity.class);
                intent.putExtra("userId",mainBean.getResult().getUserId());
                intent.putExtra("sessionId",mainBean.getResult().getSessionId());
                Log.i("TAG",mainBean.getResult().getUserId()+"......."+mainBean.getResult().getSessionId());
                startActivity(intent);
                finish();
            }else{
                //否则吐司错误消息
                Toast.makeText(MainActivity.this,mainBean.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
