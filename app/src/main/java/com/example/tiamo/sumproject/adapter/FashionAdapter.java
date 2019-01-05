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

public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.ViewHolder> {
    private Context context;
    private List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> list;

    public FashionAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FashionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepage_fashion,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FashionAdapter.ViewHolder viewHolder, final int i) {
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.imageView);
        viewHolder.tTitle.setText(list.get(i).getCommodityName());
        viewHolder.tPrice.setText("¥："+list.get(i).getPrice());
        //点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.fashion_img)
        ImageView imageView;
        @BindView(R.id.fishion_title)
        TextView tTitle;
        @BindView(R.id.fishion_price)
        TextView tPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void Click(int commodityId);
    }
}
