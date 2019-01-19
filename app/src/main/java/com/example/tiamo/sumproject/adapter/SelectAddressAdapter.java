package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.activity.myfragment_activity.UpdateAddressActivity;
import com.example.tiamo.sumproject.bean.myfragmentbean.SelectAddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.ViewHolder> {

    private Context context;
    private List<SelectAddressBean.ResultBean> list;

    public SelectAddressAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<SelectAddressBean.ResultBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.addressactivity_new_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectAddressAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tName.setText(list.get(i).getRealName());
        viewHolder.tPhone.setText(list.get(i).getPhone()+"");
        viewHolder.tAddress.setText(list.get(i).getAddress());
        if (list.get(i).getWhetherDefault() == 1){
            viewHolder.tCheck.setChecked(true);
        }else{
            viewHolder.tCheck.setChecked(false);
        }
        viewHolder.tUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null){
                    onClick.click(list.get(i).getId());
                }

            }
        });
        viewHolder.tCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (onAddrIdClickick != null){
                        onAddrIdClickick.click(list.get(i).getId());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_iten_name)
        TextView tName;
        @BindView(R.id.address_iten_phone)
        TextView tPhone;
        @BindView(R.id.address_iten_address)
        TextView tAddress;
        @BindView(R.id.address_iten_ck)
        RadioButton tCheck;
        @BindView(R.id.address_iten_update)
        TextView tUpdate;
        @BindView(R.id.address_iten_del)
        TextView tDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    OnClick onClick;

    public void setOnClick(OnClick click){
        onClick = click;
    }
    public interface OnClick{
        void click(int id);
    }

    OnAddrIdClick onAddrIdClickick;

    public void setOnAddClick(OnAddrIdClick click){
        onAddrIdClickick = click;
    }
    public interface OnAddrIdClick{
        void click(int id);
    }
}
