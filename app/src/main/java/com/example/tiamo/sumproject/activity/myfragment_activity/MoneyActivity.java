package com.example.tiamo.sumproject.activity.myfragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.MoneAdapter;
import com.example.tiamo.sumproject.bean.myfragmentbean.MoneyBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoneyActivity extends AppCompatActivity implements IView {

    @BindView(R.id.money_text)
    TextView moneyText;
    @BindView(R.id.money_rv)
    XRecyclerView recyclerViewMoney;
    private IPersenterImpl iPersenter;
    private Unbinder bind;
    private MoneAdapter adapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        page = 1;

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMoney.setLayoutManager(manager);
        adapter = new MoneAdapter(this);
        recyclerViewMoney.setAdapter(adapter);
        recyclerViewMoney.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                iPersenter.showRequestData(String.format(UrlApis.SELECT_MONEY,page),MoneyBean.class);
                recyclerViewMoney.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                iPersenter.showRequestData(String.format(UrlApis.SELECT_MONEY,page),MoneyBean.class);
                recyclerViewMoney.loadMoreComplete();
            }
        });

        iPersenter.showRequestData(String.format(UrlApis.SELECT_MONEY,page),MoneyBean.class);

    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof MoneyBean){
            MoneyBean bean = (MoneyBean) data;
            Toast.makeText(MoneyActivity.this,bean.getMessage().toString(),Toast.LENGTH_SHORT).show();
            if (bean.getStatus().equals("0000")){
                moneyText.setText(bean.getResult().getBalance()+"");
                if (page == 1) {
                    adapter.setData(bean.getResult().getDetailList());
                }else{
                    adapter.addData(bean.getResult().getDetailList());
                }
                page++;
                recyclerViewMoney.loadMoreComplete();
                recyclerViewMoney.refreshComplete();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPersenter != null) {
            iPersenter.onDestory();
        }
        bind.unbind();
    }
}
