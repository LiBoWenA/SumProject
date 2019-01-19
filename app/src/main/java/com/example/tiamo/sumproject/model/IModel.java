package com.example.tiamo.sumproject.model;

import com.example.tiamo.sumproject.callback.MyCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;
/**
 * M层接口
 * @author TiAmo
 */
public interface IModel {
    void requestData(String path, Map<String,String> map, Class clazz, MyCallBack myCallBack);
    void getRequesData(String path, Class clazz, MyCallBack myCallBack);
    void deleteRequesData(String path, Class clazz, MyCallBack myCallBack);
    void putRequestData(String path, Map<String,String> map, Class clazz, MyCallBack myCallBack);
    void imageRequestData(String path, Map<String,String> map, Class clazz, MyCallBack myCallBack);
    void requestDataMduoContext(String url, Map<String, String> map, List<File> list, Class clazz, MyCallBack callBack);
}
