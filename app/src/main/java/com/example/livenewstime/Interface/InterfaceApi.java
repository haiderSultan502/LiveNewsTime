package com.example.livenewstime.Interface;


import android.provider.SyncStateContract;
import com.example.livenewstime.models.NewsAboutAllCategories;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InterfaceApi {

    @GET("posts")   //working
    Call<List<NewsAboutAllCategories>> getAllNews();




}
