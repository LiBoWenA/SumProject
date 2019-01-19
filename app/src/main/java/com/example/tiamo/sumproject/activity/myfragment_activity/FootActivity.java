package com.example.tiamo.sumproject.activity.myfragment_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.MyFootPriceAdapter;
import com.example.tiamo.sumproject.bean.myfragmentbean.FootPriceBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FootActivity extends AppCompatActivity implements IView {


    private static final int ITEM_COUNT = 2;
    @BindView(R.id.foortp_xrecy)
    XRecyclerView foortp_xrecy;
    private MyFootPriceAdapter footPriceAdapter;
    private int page;
    private int count=5;
    private IPersenterImpl iPersenter;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot);
        iPersenter = new IPersenterImpl(this);
        bind = ButterKnife.bind(this);
        //瀑布流
        page=1;
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(ITEM_COUNT,OrientationHelper.VERTICAL);
        foortp_xrecy.setLayoutManager(manager);
        footPriceAdapter=new MyFootPriceAdapter(this);
        foortp_xrecy.setAdapter(footPriceAdapter);
        foortp_xrecy.setPullRefreshEnabled(true);
        foortp_xrecy.setLoadingMoreEnabled(true);
        foortp_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                foortp_xrecy.refreshComplete();
                iPersenter.showRequestData(String.format(UrlApis.FOOT_URL,page,count),FootPriceBean.class);
            }

            @Override
            public void onLoadMore() {
                foortp_xrecy.loadMoreComplete();
                iPersenter.showRequestData(String.format(UrlApis.FOOT_URL,page,count),FootPriceBean.class);
            }
        });
        //请求数据
        iPersenter.showRequestData(String.format(UrlApis.FOOT_URL,page,count),FootPriceBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPersenter!=null){
            iPersenter.onDestory();
        }
        bind.unbind();
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof FootPriceBean){
            FootPriceBean footPriceBean= (FootPriceBean) data;
            if (footPriceBean.getStatus().equals("0000")){
                List<FootPriceBean.ResultBean> result = footPriceBean.getResult();
                if (page==1){
                    footPriceAdapter.setMlist(result);
                }else {
                    footPriceAdapter.addMlist(result);
                }
                page++;
            }
        }
    }
}
