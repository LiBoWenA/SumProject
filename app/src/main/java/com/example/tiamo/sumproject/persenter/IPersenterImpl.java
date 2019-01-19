package com.example.tiamo.sumproject.persenter;

import android.util.Log;

import com.example.tiamo.sumproject.callback.MyCallBack;
import com.example.tiamo.sumproject.model.IModel;
import com.example.tiamo.sumproject.model.IModelImpl;
import com.example.tiamo.sumproject.network.RxManager;
import com.example.tiamo.sumproject.okhttp.OkHttpUtils;
import com.example.tiamo.sumproject.view.IView;

import java.io.File;
import java.util.List;
import java.util.Map;

public class IPersenterImpl implements IPersenter {
    private IView iView;
    IModelImpl iModel;

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
                Log.i("MONEY",data.toString()+"duide");
                iView.startRequestData(data);
            }

            @Override
            public void faild(String error) {
                Log.i("MONEY",error+"错误");
                iView.startRequestData(error);
            }
        });
    }

    //delete
    @Override
    public void deleteShowRequestData(String path, Class clazz) {
        iModel.deleteRequesData(path, clazz, new MyCallBack() {
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

    @Override
    public void putShowRequestData(String path, Map<String, String> map, Class clazz) {
        iModel.putRequestData(path, map, clazz, new MyCallBack() {
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

    @Override
    public void postImgShowRequestData(String path, Map<String, String> map, Class clazz) {
        iModel.imageRequestData(path, map, clazz, new MyCallBack() {
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

    @Override
    public void postImagesCon(String url, Map<String, String> map, List<File> list, Class clazz) {
        iModel.requestDataMduoContext(url, map, list, clazz, new MyCallBack() {
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
