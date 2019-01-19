package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.myfragmentbean.MoneyBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneAdapter extends RecyclerView.Adapter<MoneAdapter.ViewHolder> {
    private Context context;
    private List<MoneyBean.ResultBean.DetailListBean> list;

    public MoneAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<MoneyBean.ResultBean.DetailListBean> lists){
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }
    public void addData(List<MoneyBean.ResultBean.DetailListBean> lists){
        list.addAll(lists);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_monry_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoneAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tTitle.setText("¥："+list.get(i).getAmount());
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(list.get(i).getConsumerTime()));
        viewHolder.tPrice.setText(format);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.money_num)
        TextView tTitle;
        @BindView(R.id.money_time)
        TextView tPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
