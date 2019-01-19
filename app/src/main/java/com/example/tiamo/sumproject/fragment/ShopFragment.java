package com.example.tiamo.sumproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.activity.shopfragmentactivity.ShopActivity;
import com.example.tiamo.sumproject.adapter.ShopAdapter;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopFragment extends Fragment implements IView {
    IPersenterImpl iPersenter;
    @BindView(R.id.shop_rv)
    RecyclerView shopRecyclerView;
    private ShopAdapter adapter;
    @BindView(R.id.ck_chose)
    CheckBox sun_ck;
    @BindView(R.id.shop_price)
    TextView tPrice;
    @BindView(R.id.close_price)
    Button closePriceBtn;
    private SelectShopBean selectShopBean;
    private List<SelectShopBean.ResultBean> result;
    private double price;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopfragment,null);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //查询购物车
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.SELECT_SHOP,SelectShopBean.class);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        shopRecyclerView.setLayoutManager(manager);
        adapter = new ShopAdapter(getActivity());
        shopRecyclerView.setAdapter(adapter);
        adapter.setShopLisener(new ShopAdapter.ShopLisener() {
            @Override
            public void lisener(List<SelectShopBean.ResultBean> list) {
                //接受返回来的以改变状态值的集合
                //求价格
                price = 0;
                //总数
                int num = 0;
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).isCheck()){
                        price += (list.get(i).getPrice()*list.get(i).getCount());
                        int commodityId = list.get(i).getCommodityId();
                        num++;
                    }
                }
                if (num == list.size()){
                    sun_ck.setChecked(true);
                }else{
                    sun_ck.setChecked(false);
                }
                tPrice.setText("¥："+ price);
            }
        });

    }

    /*@Override
    public void onPause() {
        super.onPause();
        iPersenter.showRequestData(UrlApis.SELECT_SHOP,SelectShopBean.class);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        iPersenter.showRequestData(UrlApis.SELECT_SHOP,SelectShopBean.class);

    }

    @OnClick({R.id.ck_chose,R.id.close_price})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ck_chose:
                price = 0;
                for (int i = 0; i < result.size(); i++) {
                    result.get(i).setCheck(sun_ck.isChecked());
                }
                adapter.setData(result);
                adapter.notifyDataSetChanged();
                break;
            case R.id.close_price:
                //TUDO:点击去结算进行跳转  带参是为选中状态的商品
                List<SelectShopBean.ResultBean> listShop = new ArrayList<>();
                for (int i = 0; i < selectShopBean.getResult().size(); i++) {
                    if (selectShopBean.getResult().get(i).isCheck()){
                        listShop.add(new SelectShopBean.ResultBean(selectShopBean.getResult().get(i).getCommodityId(),
                                selectShopBean.getResult().get(i).getCommodityName(),
                                selectShopBean.getResult().get(i).getPic(),
                                selectShopBean.getResult().get(i).getPrice(),
                                selectShopBean.getResult().get(i).getCount(),
                                selectShopBean.getResult().get(i).isCheck()
                        ));
                    }
                }
                if (listShop.size() == 0){
                    Toast.makeText(getActivity(),"请选择要购买的商品哦~",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra("list", (Serializable) listShop);
                    startActivity(intent);
                }
                break;
                default:
                    break;

        }
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof SelectShopBean){
            selectShopBean = (SelectShopBean) data;
            if (selectShopBean.getResult().size() == 0){
                Toast.makeText(getActivity(),"还没有心仪的商品哦~",Toast.LENGTH_SHORT).show();
            }else {
                result = selectShopBean.getResult();
                adapter.setData(selectShopBean.getResult());
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersenter.onDestory();
        bind.unbind();
    }
}
