package com.example.tiamo.sumproject.activity.myfragment_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.MainActivity;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.myfragmentbean.MyBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人资料activity
 * @author TiAmo
 */
public class PersonActivity extends AppCompatActivity implements IView {

    IPersenterImpl iPersenter;
    private Unbinder bind;
    @BindView(R.id.name_text_user)
    EditText tName;
    @BindView(R.id.text_pass)
    EditText tPass;
    @BindView(R.id.img_head)
    ImageView imgHead;
    private AlertDialog.Builder updataName,updataPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.SELECT_USER,MyBean.class);
    }

    //修改文字或者密码
    @OnClick({R.id.name_text_user,R.id.text_pass})
    public void setUpdata(View v){
        switch (v.getId()){
            case R.id.name_text_user:
                updataName = new AlertDialog.Builder(this);
                View updata_name = View.inflate(this, R.layout.my_alertdialog, null);
                final EditText newName = updata_name.findViewById(R.id.updata_name);
                updataName.setView(updata_name);
                updataName.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                updataName.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = newName.getText().toString();
                        if (name.equals("")) {
                            Toast.makeText(PersonActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("nickName", name);
                            iPersenter.putShowRequestData(UrlApis.UPDATE_USER_NAME,map,IndentBean.class);
                        }
                    }
                });
                updataName.show();
                break;
            case R.id.text_pass:
                updataPass = new AlertDialog.Builder(this);
                View updata_pass = View.inflate(this, R.layout.my_alertdialog_pass, null);
                //得到新老密码
                final EditText edOldPass = updata_pass.findViewById(R.id.old_pass);
                final EditText edNewPass = updata_pass.findViewById(R.id.new_pass);
                updataPass.setView(updata_pass);
                updataPass.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                updataPass.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPass = edOldPass.getText().toString().trim();
                        String newPass = edNewPass.getText().toString().trim();
                        if (oldPass.equals("")&&newPass.equals("")) {
                            Toast.makeText(PersonActivity.this, "新旧密码不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("oldPwd", oldPass);
                            map.put("newPwd",newPass);
                            iPersenter.putShowRequestData(UrlApis.UPDATE_USER_PASS,map,IndentBean.class);
                        }
                    }
                });
                updataPass.show();
                break;
                default:
                    break;
        }
    }


    @Override
    public void startRequestData(Object data) {
        if (data instanceof MyBean){
            MyBean myBean = (MyBean) data;
            tName.setText(myBean.getResult().getNickName());
            tPass.setText(myBean.getResult().getPassword());
            Glide.with(PersonActivity.this).load(myBean.getResult().getHeadPic()).into(imgHead);

        }
        if (data instanceof IndentBean){
            //修改密码
            IndentBean bean = (IndentBean) data;
            iPersenter.showRequestData(UrlApis.SELECT_USER,MyBean.class);
            if (bean.getMessage().equals("修改成功")){
                Toast.makeText(PersonActivity.this,"修改成功~",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(PersonActivity.this,"请重新修改~",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPersenter != null) {
            iPersenter.onDestory();
        }
        bind.unbind();
    }

}
