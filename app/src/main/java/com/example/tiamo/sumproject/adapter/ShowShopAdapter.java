package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowShopAdapter extends RecyclerView.Adapter<ShowShopAdapter.ViewHolder> {
    private Context context;
    private List<SelectShopBean.ResultBean> list;
    private double price;

    public ShowShopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setDate(List<SelectShopBean.ResultBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopavtivity_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowShopAdapter.ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getPic()).into(viewHolder.shopImg);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText(list.get(i).getPrice()+"");
        viewHolder.tCount.setText("*"+list.get(i).getCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shop_item_img)
        ImageView shopImg;
        @BindView(R.id.shop_item_title)
        TextView tTitle;
        @BindView(R.id.shop_item_price)
        TextView tPrice;
        @BindView(R.id.goods_count)
        TextView tCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
