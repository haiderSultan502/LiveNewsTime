package com.example.livenewstime.Interface;


import com.example.livenewstime.models.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceApi {

    @GET("posts")
    Call<List<NewsModel>> getAllNews();




}
