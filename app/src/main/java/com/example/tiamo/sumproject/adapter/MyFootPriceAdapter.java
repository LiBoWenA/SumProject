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
import com.example.tiamo.sumproject.bean.myfragmentbean.FootPriceBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFootPriceAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private List<FootPriceBean.ResultBean> mlist;
    private Context context;

    public MyFootPriceAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setMlist(List<FootPriceBean.ResultBean> list) {
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void addMlist(List<FootPriceBean.ResultBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_footprice_mine, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        holder.item_foortp_name.setText(mlist.get(i).getCommodityName());
        holder.item_foortp_peice.setText("￥"+mlist.get(i).getPrice()+"");
        String times = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(mlist.get(i).getBrowseTime()));
        holder.item_footp_time.setText(times);
        holder.item_foortp_bro.setText("已浏览"+mlist.get(i).getBrowseNum()+"次");
        Glide.with(context).load(mlist.get(i).getMasterPic()).into(holder.item_foortp_image);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.foot_imageView)
        ImageView item_foortp_image;
        @BindView(R.id.foot_name)
        TextView item_foortp_name;
        @BindView(R.id.foot_price)
        TextView item_foortp_peice;
        @BindView(R.id.foot_llcs)
        TextView item_foortp_bro;
        @BindView(R.id.foot_llsj)
        TextView item_footp_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
