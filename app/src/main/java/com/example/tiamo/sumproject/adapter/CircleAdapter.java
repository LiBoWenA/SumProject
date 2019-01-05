package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.CircleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    Context context;
    List<CircleBean.ResultBean> list;

    public CircleAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<CircleBean.ResultBean> lists){
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void addData(List<CircleBean.ResultBean> lists){
        list.addAll(lists);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CircleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_circlefragment, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CircleAdapter.ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(list.get(i).getHeadPic());
        viewHolder.draweeView.setImageURI(uri);
        viewHolder.headName.setText(list.get(i).getNickName());
        viewHolder.headTime.setText(list.get(i).getCreateTime()+"");
        viewHolder.textNum.setText(list.get(i).getGreatNum()+"");
        viewHolder.textTitle.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(viewHolder.circlrImg);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        TextView headName,headTime,textTitle,textNum;
        ImageView circlrImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.ciecle_head_photo);
            headName = itemView.findViewById(R.id.circle_head_name);
            headTime = itemView.findViewById(R.id.circl_time);
            textTitle = itemView.findViewById(R.id.circle_text);
            textNum = itemView.findViewById(R.id.circlr_number);
            circlrImg = itemView.findViewById(R.id.circle_img);


        }
    }
    OnClick onClick;

    public void setOnClick(OnClick click){
        onClick = click;
    }

    public interface OnClick{
        void Click(int id);
    }

    OnGreatImage greatImage;

    public void setOnGreatImage(OnGreatImage image){
        greatImage = image;
    }

    public interface OnGreatImage{
        void Click(int state);
    }

}
