package app.webscare.livenewstime.Interface;


import app.webscare.livenewstime.models.FragmentDetailModel;
import app.webscare.livenewstime.models.LiveChannelsModel;
import app.webscare.livenewstime.models.NewsModel;

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
    Call<List<NewsModel>> getAllCategoriesNews(@Query("cat") int categoryIdAndPageNumber);

    @GET("posts")
    Call<List<NewsModel>> getAllCategoriesNews(@Query("cat") String categoryIdAndPageNumber);

    @GET("tchannels")
    Call<LiveChannelsModel> getLiveChannels();

    @GET("title/")
    Call<List<FragmentDetailModel>> getCategoryDetail();






}
