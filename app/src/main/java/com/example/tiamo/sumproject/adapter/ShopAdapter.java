package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;
import com.example.tiamo.sumproject.view.ShopCustomView.ShopCustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context context;
    private List<SelectShopBean.ResultBean> list;

    public ShopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<SelectShopBean.ResultBean> lists){
        list = lists;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopfragment_shopitem,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter.ViewHolder viewHolder, final int i) {
        Glide.with(context).load(list.get(i).getPic()).into(viewHolder.shopImg);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText(list.get(i).getPrice()+"");
        viewHolder.shopItemCk.setChecked(list.get(i).isCheck());
        viewHolder.shopItemCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setCheck(isChecked);
                if (isChecked){
                    if (shopLisener!= null){
                        shopLisener.lisener(list);
                    }
                }else{
                    if (shopLisener!= null){
                        shopLisener.lisener(list);
                    }
                }
            }
        });
        viewHolder.shopCustomView.setData(this,list,i);
        viewHolder.shopCustomView.setOnCallBack(new ShopCustomView.CallBackListener() {
            @Override
            public void callBack() {
                if (shopLisener != null){
                    shopLisener.lisener(list);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_item_ck)
        CheckBox shopItemCk;
        @BindView(R.id.shop_item_img)
        ImageView shopImg;
        @BindView(R.id.shop_item_title)
        TextView tTitle;
        @BindView(R.id.shop_item_price)
        TextView tPrice;
        @BindView(R.id.shop_custom_view)
        ShopCustomView shopCustomView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    ShopLisener shopLisener;

    public void setShopLisener(ShopLisener lisener){
        shopLisener = lisener;
    }

    public interface ShopLisener{
        void lisener(List<SelectShopBean.ResultBean> list);
    }
}
