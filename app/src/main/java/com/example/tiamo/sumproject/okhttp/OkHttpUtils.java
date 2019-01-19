package com.example.tiamo.sumproject.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.example.tiamo.sumproject.callback.MyCallBack;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
        private static volatile OkHttpUtils mInstance;

        private OkHttpClient mClient;

        private Handler mHandler = new Handler(Looper.getMainLooper());

        /**
         * 第一步，写一个单例，这里用的懒汉式，也可以使用饿汉
         * @return
         */
        public static OkHttpUtils getInstance() {
            if (mInstance == null) {
                synchronized (OkHttpUtils.class) {
                    if (null == mInstance) {
                        mInstance = new OkHttpUtils();
                    }
                }
            }
            return mInstance;
        }

        /**
         * 完成构造方法，OkHttpClient
         */
        private OkHttpUtils() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            /**
             * 使用构造者模式
             * 设置连接超时
             * 读取超时
             * 写超时
             * 添加拦截器
             */
            mClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
        }
        /**
         * 上传图片
         * 创建MultipartBody.Builder，类型为Form
         * 如果上传的是图片
         * 通过File找到图片
         * 给我们 MultipartBody.Builder添加一个表单数据
         * 第二个参数filename为上传之后，服务器保存的名称
         * 第三个参数创建requestBody,类型为表单数据
         * 第四个参数，文件
         *
         * 如果遍历发现不是文件，则保存普通键值对
         *
         * @param url
         * @param params
         * @param clazz
         * @param callBack
         */
        public void uploadImage(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
            OkHttpClient okHttpClient = new OkHttpClient();
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

           /* for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey().equals(Constants.MAP_KEY_UP_LOAD_IMG)) {
                    File file = new File(entry.getValue());
                    builder.addFormDataPart(entry.getKey(), "图片1.png",
                            RequestBody.create(MediaType.parse("multipart/form-data"), file));
                }else{
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }*/

            RequestBody requestBody = builder.build();

            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    final Object o = gson.fromJson(result, clazz);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.sucess(o);
                        }
                    });
                }
            });
        }
}
