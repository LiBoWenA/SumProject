package com.example.tiamo.sumproject.network;

import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface RxApis {

    @GET
    Observable<ResponseBody> get(@Url String url);

    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, String> map);

    @POST
    Observable<ResponseBody> postFile(@Url String url,@Body MultipartBody multipartBody);

    @POST
    Observable<ResponseBody> postImageFile(@Url String url,@QueryMap Map<String,String> map,@Body MultipartBody multipartBody);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> map);

    @POST
    @Multipart
    Observable<ResponseBody> postDuoContent(@Url String url ,@QueryMap Map<String, String> map ,@Part MultipartBody.Part[] parts);


}
