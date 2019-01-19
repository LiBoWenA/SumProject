package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.myfragmentbean.MyCircleBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCircleItemAdapter extends RecyclerView.Adapter<MyCircleItemAdapter.MyCircleItemViewHolder> {

    private List<MyCircleBean.ResultBean> resultBeanList;
    private Context resultContext;

    public MyCircleItemAdapter(Context resultContext) {
        this.resultContext = resultContext;
        resultBeanList = new ArrayList<>();
    }

    public void setData(List<MyCircleBean.ResultBean> list) {
        if (list != null) {
            resultBeanList = list;
        }
        notifyDataSetChanged();
    }

    public void addData(List<MyCircleBean.ResultBean> list) {
        if (list != null) {
            resultBeanList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCircleItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(resultContext, R.layout.adapter_mycircle_item, null);
        MyCircleItemViewHolder myCircleItemViewHolder = new MyCircleItemViewHolder(view);
        return myCircleItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCircleItemViewHolder viewHolder, final int i) {
        String image = resultBeanList.get(i).getImage();
        String[] split = image.split(",");

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(split[0])
                .build();
        viewHolder.icon_artcile.setController(controller);
        viewHolder.icon_artcile.setScaleType(ImageView.ScaleType.FIT_XY);

        viewHolder.text_artcile.setText(resultBeanList.get(i).getContent());
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(resultBeanList.get(i).getCreateTime()));
        viewHolder.text_time.setText(dateTime);
        viewHolder.text_prisenum.setText(resultBeanList.get(i).getGreatNum() + "");

        viewHolder.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLongClick != null) {
                    isLongClick.isLongClick(resultBeanList.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }

    class MyCircleItemViewHolder extends RecyclerView.ViewHolder {
        TextView text_artcile, text_time, text_prisenum;
        SimpleDraweeView icon_artcile;
        ImageView icon_delete;
        public MyCircleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            text_artcile = itemView.findViewById(R.id.adapter_mycircle_item_text_artcile);
            text_time = itemView.findViewById(R.id.adapter_mycircle_item_text_time);
            text_prisenum = itemView.findViewById(R.id.adapter_mycircle_item_text_prisenum);
            icon_artcile = itemView.findViewById(R.id.adapter_mycircle_item_icon_artcile);
            icon_delete = itemView.findViewById(R.id.adapter_mycircle_item_icon_delete);
        }
    }

    public interface IsLongClick{
        void isLongClick(int id);
    }
    IsLongClick isLongClick;

    public void setIsLongClick(IsLongClick isLongClick) {
        this.isLongClick = isLongClick;
    }
}
