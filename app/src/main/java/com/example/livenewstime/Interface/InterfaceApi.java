package com.example.livenewstime.Interface;


import com.example.livenewstime.models.FragmentDetailModel;
import com.example.livenewstime.models.LiveChannelsModel;
import com.example.livenewstime.models.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceApi {

    @GET("posts")
    Call<List<NewsModel>> getHomeNews();

    @GET("search")
    Call<List<NewsModel>> getSearchNews(@Query("s") String searchKeyword);

    @GET("posts")
    Call<List<NewsModel>> getAllCategoriesNews(@Query("cat") int categoryid);

    @GET("tchannels")
    Call<LiveChannelsModel> getLiveChannels();

    @GET("title/")
    Call<List<FragmentDetailModel>> getFragmnetDetail();






}
