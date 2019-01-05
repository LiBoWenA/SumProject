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
import com.example.tiamo.sumproject.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.ViewHolder> {
    private Context context;
    private List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> list;

    public LifeAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> lists){
        list = lists;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LifeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepage_life,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LifeAdapter.ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.imageView);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText("¥："+list.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.life_img)
        ImageView imageView;
        @BindView(R.id.life_title)
        TextView tTitle;
        @BindView(R.id.life_price)
        TextView tPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
