package com.example.tiamo.sumproject.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.tiamo.sumproject.MyApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxManager<T> {
    private final String URL = "http://mobile.bwstudent.com/small/";
    private static RxManager rxManager;
    private RxApis rxApis;

    public static synchronized RxManager getRxManager() {
        if (rxManager == null) {
            rxManager = new RxManager();
        }
        return rxManager;
    }


    private RxManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                SharedPreferences preferences = MyApp.getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
                String userId = preferences.getString("UserId", "");
                String sessionId = preferences.getString("SessionId", "");
                Request.Builder builder1 = request.newBuilder();
                builder1.method(request.method(),request.body());

                if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                    builder1.addHeader("userId",userId);
                    builder1.addHeader("sessionId",sessionId);
                }
                Request build = builder1.build();
                return chain.proceed(build);
            }
        });
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URL)
                .client(client)
                .build();

        rxApis = retrofit.create(RxApis.class);
    }

    /**
     * get请求
     */
    public RxManager get(String url,HttpListener listener) {

        rxApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

        return rxManager;
    }

    /**
     * delete请求
     */
    public RxManager delete(String url,HttpListener listener) {

        rxApis.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

        return rxManager;
    }

    /**
     * 普通post请求
     */
    public RxManager post(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }

        rxApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return rxManager;
    }

    public RxManager put(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        rxApis.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return rxManager;
    }


    private Observer getObserver(final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    Log.i("ADDRESS",data+"成功返回的放法");
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("ADDRESS",e.getMessage()+"失败的报错信息");
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                    Log.i("ADDRESS",e.getMessage()+"异常的报错信息");
                }
            }

            @Override
            public void onCompleted() {

            }
        };
        return observer;
    }
    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }
}


