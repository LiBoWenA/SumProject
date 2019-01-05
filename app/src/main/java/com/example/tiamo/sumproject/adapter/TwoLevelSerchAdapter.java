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
import com.example.tiamo.sumproject.bean.TwoLevelSerchBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwoLevelSerchAdapter extends RecyclerView.Adapter<TwoLevelSerchAdapter.ViewHolder> {
    private Context context;
    private List<TwoLevelSerchBean.ResultBean> list;

    public TwoLevelSerchAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<TwoLevelSerchBean.ResultBean> lists){
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void addData(List<TwoLevelSerchBean.ResultBean> lists){
        list.addAll(lists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TwoLevelSerchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_shop_new_design,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TwoLevelSerchAdapter.ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.imageView);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText("¥："+list.get(i).getPrice());
        viewHolder.tNum.setText("已销售"+list.get(i).getSaleNum()+"件");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_new_design_img)
        ImageView imageView;
        @BindView(R.id.home_new_design_title)
        TextView tTitle;
        @BindView(R.id.home_new_design_price)
        TextView tPrice;
        @BindView(R.id.home_new_design_num)
        TextView tNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
