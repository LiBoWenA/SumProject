package com.example.tiamo.sumproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndentFragment extends Fragment implements IView {
    @BindView(R.id.indent_sunindent)
    LinearLayout layoutSumIndent;
    @BindView(R.id.indent_payment)
    LinearLayout layoutPayment;
    @BindView(R.id.indent_waitcargo)
    LinearLayout layoutCargo;
    @BindView(R.id.indent_evaluate)
    LinearLayout layoutEvaluate;
    @BindView(R.id.indent_sucess)
    LinearLayout layoutSucess;
    @BindView(R.id.indent_recycler)
    XRecyclerView recyclerView;
    private int page;
    private final int SELECTSUM = 0;
    private final int SELECT_PAYMENT = 1;
    private final int SELECT_CARGO = 2;
    private final int SELECT_EVALUATE = 3;
    private final int SELECT_SUCESS = 9;
    private IPersenterImpl iPersenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.indentfragment,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iPersenter = new IPersenterImpl(this);
        page = 1;
    }
    //布局管理者
    public void setIndent(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

    }
    @OnClick({R.id.indent_sunindent,R.id.indent_payment,R.id.indent_waitcargo,R.id.indent_evaluate,R.id.indent_sucess})
    public void setOnClick(View v){
        switch (v.getId()){
            case R.id.indent_sunindent:
                //点击全部订单进行查询
//                iPersenter.showRequestData(String.format(UrlApis.INDENT_FIND,page),);
        }
    }

    @Override
    public void startRequestData(Object data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersenter.onDestory();
    }
}
