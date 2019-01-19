package com.example.tiamo.sumproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tiamo.sumproject.MainActivity;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.CircleAdapter;
import com.example.tiamo.sumproject.bean.CircleBean;
import com.example.tiamo.sumproject.bean.PriseBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleFragment extends Fragment implements IView {
    IPersenterImpl iPersenter;
    @BindView(R.id.recyclr)
    XRecyclerView recyclerView;
    CircleAdapter adapter;
    int page;
    private int ids;
    private String sessionId;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circlefragment,null);
        bind = ButterKnife.bind(this, view);
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
                iPersenter.showRequestData(String.format(UrlApis.CIRCLE_PATH,ids,sessionId,page),CircleBean.class);
            }

            @Override
            public void onLoadMore() {
                iPersenter.showRequestData(String.format(UrlApis.CIRCLE_PATH,ids,sessionId,page),CircleBean.class);
            }
        });

        Intent intent = getActivity().getIntent();
        ids = intent.getIntExtra("userId",0);
        sessionId = intent.getStringExtra("sessionId");
        Log.i("TAG",ids+sessionId);
        iPersenter.showRequestData(String.format(UrlApis.CIRCLE_PATH, ids, sessionId,page),CircleBean.class);
        adapter.setOnClick(new CircleAdapter.OnClick() {
            @Override
            public void Click(int great, int id,int position) {
                if (great == 1){
                    //如果为点赞在次点击就是取消点赞
                    cancelGreatCircle(id);
                    adapter.priceCencel(position);
                }else{
                    //否则就是点赞
                    greatCircle(id);
                    adapter.priceSucess(position);
                }
            }
        });

    }

    //点赞
    private void greatCircle(int id) {
        Map<String,String> map = new HashMap<>();
        map.put("circleId",id+"");
        iPersenter.showRequestData(UrlApis.CIRCLE_GREAT,map,PriseBean.class);
    }

    //取消点赞
    private void cancelGreatCircle(int id) {
        iPersenter.deleteShowRequestData(String.format(UrlApis.CIRCLE_CANCLEGREAT,id),PriseBean.class);
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
            if(bean.equals("请先登陆")){
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersenter.onDestory();
        bind.unbind();
    }
}
