package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(list.get(i).getCreateTime()));
        viewHolder.headTime.setText(dateTime);
        viewHolder.textNum.setText(list.get(i).getGreatNum()+"");
        viewHolder.textTitle.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(viewHolder.circlrImg);
        //判断是否点赞
        if (list.get(i).getWhetherGreat() == 1){
            viewHolder.imgBtn.setImageResource(R.mipmap.common_btn_prise_s);
        }else{
            viewHolder.imgBtn.setImageResource(R.mipmap.common_btn_prise_n);
        }
        //圈子点赞
        viewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null){
                    Log.i("LALALLALA",list.get(i).getId()+"........");
                    onClick.Click(list.get(i).getWhetherGreat(),list.get(i).getId(),i);
                }
            }
        });
    }
    private static int NOPRISE = 1;
    //点赞
    public void priceSucess(int i){
       // viewHolder.imgBtn.setImageResource(R.mipmap.common_btn_prise_s);
        int num = list.get(i).getGreatNum() + 1;
        list.get(i).setGreatNum(list.get(i).getGreatNum()+NOPRISE);
        list.get(i).setWhetherGreat(NOPRISE);
        Log.i("LALALLALA",NOPRISE+"点赞........");
        notifyDataSetChanged();
    }
    //取消点赞
    public void priceCencel(int i){
       // viewHolder.imgBtn.setImageResource(R.mipmap.common_btn_prise_n);
        list.get(i).setGreatNum(list.get(i).getGreatNum()-NOPRISE);
        list.get(i).setWhetherGreat(NOPRISE+1);
        Log.i("LALALLALA",NOPRISE+1+"取消........");
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ciecle_head_photo)
        SimpleDraweeView draweeView;
        @BindView(R.id.circle_head_name)
        TextView headName;
        @BindView(R.id.circl_time)
        TextView headTime;
        @BindView(R.id.circle_text)
        TextView textTitle;
        @BindView(R.id.circlr_number)
        TextView textNum;
        @BindView(R.id.circle_img)
        ImageView circlrImg;
        @BindView(R.id.btn_prise)
        ImageView imgBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    OnClick onClick;

    public void setOnClick(OnClick click){
        onClick = click;
    }

    public interface OnClick{
        void Click(int great,int id,int position);
    }

}
