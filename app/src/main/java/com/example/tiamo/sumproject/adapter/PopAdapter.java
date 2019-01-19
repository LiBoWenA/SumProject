package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.shopfragmentbean.ShopAddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {
    private Context context;
    private List<ShopAddressBean.ResultBean> list;

    public PopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<ShopAddressBean.ResultBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopactivity_popitem,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tName.setText(list.get(i).getRealName());
        viewHolder.tPhone.setText(list.get(i).getPhone()+"");
        viewHolder.tAddress.setText(list.get(i).getAddress());
        viewHolder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAddrClick != null){
                    onAddrClick.onClick(list.get(i).getRealName(),list.get(i).getPhone(),list.get(i).getAddress());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_pop_name)
        TextView tName;
        @BindView(R.id.shop_pop_num)
        TextView tPhone;
        @BindView(R.id.shop_pop_addre)
        TextView tAddress;
        @BindView(R.id.btn_check)
        Button btnCheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    onAddrClick onAddrClick;

    public void setOnAddrClick(PopAdapter.onAddrClick onAddrClick) {
        this.onAddrClick = onAddrClick;
    }

    public interface onAddrClick{
        void onClick(String name,String phone,String addr);
    }
}
