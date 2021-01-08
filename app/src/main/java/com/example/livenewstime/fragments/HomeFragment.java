package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.MainActivity;
import com.example.livenewstime.R;
import com.example.livenewstime.adpater.HomeLatestNewsAdapter;
import com.example.livenewstime.adpater.HomeReadMoreAdapter;
import com.example.livenewstime.models.NewsModel;
import com.example.livenewstime.otherClasses.Data;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewLatestNews,getRecyclerViewMoreNews;
    LinearLayoutManager linearLayoutManager;
    View view;
    Context context;


    Data data;

    public HomeFragment(Context context) {
        this.context = context;
        data = new Data(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_home,container,false);

        recyclerViewLatestNews=view.findViewById(R.id.recycler_view_latest_news);
        getRecyclerViewMoreNews=view.findViewById(R.id.recycler_view_more_news);

        setDataInViews();

        return view;
    }

    private void setDataInViews() {

        LinearLayoutManager setOrientationToLatestNewsRecyclerView =setRecyclerViewOrientation();
        recyclerViewLatestNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        data.getNews("https://livenewstime.com/wp-json/newspaper/v2/");
//        HomeLatestNewsAdapter homeLatestNewsAdapter =new HomeLatestNewsAdapter(context);
//        recyclerViewLatestNews.setAdapter(homeLatestNewsAdapter);

//        LinearLayoutManager setOrientationToMorereadRecyclerView =setRecyclerViewOrientation();
//        getRecyclerViewMoreNews.setLayoutManager(setOrientationToMorereadRecyclerView);
//        HomeReadMoreAdapter homeReadMoreAdapter =new HomeReadMoreAdapter(context);
//        getRecyclerViewMoreNews.setAdapter(homeReadMoreAdapter);

    }

    private LinearLayoutManager setRecyclerViewOrientation() {
        linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }
}
