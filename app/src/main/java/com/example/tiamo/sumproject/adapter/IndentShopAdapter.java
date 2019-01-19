package com.example.tiamo.sumproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentShopBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndentShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<IndentShopBean.OrderListBean> list;
    private double price;

    public IndentShopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<IndentShopBean.OrderListBean> lists){
       // list.clear();
      //  list.addAll(lists);
        list=lists;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                View sumView = LayoutInflater.from(context).inflate(R.layout.indentfragment_sumindent, viewGroup, false);
                SuninViewHolder viewHolder = new SuninViewHolder(sumView);
                ButterKnife.bind(viewHolder,sumView);
                return viewHolder;
            case 2:
                View waitView = LayoutInflater.from(context).inflate(R.layout.indentfragment_waitgoods, viewGroup, false);
                WaitViewHolder waitViewHolder = new WaitViewHolder(waitView);
                ButterKnife.bind(waitViewHolder,waitView);
                return waitViewHolder;
            case 3:
                View goodsView = LayoutInflater.from(context).inflate(R.layout.indentfragment_itemname, viewGroup, false);
                GoodsViewHolder rminViewHolder= new GoodsViewHolder(goodsView);
                ButterKnife.bind(rminViewHolder,goodsView);
                return rminViewHolder;
            case 9:
                View view1 = View.inflate(context,R.layout.completed_indent,null);
                CompleedViewHolder compleedViewHolder = new CompleedViewHolder(view1);
                return compleedViewHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType) {
            case 1:
                SuninViewHolder viewHolder1 = (SuninViewHolder) viewHolder;
                viewHolder1.tCreat.setText(list.get(i).getOrderId());
                String format = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date(list.get(i).getOrderTime()));
                viewHolder1.tTime.setText(format);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                viewHolder1.recyclerView.setLayoutManager(manager);
                IndentShopItemAdapter adapter = new IndentShopItemAdapter(context);
                viewHolder1.recyclerView.setAdapter(adapter);
                adapter.setData(list.get(i).getDetailList());
                int num = 0;
                price = 0.00;
                for (int j = 0; j < list.get(i).getDetailList().size(); j++) {
                    num += list.get(i).getDetailList().get(j).getCommodityCount();
                    price += list.get(i).getDetailList().get(j).getCommodityCount() * list.get(i).getDetailList().get(j).getCommodityPrice();
                }
                viewHolder1.tNum.setText(num + "");
                viewHolder1.tPrice.setText(price + "");
                viewHolder1.btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClick != null) {
                            onClick.Click(list.get(i).getOrderId(), price,1);
                        }
                    }
                });
                break;
            case 2:
                WaitViewHolder waitViewHolder= (WaitViewHolder) viewHolder;
                waitViewHolder.wCreat.setText(list.get(i).getOrderId());

                waitViewHolder.wSend.setText(list.get(i).getExpressCompName());
                waitViewHolder.wOddNum.setText(list.get(i).getExpressSn());
                LinearLayoutManager managers = new LinearLayoutManager(context);
                managers.setOrientation(LinearLayoutManager.VERTICAL);
                waitViewHolder.waitRecyclerView.setLayoutManager(managers);
                IndentShopItemAdapter adapters = new IndentShopItemAdapter(context);
                waitViewHolder.waitRecyclerView.setAdapter(adapters);
                adapters.setData(list.get(i).getDetailList());
                waitViewHolder.btnPayPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onPayClick != null){
                            onPayClick.Click(list.get(i).getOrderId(),2);
                        }
                    }
                });
                break;
            case 3:
                //评价
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) viewHolder;
                goodsViewHolder.tCreatNum.setText(list.get(i).getOrderId());
                String formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(list.get(i).getOrderTime()));
                goodsViewHolder.tTime.setText(formatTime);

                LinearLayoutManager managerGoods = new LinearLayoutManager(context);
                managerGoods.setOrientation(LinearLayoutManager.VERTICAL);
                goodsViewHolder.RvIndent.setLayoutManager(managerGoods);
                IndentShopIndentAdapter indentAdapter = new IndentShopIndentAdapter(context);
                goodsViewHolder.RvIndent.setAdapter(indentAdapter);
                indentAdapter.setData(list.get(i).getDetailList());
                indentAdapter.setOnBtnClick(new IndentShopIndentAdapter.OnClick() {
                    @Override
                    public void Click(int id) {
                        if (onEvaluateClick != null){
                            onEvaluateClick.Click(id,list.get(i).getOrderId(),3);
                        }
                    }
                });
                break;
            case 9:
                CompleedViewHolder compleedViewHolder = (CompleedViewHolder) viewHolder;
                compleedViewHolder.completed.setText(list.get(i).getOrderId());
                LinearLayoutManager linearLayoutManagerw1 =new LinearLayoutManager(context);
                compleedViewHolder.completedrecycle.setLayoutManager(linearLayoutManagerw1);
                IndentShopItemAdapter adaptersucess = new IndentShopItemAdapter(context);
                compleedViewHolder.completedrecycle.setAdapter(adaptersucess);
                adaptersucess.setData(list.get(i).getDetailList());
                break;
                default:break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getOrderStatus() == 1) {
            return 1;
        }else if(list.get(position).getOrderStatus() == 2){
            return 2;
        }else if(list.get(position).getOrderStatus()==3){
            return 3;
        }else if(list.get(position).getOrderStatus()== 9) {
            return 9;
        }else {
            return -1;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class SuninViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.indent_sunindent_creat)
        TextView tCreat;
        @BindView(R.id.indent_sunindent_creat_time)
        TextView tTime;
        @BindView(R.id.indent_sunindent_rv)
        RecyclerView recyclerView;
        @BindView(R.id.sumindet_num)
        TextView tNum;
        @BindView(R.id.sumindet_price)
        TextView tPrice;
        @BindView(R.id.shop_pay)
        Button btnPay;
        public SuninViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class WaitViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.indent_waitindent_creat)
        TextView wCreat;
        @BindView(R.id.indent_waitindent_creat_time)
        TextView wTime;
        @BindView(R.id.indent_waitindent_rv)
        RecyclerView waitRecyclerView;
        @BindView(R.id.send_company)
        TextView wSend;
        @BindView(R.id.odd_num)
        TextView wOddNum;
        @BindView(R.id.shop_payprice)
        Button btnPayPrice;
        public WaitViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.indent_creat_num)
        TextView tCreatNum;
        @BindView(R.id.indent_indent_creat_time)
        TextView tTime;
        @BindView(R.id.indent_rv)
        RecyclerView RvIndent;
        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class CompleedViewHolder extends RecyclerView.ViewHolder{
        TextView completed;
        RecyclerView completedrecycle;
        public CompleedViewHolder(@NonNull View itemView) {
            super(itemView);
            completed = itemView.findViewById(R.id.completed_item_code);
            completedrecycle = itemView.findViewById(R.id.completed_item_recycler);
        }
    }

    OnClick onClick;

    public void setOnClick(OnClick click){
        onClick = click;
    }

    public interface OnClick{
        void Click(String id,double price,int num);
    }

    OnPayClick onPayClick;

    public void setOnBtnClick(OnPayClick click){
        onPayClick = click;
    }

    public interface OnPayClick{
        void Click(String id,int num);
    }

    OnEvaluateClick onEvaluateClick;

    public void setOnEvaluateClick(OnEvaluateClick click){
        onEvaluateClick = click;
    }

    public interface OnEvaluateClick{
        void Click(int id,String orderid,int num);
    }

}
