package com.example.tiamo.sumproject.activity.myfragment_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.MyCircleItemAdapter;
import com.example.tiamo.sumproject.bean.myfragmentbean.MyCircleBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleActivity extends AppCompatActivity implements IView {

    @BindView(R.id.activity_mycircle_xrecyclerview)
    XRecyclerView xViewCircle;
    private IPersenterImpl iPersenter;
    private Unbinder bind;
    private int page;
    private MyCircleItemAdapter myCircleItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        init();
    }

    private void init() {
        page =1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xViewCircle.setLayoutManager(linearLayoutManager);
        myCircleItemAdapter = new MyCircleItemAdapter(this);
        xViewCircle.setAdapter(myCircleItemAdapter);
        xViewCircle.setPullRefreshEnabled(true);
        xViewCircle.setLoadingMoreEnabled(true);
        xViewCircle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                iPersenter.showRequestData(String.format(UrlApis.MYCIRCLE, page), MyCircleBean.class);
            }
            @Override
            public void onLoadMore() {
                iPersenter.showRequestData(String.format(UrlApis.MYCIRCLE, page), MyCircleBean.class);
            }
        });
        iPersenter.showRequestData(String.format(UrlApis.MYCIRCLE, page), MyCircleBean.class);

    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof MyCircleBean) {
            MyCircleBean bean = (MyCircleBean) data;
            List<MyCircleBean.ResultBean> result = bean.getResult();
            if (result != null) {
                if (page == 1){
                    myCircleItemAdapter.setData(result);
                } else {
                    myCircleItemAdapter.addData(result);
                }
                page ++;
                xViewCircle.refreshComplete();
                xViewCircle.loadMoreComplete();
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
