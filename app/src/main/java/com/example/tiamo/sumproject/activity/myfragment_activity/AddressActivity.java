package com.example.tiamo.sumproject.activity.myfragment_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.adapter.SelectAddressAdapter;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.myfragmentbean.SelectAddressBean;
import com.example.tiamo.sumproject.fragment.MyFragment;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddressActivity extends AppCompatActivity implements IView {

    private IPersenterImpl iPersenter;
    private Unbinder bind;
    @BindView(R.id.address_rv)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.insert_address)
    Button insertBtn;
    @BindView(R.id.sucess)
    TextView tSucess;
    private SelectAddressAdapter adapter;
    private SelectAddressBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.SELECT_ADDRESS,SelectAddressBean.class);
        init();
    }

    private void init() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAddress.setLayoutManager(manager);
        adapter = new SelectAddressAdapter(this);
        recyclerViewAddress.setAdapter(adapter);
        adapter.setOnClick(new SelectAddressAdapter.OnClick() {
            @Override
            public void click(int id) {
                Intent intent = new Intent(AddressActivity.this,UpdateAddressActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });
        //勾选默认地址
        adapter.setOnAddClick(new SelectAddressAdapter.OnAddrIdClick() {
            @Override
            public void click(int id) {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                iPersenter.showRequestData(UrlApis.ADDRESS_CHECK,map,IndentBean.class);
            }
        });
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this,InsertAddressActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //点击完成返回Myfragment
        tSucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        iPersenter.showRequestData(UrlApis.SELECT_ADDRESS,SelectAddressBean.class);
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof SelectAddressBean){
            SelectAddressBean bean = (SelectAddressBean) data;
            if (bean.getResult().size() ==0){
                Toast.makeText(AddressActivity.this, "没有地址！请重新添加", Toast.LENGTH_SHORT).show();
            }else {
                if (bean.getStatus().equals("0000")) {
                    adapter.setData(bean.getResult());
                } else {
                    Toast.makeText(AddressActivity.this, "没有地址！", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (data instanceof IndentBean){
            IndentBean bean = (IndentBean) data;
            if (bean.getStatus().equals("0000")){
                iPersenter.showRequestData(UrlApis.SELECT_ADDRESS,SelectAddressBean.class);
            }
            Toast.makeText(AddressActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
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
