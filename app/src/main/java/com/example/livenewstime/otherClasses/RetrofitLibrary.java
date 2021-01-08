package com.example.livenewstime.otherClasses;

import com.example.livenewstime.Interface.InterfaceApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLibrary {
    static InterfaceApi interfaceApi;
    RetrofitLibrary()
    {

    }

    static public InterfaceApi connect(String baseUrl)
    {
        Gson gson = new GsonBuilder()
                .setLenient().
                        create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        interfaceApi = retrofit.create(InterfaceApi.class);

        return  interfaceApi;
    }
}
