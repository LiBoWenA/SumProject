package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndentShopItemAdapter extends RecyclerView.Adapter<IndentShopItemAdapter.ViewHolder> {

    private Context context;
    private List<IndentShopBean.OrderListBean.DetailListBean> list;

    public IndentShopItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<IndentShopBean.OrderListBean.DetailListBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IndentShopItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopavtivity_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IndentShopItemAdapter.ViewHolder viewHolder, int i) {
        String[] split = list.get(i).getCommodityPic().split(",");
        Glide.with(context).load(split[0]).into(viewHolder.shopImg);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText("¥："+list.get(i).getCommodityPrice());
        viewHolder.tCount.setText("*"+list.get(i).getCommodityCount());

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
