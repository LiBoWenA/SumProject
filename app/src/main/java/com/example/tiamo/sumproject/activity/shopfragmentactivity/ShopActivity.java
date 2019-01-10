package com.example.tiamo.sumproject.activity.shopfragmentactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.ShowShopAdapter;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.CreatShopBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.ShopAddressBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends AppCompatActivity implements IView  {

    @BindView(R.id.btn_commit_goods)
    Button btnCommit;
    @BindView(R.id.goods_address_name)
    TextView tName;
    @BindView(R.id.goods_address_phone)
    TextView tPhone;
    @BindView(R.id.goods_address)
    TextView tAddress;
    @BindView(R.id.show_goods)
    RecyclerView showRecyclerView;
    @BindView(R.id.address_describe)
    TextView tDescribe;
    @BindView(R.id.addre)
    RelativeLayout showAdd;
    @BindView(R.id.addre_no)
    RelativeLayout hideAdd;
    IPersenterImpl iPersenter;
    private ShowShopAdapter adapter;
    private int num = 0;
    private double goodsPrice = 0.00;
    private ShopAddressBean shopAddressBean;
    private String json;
    private List<SelectShopBean.ResultBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.ADDRESS_GOODS,ShopAddressBean.class);
        //得到传递的商品序列化
        Intent intent = getIntent();
        list = (List<SelectShopBean.ResultBean>) intent.getSerializableExtra("list");
        //展示商品内容（查询购物车）
        showGoods();
        //提交订单
        commitGoods();

    }

    //提交订单
    private void commitGoods() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击提交订单进行操作
                List<CreatShopBean> listCreat = new ArrayList<>();
                for (SelectShopBean.ResultBean bean:list) {
                    CreatShopBean creatShopBean = new CreatShopBean();
                    creatShopBean.setCount(bean.getCount());
                    creatShopBean.setCommodityId(bean.getCommodityId());
                    listCreat.add(creatShopBean);
                }
                String json = new Gson().toJson(listCreat);
                Map<String,String> map = new HashMap<>();
                map.put("orderInfo",json);
                map.put("totalPrice",goodsPrice+"");
                map.put("addressId",shopAddressBean.getResult().get(0).getId()+"");

                Log.i("ADDRESS",map.toString()+"map里面的数据");
                //发送请求
                iPersenter.showRequestData(UrlApis.CREAT_INDENT,map,IndentBean.class);
            }
        });
    }

    //展示商品内容
    private void showGoods() {
        //布局管理者
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        showRecyclerView.setLayoutManager(manager);
        //添加适配器
        adapter = new ShowShopAdapter(this);
        showRecyclerView.setAdapter(adapter);
        //获取跳转过来的集合
        adapter.setDate(list);
        for (int i = 0; i < list.size(); i++) {
            //获取携带的数量
            int count = list.get(i).getCount();
            //数量累加
            num += count;
            //计算价格
            goodsPrice += list.get(i).getPrice()*list.get(i).getCount();
        }
        //底部商品描述
        tDescribe.setText("共"+num+"件商品，需支付"+goodsPrice+"元");
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof ShopAddressBean){
            //查询地址
            shopAddressBean = (ShopAddressBean) data;
            //判断是否有地址
            if (shopAddressBean.getResult().size() == 0){
                //没有的话提示用户添加地址
                hideAdd.setVisibility(View.VISIBLE);
                showAdd.setVisibility(View.GONE);
            }else {
                //赋值
                tName.setText(shopAddressBean.getResult().get(0).getRealName());
                tPhone.setText(shopAddressBean.getResult().get(0).getPhone());
                tAddress.setText(shopAddressBean.getResult().get(0).getAddress());
                Log.i("ADDRESS",shopAddressBean.getResult().get(0).getId()+"");
            }
        }else if (data instanceof IndentBean){
            //判断订单的状态
            IndentBean bean = (IndentBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(ShopActivity.this,bean.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }else{
                Log.i("ADDRESS",bean.getMessage().toString());
                Toast.makeText(this, "创建失败", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
