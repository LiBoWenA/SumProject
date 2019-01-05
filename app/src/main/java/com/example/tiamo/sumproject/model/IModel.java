package com.example.tiamo.sumproject.model;

import com.example.tiamo.sumproject.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String path, Map<String,String> map, Class clazz, MyCallBack myCallBack);
    void getRequesData(String path, Class clazz, MyCallBack myCallBack);
}
