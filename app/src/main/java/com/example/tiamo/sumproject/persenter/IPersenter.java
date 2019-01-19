package com.example.tiamo.sumproject.persenter;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IPersenter {
    //post
    void showRequestData(String path, Map<String,String> map,Class clazz);
    //get
    void showRequestData(String path,Class clazz);
    //delete
    void deleteShowRequestData(String path,Class clazz);
    //put
    void putShowRequestData(String path, Map<String,String> map,Class clazz);
    //postImg
    void postImgShowRequestData(String path, Map<String,String> map,Class clazz);
    //postImgsCon
    void postImagesCon(String url, Map<String, String> map, List<File> list, Class clazz);

}
