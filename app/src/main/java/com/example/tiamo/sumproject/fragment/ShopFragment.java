package com.example.tiamo.sumproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.ShopAdapter;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopFragment extends Fragment implements IView {
    IPersenterImpl iPersenter;
    @BindView(R.id.shop_rv)
    RecyclerView shopRecyclerView;
    private ShopAdapter adapter;
    @BindView(R.id.ck_chose)
    CheckBox sun_ck;
    @BindView(R.id.shop_price)
    TextView tPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopfragment,null);
        ButterKnife.bind(this,view);
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
                double price = 0;
                //总数
                int num = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isCheck()){
                        price += (list.get(i).getPrice()*list.get(i).getCount());
                    }
                }
                tPrice.setText("¥："+price);
            }
        });
        init();
    }

    private void init() {
        //如果点击了全选按钮
        sun_ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //如果为选中

                }
            }
        });
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof SelectShopBean){
            SelectShopBean bean = (SelectShopBean) data;
            adapter.setData(bean.getResult());
        }

    }
}
