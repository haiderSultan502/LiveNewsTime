package com.example.livenewstime.otherClasses;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.example.livenewstime.Interface.InterfaceApi;
import com.example.livenewstime.MainActivity;
import com.example.livenewstime.fragments.HomeFragment;
import com.example.livenewstime.models.NewsModel;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Data {

    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForNews;
    Context context;

    public Data(Context context)
    {
        this.context = context;
    }
    public void getNews(String url)
    {
        interfaceApi = RetrofitLibrary.connect(url);
        callForNews = interfaceApi.getAllNews();
        callForNews.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
//                mainActivity.news = (ArrayList<NewsModel>) response.body();
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
