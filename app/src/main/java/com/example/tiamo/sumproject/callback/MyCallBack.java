package com.example.tiamo.sumproject.callback;

public interface MyCallBack {
    //请求成功返回的方法
    void sucess(Object data);
    //请求失败返回的方法
    void faild(String error);
}
