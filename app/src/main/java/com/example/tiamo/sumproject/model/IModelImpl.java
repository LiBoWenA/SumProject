package com.example.tiamo.sumproject.model;

import com.example.tiamo.sumproject.MyApp;
import com.example.tiamo.sumproject.callback.MyCallBack;
import com.example.tiamo.sumproject.network.RxManager;
import com.example.tiamo.sumproject.okhttp.OkHttpUtils;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.ResponseBody;

public class IModelImpl implements IModel {
    /*@Override
    public void requestData(String path, Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getInstance().postEnqueue(path, map, clazz, new MyCallBack() {
            @Override
            public void sucess(Object data) {
                myCallBack.sucess(data);
            }

            @Override
            public void faild(Exception e) {
                myCallBack.faild(e);
            }
        });
    }

    @Override
    public void getRequesData(String path, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getInstance().getEnqueque(path, clazz, new MyCallBack() {
            @Override
            public void sucess(Object data) {
                myCallBack.sucess(data);
            }

            @Override
            public void faild(Exception e) {
                myCallBack.faild(e);
            }
        });
    }*/


    @Override
    public void requestData(String s, Map<String, String> map, final Class clas, final MyCallBack myCallBack) {
        RxManager.getRxManager().post(s, map, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clas);
                myCallBack.sucess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.faild(error);
            }
        });
    }

    @Override
    public void getRequesData(String s, final Class clazz, final MyCallBack myCallBack) {
        RxManager.getRxManager().get(s, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.sucess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.faild(error);
            }
        });

    }
}
