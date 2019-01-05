package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.OneLevelBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  OneLevelAdapter extends RecyclerView.Adapter<OneLevelAdapter.ViewHolder> {
    private Context context;
    private List<OneLevelBean.ResultBean> list;

    public OneLevelAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setDate(List<OneLevelBean.ResultBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OneLevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepage_one_level,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OneLevelAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(list.get(i).getName());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("IIIIIIIDDDDDD","+++"+list.get(i).getId());
                if (onClick != null){

                    onClick.Click(list.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.one_level_text)
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void Click(String commodityId);
    }
}
