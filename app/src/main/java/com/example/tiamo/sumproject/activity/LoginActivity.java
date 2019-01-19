package com.example.tiamo.sumproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.MainActivity;
import com.example.tiamo.sumproject.MyApp;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.LoginBean;
import com.example.tiamo.sumproject.bean.MainBean;
import com.example.tiamo.sumproject.persenter.IPersenter;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements IView {

    @BindView(R.id.login_phone)
    EditText loginEdPhone;
    @BindView(R.id.login_pwd)
    EditText loginEdPwd;
    @BindView(R.id.login_code)
    EditText loginEdCode;
    @BindView(R.id.login_but)
    Button loginBtn;
    @BindView(R.id.btn_login)
    TextView intentBtn;
    @BindView(R.id.sign_eye)
    ImageView imgEye;

    IPersenterImpl iPersenter;
    private String path = "user/v1/register";
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ButterKnife.bind(this);
        //获取资源ID
        init();

    }

    private void init() {
        iPersenter = new IPersenterImpl(this);
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
        //点击小眼睛显示密码
        //点击小眼睛进行显示密码与否
        imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击进行密码的显示隐藏
                if (imgEye == null || imgEye == null) {
                    return;
                }
                if (imgEye.isSelected()) {
                    imgEye.setSelected(false);
                    loginEdPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    imgEye.setSelected(true);
                    loginEdPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof LoginBean) {
            LoginBean bean = (LoginBean) data;
            //判断是否注册成功，成功跳转
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(this, bean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, bean.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPersenter != null) {
            iPersenter.onDestory();
        }
        bind.unbind();
    }
}
