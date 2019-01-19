package com.example.tiamo.sumproject.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.tiamo.sumproject.MyApp;
import com.example.tiamo.sumproject.callback.MyCallBack;
import com.example.tiamo.sumproject.network.RxManager;
import com.example.tiamo.sumproject.okhttp.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * M层
 * @author TiAmo
 */
public class IModelImpl implements IModel {


    @Override
    public void requestData(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        if (!hasNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RxManager.getRxManager().post(path, map, new RxManager.HttpListener() {

            @Override
            public void onSuccess(String data) {
                Context application = MyApp.getApplication();
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null) {
                    myCallBack.sucess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (myCallBack != null) {
                    myCallBack.faild(error);
                }
            }
        });
    }

    @Override
    public void getRequesData(String path, final Class clazz, final MyCallBack myCallBack) {
        if (!hasNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RxManager.getRxManager().get(path, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null) {
                    myCallBack.sucess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (myCallBack != null) {
                    myCallBack.faild(error);
                }
            }
        });
    }


    @Override
    public void deleteRequesData(String path, final Class clazz, final MyCallBack myCallBack) {
        if (!hasNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RxManager.getRxManager().delete(path, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null) {
                    myCallBack.sucess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (myCallBack != null) {
                    myCallBack.faild(error);
                }
            }
        });
    }

    @Override
    public void putRequestData(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        if (!hasNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RxManager.getRxManager().put(path, map, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null) {
                    myCallBack.sucess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (myCallBack != null) {
                    myCallBack.faild(error);
                }
            }
        });
    }

    @Override
    public void imageRequestData(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        if (!hasNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RxManager.getRxManager().postFile(path, map, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null) {
                    myCallBack.sucess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (myCallBack != null) {
                    myCallBack.faild(error);
                }

            }
        });
    }


    //图文上传
    @Override
    public void requestDataMduoContext(String url, Map<String, String> map, List<File> list, final Class clazz, final MyCallBack callBack) {
        RxManager.getRxManager().postduocon(url, map, list, new RxManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                if (callBack != null) {
                    callBack.sucess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (callBack != null) {
                    callBack.faild(error);
                }
            }
        });
    }

    public static boolean hasNetWork(){
        ConnectivityManager cm = (ConnectivityManager) MyApp.getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isAvailable();
    }


}
