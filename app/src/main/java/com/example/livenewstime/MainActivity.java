package com.example.livenewstime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.example.livenewstime.adpater.LatestNewsAdapter;
import com.example.livenewstime.adpater.ReadMoreAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewLatestNews,getRecyclerViewMoreNews;
    LinearLayoutManager linearLayoutManager;
    TabLayout tabLayout;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

    }

    private void initializeView() {
        tabLayout=findViewById(R.id.tabLayout);
        recyclerViewLatestNews=findViewById(R.id.recycler_view_latest_news);
        getRecyclerViewMoreNews=findViewById(R.id.recycler_view_more_news);
        setDataInViews();
    }

    private void setDataInViews() {


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                index = tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayoutManager setOrientationToLatestNewsRecyclerView =setRecyclerViewOrientation();
        recyclerViewLatestNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);
        LatestNewsAdapter latestNewsAdapter=new LatestNewsAdapter(this);
        recyclerViewLatestNews.setAdapter(latestNewsAdapter);

        LinearLayoutManager setOrientationToMorereadRecyclerView =setRecyclerViewOrientation();
        getRecyclerViewMoreNews.setLayoutManager(setOrientationToMorereadRecyclerView);
        ReadMoreAdapter readMoreAdapter=new ReadMoreAdapter(this);
        getRecyclerViewMoreNews.setAdapter(readMoreAdapter);

    }

    private LinearLayoutManager setRecyclerViewOrientation() {
       linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }

}