package com.example.tiamo.sumproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.adapter.CircleAdapter;
import com.example.tiamo.sumproject.bean.CircleBean;
import com.example.tiamo.sumproject.bean.PriseBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class CircleFragment extends Fragment implements IView {
    private String path = "http://172.17.8.100/small/circle/v1/findCircleList?userId=%d&sessionId=%s&page=%d&count=5";
    private String prisePath = "http://172.17.8.100/small/circle/verify/v1/addCircleGreat";
    IPersenterImpl iPersenter;
    XRecyclerView recyclerView;
    CircleAdapter adapter;
    int page;
    private int ids;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circlefragment,null);
        recyclerView = view.findViewById(R.id.recyclr);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        page = 1;
        init();
    }

    private void init() {
        iPersenter = new IPersenterImpl(this);
        adapter = new CircleAdapter(getActivity());
        //布局
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        //添加监听
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                iPersenter.showRequestData(String.format(path,ids,sessionId,page),CircleBean.class);
            }

            @Override
            public void onLoadMore() {
                iPersenter.showRequestData(String.format(path,ids,sessionId,page),CircleBean.class);
            }
        });

        Intent intent = getActivity().getIntent();
        ids = intent.getIntExtra("userId",0);
        sessionId = intent.getStringExtra("sessionId");
        Log.i("TAG",ids+sessionId);
        iPersenter.showRequestData(String.format(path, ids, sessionId,page),CircleBean.class);


    }


    @Override
    public void startRequestData(Object data) {
        if (data instanceof CircleBean){
            CircleBean bean = (CircleBean) data;
            if (page == 1) {
                adapter.setData(bean.getResult());
            }else{
                adapter.addData(bean.getResult());
            }
            page++;
            //停止刷新和加载
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        }
        if (data instanceof PriseBean){
            PriseBean bean = (PriseBean) data;
            Log.i("TAG",bean.getMessage());
        }
    }
}
