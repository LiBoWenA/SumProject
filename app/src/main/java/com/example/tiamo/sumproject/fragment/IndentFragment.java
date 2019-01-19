package com.example.tiamo.sumproject.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.activity.indentfragment_activity.EvaluateActivity;
import com.example.tiamo.sumproject.activity.indentfragment_activity.IndentFragmentActivity;
import com.example.tiamo.sumproject.adapter.IndentShopAdapter;
import com.example.tiamo.sumproject.bean.EventBean;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentShopBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class IndentFragment extends Fragment implements IView {
    @BindView(R.id.indent_sunindent)
    LinearLayout layoutSumIndent;
    @BindView(R.id.indent_payment)
    LinearLayout layoutPayment;
    @BindView(R.id.indent_waitcargo)
    LinearLayout layoutCargo;
    @BindView(R.id.indent_evaluate)
    LinearLayout layoutEvaluate;
    @BindView(R.id.indent_sucess)
    LinearLayout layoutSucess;
    @BindView(R.id.indent_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.text_sum)
    TextView tSum;
    @BindView(R.id.text_price)
    TextView tPrice;
    @BindView(R.id.text_goods)
    TextView tGoods;
    @BindView(R.id.text_name)
    TextView tName;
    @BindView(R.id.text_sucess)
    TextView tSucess;
    private int page;
    private int SELECTSUM;
    private IPersenterImpl iPersenter;
    private Unbinder bind;
    private IndentShopAdapter indentShopAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.indentfragment,null);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iPersenter = new IPersenterImpl(this);
        EventBus.getDefault().register(this);
        page = 1;
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPost(EventBean bean) {
        if (bean.getId() == 66) {
            SELECTSUM = 1;
            iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND, SELECTSUM, page), IndentShopBean.class);
        } else if (bean.getId() == 77) {
            SELECTSUM = 3;
            iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND, SELECTSUM, page), IndentShopBean.class);
        }
    }

    //布局管理者
    private void init() {
        SELECTSUM = 0;
        tSum.setTextColor(Color.RED);
        tPrice.setTextColor(Color.BLACK);
        tGoods.setTextColor(Color.BLACK);
        tName.setTextColor(Color.BLACK);
        tSucess.setTextColor(Color.BLACK);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        indentShopAdapter = new IndentShopAdapter(getActivity());
        recyclerView.setAdapter(indentShopAdapter);

        //点击适配器进行支付
        indentShopAdapter.setOnClick(new IndentShopAdapter.OnClick() {
            @Override
            public void Click(String id, double price,int num) {
                    Intent intent = new Intent(getActivity(),IndentFragmentActivity.class);
                    intent.putExtra("orderId",id);
                    intent.putExtra("price",price);
                    intent.putExtra("num",num);
                    startActivity(intent);
                    //Toast.makeText(getActivity(),"已支付，请去待收货页面查看",Toast.LENGTH_SHORT).show();
            }
        });
        //收货
        indentShopAdapter.setOnBtnClick(new IndentShopAdapter.OnPayClick() {
            @Override
            public void Click(String id,int num) {
                SELECTSUM = num;
                Map<String,String> map = new HashMap<>();
                map.put("orderId",id);
                iPersenter.putShowRequestData(UrlApis.TAKE_GOODS,map,IndentBean.class);
                    //Toast.makeText(getActivity(),"已收货，请去带评论页面查看",Toast.LENGTH_SHORT).show();
            }
        });
        //点击待评价
        indentShopAdapter.setOnEvaluateClick(new IndentShopAdapter.OnEvaluateClick() {
            @Override
            public void Click(int id,String orderid,int num) {
                    Intent intent = new Intent(getActivity(),EvaluateActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("orid",orderid);
                    startActivity(intent);

                    //Toast.makeText(getActivity(),"已评价，请去完成页面查看",Toast.LENGTH_SHORT).show();
            }
        });
        iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
    }


    @OnClick({R.id.indent_sunindent,R.id.indent_payment,R.id.indent_waitcargo,R.id.indent_evaluate,R.id.indent_sucess})
    public void setOnClick(View v){
        page = 1;
        switch (v.getId()){
            case R.id.indent_sunindent:
                SELECTSUM = 0;
                tSum.setTextColor(Color.RED);
                tPrice.setTextColor(Color.BLACK);
                tGoods.setTextColor(Color.BLACK);
                tName.setTextColor(Color.BLACK);
                tSucess.setTextColor(Color.BLACK);
                //点击全部订单进行查询
                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
                break;
            case R.id.indent_payment:
                SELECTSUM = 1;
                tSum.setTextColor(Color.BLACK);
                tPrice.setTextColor(Color.RED);
                tGoods.setTextColor(Color.BLACK);
                tName.setTextColor(Color.BLACK);
                tSucess.setTextColor(Color.BLACK);
                //点击查看待付款
                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
                break;
            case R.id.indent_waitcargo:
                SELECTSUM = 2;
                tSum.setTextColor(Color.BLACK);
                tPrice.setTextColor(Color.BLACK);
                tGoods.setTextColor(Color.RED);
                tName.setTextColor(Color.BLACK);
                tSucess.setTextColor(Color.BLACK);
                //点击查看待收货
                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
                break;
            case R.id.indent_evaluate:
                SELECTSUM = 3;
                tSum.setTextColor(Color.BLACK);
                tPrice.setTextColor(Color.BLACK);
                tGoods.setTextColor(Color.BLACK);
                tName.setTextColor(Color.RED);
                tSucess.setTextColor(Color.BLACK);
                //点击查看待评价
                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
                break;
            case R.id.indent_sucess:
                SELECTSUM = 9;
                tSum.setTextColor(Color.BLACK);
                tPrice.setTextColor(Color.BLACK);
                tGoods.setTextColor(Color.BLACK);
                tName.setTextColor(Color.BLACK);
                tSucess.setTextColor(Color.RED);
                //点击查看已完成
                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
                break;
                default:
                    break;

        }
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof IndentShopBean){
            IndentShopBean bean = (IndentShopBean) data;
            for (int i = 0; i < bean.getOrderList().size(); i++) {
                Log.i("TAGG",bean.getOrderList().get(i).getOrderId()+"...");
            }
            indentShopAdapter.setData(bean.getOrderList());
           /* if (page == 1) {

            }else{
                indentShopAdapter.addData(bean.getOrderList());
            }
            page++;
            recyclerView.loadMoreComplete();
            recyclerView.refreshComplete();*/
        }else if (data instanceof IndentBean){
            IndentBean bean = (IndentBean) data;
            if (bean.getStatus().equals("0000")){
                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,SELECTSUM,page),IndentShopBean.class);
                /*Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();*/
            }else{
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iPersenter != null) {
            iPersenter.onDestory();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        bind.unbind();
    }
}
