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

public class NewDesignAdapter extends RecyclerView.Adapter<NewDesignAdapter.ViewHolder> {

    private Context context;
    private List<HomeBean.ResultBean.RxxpBean.CommodityListBean> list;

    public NewDesignAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<HomeBean.ResultBean.RxxpBean.CommodityListBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewDesignAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepage_new_design,null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewDesignAdapter.ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.imageView);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText("¥："+list.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.new_design_image_phone)
        ImageView imageView;
        @BindView(R.id.new_design_title)
        TextView tTitle;
        @BindView(R.id.new_design_price)
        TextView tPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
