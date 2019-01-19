package com.example.tiamo.sumproject.activity.shopfragmentactivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.PopAdapter;
import com.example.tiamo.sumproject.adapter.ShowShopAdapter;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.CreatShopBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.ShopAddressBean;
import com.example.tiamo.sumproject.fragment.IndentFragment;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongToIntFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShopActivity extends AppCompatActivity implements IView {

    @BindView(R.id.address_drop_down)
    ImageView imgDrop;
    @BindView(R.id.goods_address_name)
    TextView tName;
    @BindView(R.id.goods_address_phone)
    TextView tPhone;
    @BindView(R.id.goods_address)
    TextView tAddress;
    @BindView(R.id.show_goods)
    RecyclerView showRecyclerView;
    @BindView(R.id.address_describe_num)
    TextView tDescribe;
    @BindView(R.id.addre)
    RelativeLayout showAdd;
    @BindView(R.id.addre_no)
    RelativeLayout hideAdd;
    @BindView(R.id.address_describe_price)
    TextView tDescribePrice;
    @BindView(R.id.pop_show)
    RelativeLayout relativeLayoutPop;
    private RecyclerView popRv;
    IPersenterImpl iPersenter;
    private ShowShopAdapter adapter;
    private int num = 0;
    private boolean isShow;
    private double goodsPrice = 0.00;
    private ShopAddressBean shopAddressBean;
    private String json;
    private List<SelectShopBean.ResultBean> list;
    private PopAdapter popAdapter;
    private PopupWindow popupWindow;
    private Unbinder bind;
    @BindView(R.id.btn_commit_goods)
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.ADDRESS_GOODS,ShopAddressBean.class);
        //得到传递的商品序列化
        Intent intent = getIntent();
        list = (List<SelectShopBean.ResultBean>) intent.getSerializableExtra("list");
        popupWindow = new PopupWindow();
        //展示商品内容（查询购物车）
        showGoods();
        toastPop();
        initPopRecy();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopAddressBean.getResult().size() == 0){
                    Toast.makeText(ShopActivity.this,"请先添加收货地址，在提交订单哦~",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                //当点击提交订单进行操作
                List<CreatShopBean> listCreat = new ArrayList<>();
                for (SelectShopBean.ResultBean bean:list) {
                    CreatShopBean creatShopBean = new CreatShopBean();
                    creatShopBean.setAmount(bean.getCount());
                    creatShopBean.setCommodityId(bean.getCommodityId());
                    listCreat.add(creatShopBean);
                }
                String json = new Gson().toJson(listCreat);
                Map<String,String> map = new HashMap<>();
                map.put("orderInfo",json);
                map.put("totalPrice",goodsPrice+"");
                map.put("addressId",shopAddressBean.getResult().get(0).getId()+"");
                //发送请求
                iPersenter.showRequestData(UrlApis.CREAT_INDENT,map,IndentBean.class);
            }
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
        tDescribe.setText(num+"");
        tDescribePrice.setText(goodsPrice+"");
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof ShopAddressBean){
            //查询地址
            shopAddressBean = (ShopAddressBean) data;
            if (shopAddressBean.getMessage().equals("你还没有收货地址，快去添加吧")){
                showAdd.setVisibility(View.GONE);
                hideAdd.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                Toast.makeText(this,shopAddressBean.getMessage().toString(),Toast.LENGTH_SHORT).show();

            }else {
                //赋值
                List<ShopAddressBean.ResultBean> result = shopAddressBean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getWhetherDefault() == 1){
                        tName.setText(shopAddressBean.getResult().get(0).getRealName());
                        tPhone.setText(shopAddressBean.getResult().get(0).getPhone());
                        tAddress.setText(shopAddressBean.getResult().get(0).getAddress());
                    }
                }

            }
            popAdapter.setData(shopAddressBean.getResult());
        }else if (data instanceof IndentBean){
            IndentBean bean = (IndentBean) data;
            Toast.makeText(this,bean.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    //点击弹出pop
    private void toastPop() {
            // 用于PopupWindow的View
            View contentView=LayoutInflater.from(this).inflate(R.layout.shopactivitypop, null, false);
            popRv = contentView.findViewById(R.id.pop_rv);
            // 创建PopupWindow对象，其中：
            // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
            // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
       /* // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);*/
            // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            //点击弹出
            imgDrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    popupWindow.showAsDropDown(v,0,26);
                    // image_pop.setBackgroundResource(R.mipmap.down);

                    //popupWindow.dismiss();
                    //image_pop.setBackgroundResource(R.mipmap.up);

                    imgDrop.setSelected(!imgDrop.isSelected());
                    //flag=!flag;
                }
            });
            popupWindow.dismiss();

    }

    private void initPopRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        popRv.setLayoutManager(linearLayoutManager);
        popAdapter = new PopAdapter(this);
        popRv.setAdapter(popAdapter);
        popAdapter.setOnAddrClick(new PopAdapter.onAddrClick() {
            @Override
            public void onClick(String name, String phone, String addr) {
                tName.setText(name);
                tPhone.setText(phone+"");
                tAddress.setText(addr);
            }
        });
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
