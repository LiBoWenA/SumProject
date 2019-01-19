package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndentShopIndentAdapter extends RecyclerView.Adapter<IndentShopIndentAdapter.ViewHolder> {
    private Context context;
    private List<IndentShopBean.OrderListBean.DetailListBean> list;

    public IndentShopIndentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<IndentShopBean.OrderListBean.DetailListBean> lists){
        list = lists;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public IndentShopIndentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.indentfragment_items_name,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IndentShopIndentAdapter.ViewHolder viewHolder, final int i) {
        String[] split = list.get(i).getCommodityPic().split(",");
        Glide.with(context).load(split[0]).into(viewHolder.shopImg);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText("¥："+list.get(i).getCommodityPrice());
        viewHolder.btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null){
                    onClick.Click(list.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_child_image)
        ImageView shopImg;
        @BindView(R.id.item_child_name)
        TextView tTitle;
        @BindView(R.id.item_child_price)
        TextView tPrice;
        @BindView(R.id.item_but)
        Button btnEvaluate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    OnClick onClick;

    public void setOnBtnClick(OnClick click){
        onClick = click;
    }

    public interface OnClick{
        void Click(int id);
    }
}
