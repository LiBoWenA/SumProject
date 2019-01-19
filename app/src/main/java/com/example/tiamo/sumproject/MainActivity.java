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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.activity.HomePageActivity;
import com.example.tiamo.sumproject.activity.LoginActivity;
import com.example.tiamo.sumproject.bean.MainBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IView{

    private String path = "user/v1/login";
    @BindView(R.id.btn_intent_activity)
    Button loginButton;
    @BindView(R.id.main_register)
    TextView registertext;
    @BindView(R.id.show_hide_eye)
    ImageView imageView;
    @BindView(R.id.main_phone)
    EditText edPhone;
    @BindView(R.id.main_pwd)
    EditText edPwd;
    IPersenterImpl iPersenter;
    @BindView(R.id.check_pwd)
    CheckBox ck_pwd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //获取资源ID
        init();
    }

    private void init() {

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
                String phone = edPhone.getText().toString();
                String pwd = edPwd.getText().toString();
                if(Validator.isPhoneValidator(phone)&&Validator.isPwdValidator(pwd)){
                    Map<String,String> map = new HashMap<>();
                    map.put("phone",edPhone.getText().toString());
                    map.put("pwd",edPwd.getText().toString());
                    iPersenter.showRequestData(path,map,MainBean.class);
                }else{
                    Toast.makeText(MainActivity.this,"账号或密码有误",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //点击小眼睛进行显示密码与否
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if (iPersenter != null) {
            iPersenter.onDestory();
        }
        bind.unbind();
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof MainBean) {
        MainBean mainBean = (MainBean) data;
        //如果登录成功则进行跳转
        if (mainBean.getMessage().equals("登录成功")) {
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            editor.putString("SessionId", mainBean.getResult().getSessionId());
            editor.putString("UserId", mainBean.getResult().getUserId() + "");
            Log.i("TAG+TAG", mainBean.getResult().getSessionId() + "++" + mainBean.getResult().getUserId());
            editor.commit();
            startActivity(intent);
            finish();
        } else {
            //否则吐司错误消息并清空
            editor.remove("SessionId");
            editor.remove("UserId");
            editor.commit();
            Toast.makeText(MainActivity.this, mainBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    }
}
