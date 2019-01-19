package com.example.tiamo.sumproject.activity.indentfragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.bean.EventBean;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.myfragmentbean.MoneyBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentFragmentActivity extends AppCompatActivity implements IView {

    @BindView(R.id.money_pay)
    Button money_pay;
    @BindView(R.id.pay_success)
    RelativeLayout pay_success;
    @BindView(R.id.pay_fail)
    RelativeLayout pay_fail;
    private IPersenterImpl iPersenter;
    private String id;
    private int type;
    private String orderId;
    private double price;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_fragment);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        price = intent.getDoubleExtra("price", 0.00);
        num = intent.getIntExtra("num", 0);

    }

    @OnClick({R.id.radio_yue,R.id.radio_wei,R.id.radio_zhi,R.id.money_pay,R.id.success_back,R.id.fail_pay})
    public void setOnClick(View v){
        switch (v.getId()){
            case R.id.radio_yue:
                type=1;
                money_pay.setText("余额支付"+price+"元");
                break;
            case R.id.radio_wei:
                type=2;
                money_pay.setText("微信支付"+price+"元");
                break;
            case R.id.radio_zhi:
                type=3;
                money_pay.setText("支付宝支付"+price+"元");
                break;
            case R.id.money_pay:
                //去支付
                Map<String,String> map = new HashMap<>();
                map.put("orderId",orderId);
                map.put("payType",type+"");
                iPersenter.showRequestData(UrlApis.PAY_SHOP,map,IndentBean.class);
                break;
            case R.id.success_back:
                EventBus.getDefault().post(new EventBean(66));
                finish();
                break;
            case R.id.fail_pay:
                pay_success.setVisibility(View.GONE);
                pay_fail.setVisibility(View.GONE);
                break;
            default:break;
        }
    }

    @Override
    public void startRequestData(Object data) {
        if(data instanceof IndentBean){
            IndentBean myAddBean= (IndentBean) data;
            if(myAddBean.getMessage().equals("支付成功")){
                // Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                pay_success.setVisibility(View.VISIBLE);
                pay_fail.setVisibility(View.GONE);
            }else{
                //Toast.makeText(this, "不支持的支付方式", Toast.LENGTH_SHORT).show();
                pay_success.setVisibility(View.GONE);
                pay_fail.setVisibility(View.VISIBLE);
            }
        }
    }
}
