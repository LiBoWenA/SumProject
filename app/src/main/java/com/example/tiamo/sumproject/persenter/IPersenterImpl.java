package com.example.tiamo.sumproject.persenter;

import com.example.tiamo.sumproject.callback.MyCallBack;
import com.example.tiamo.sumproject.model.IModel;
import com.example.tiamo.sumproject.model.IModelImpl;
import com.example.tiamo.sumproject.network.RxManager;
import com.example.tiamo.sumproject.okhttp.OkHttpUtils;
import com.example.tiamo.sumproject.view.IView;

import java.util.Map;

public class IPersenterImpl implements IPersenter {
    private IView iView;
    IModel iModel;

    public IPersenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    //post
    @Override
    public void showRequestData(String path, Map<String, String> map, Class clazz) {
        iModel.requestData(path, map, clazz, new MyCallBack() {
            @Override
            public void sucess(Object data) {
                iView.startRequestData(data);
            }

            @Override
            public void faild(String error) {
                iView.startRequestData(error);
            }
        });
    }

    //get
    @Override
    public void showRequestData(String path, Class clazz) {
        iModel.getRequesData(path, clazz, new MyCallBack() {
            @Override
            public void sucess(Object data) {
                iView.startRequestData(data);
            }

            @Override
            public void faild(String error) {
                iView.startRequestData(error);
            }
        });
    }
    //解绑
    public void onDestory(){
        if (iModel != null){
            iModel = null;
        }
        if (iView != null){
            iView = null;
        }
    }
}
