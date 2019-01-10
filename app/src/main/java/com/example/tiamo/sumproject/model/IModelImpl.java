package com.example.tiamo.sumproject.model;

import android.util.Log;

import com.example.tiamo.sumproject.MyApp;
import com.example.tiamo.sumproject.callback.MyCallBack;
import com.example.tiamo.sumproject.network.RxManager;
import com.example.tiamo.sumproject.okhttp.OkHttpUtils;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * M层
 * @author TiAmo
 */
public class IModelImpl implements IModel {

    @Override
    public void requestData(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RxManager.getRxManager().post(path, map, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                Log.i("ADDRESS",o.toString()+"m层");
                myCallBack.sucess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.faild(error);
            }
        });
    }

    @Override
    public void getRequesData(String path, final Class clazz, final MyCallBack myCallBack) {
        RxManager.getRxManager().get(path, new RxManager.HttpListener() {
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


    @Override
    public void deleteRequesData(String path, final Class clazz, final MyCallBack myCallBack) {
        RxManager.getRxManager().delete(path, new RxManager.HttpListener() {
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

    @Override
    public void putRequestData(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RxManager.getRxManager().put(path, map, new RxManager.HttpListener() {
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
