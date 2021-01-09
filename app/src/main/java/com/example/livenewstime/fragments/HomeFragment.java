    package com.example.livenewstime.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.Interface.InterfaceApi;
import com.example.livenewstime.R;
import com.example.livenewstime.adpater.HomeLatestNewsAdapter;
import com.example.livenewstime.models.NewsModel;
import com.example.livenewstime.otherClasses.RetrofitLibrary;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class HomeFragment extends Fragment {

    RecyclerView recyclerViewLatestNews,recyclerViewMoretNews;
    LinearLayoutManager linearLayoutManager;
    View view;
    TextView tvPostTitle,tvReadMore;
    ImageView imageViewrRecentNews;

    List<String> thumbnailUrl;
    String title;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForNews;
    ArrayList<NewsModel> arrayListAllNews;
    ArrayList<NewsModel> arrayListLatestnews;




    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_home,container,false);

        tvPostTitle = view.findViewById(R.id.tv_post_title);
        tvReadMore = view.findViewById(R.id.tv_readmore_recent_news);
        imageViewrRecentNews = view.findViewById(R.id.image_view_recent_news);
        recyclerViewLatestNews=view.findViewById(R.id.recycler_view_latest_news);
        recyclerViewMoretNews=view.findViewById(R.id.recycler_view_more_news);


        arrayListAllNews = new ArrayList<>();
        arrayListLatestnews = new ArrayList<>();

        setDataInViews();

        return view;
    }

    private void setDataInViews() {

        LinearLayoutManager setOrientationToLatestNewsRecyclerView =setRecyclerViewOrientation();
        recyclerViewLatestNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        LinearLayoutManager setOrientationToMoreNewsRecyclerView =setRecyclerViewOrientation();
        recyclerViewMoretNews.setLayoutManager(setOrientationToMoreNewsRecyclerView);

        getNews("https://livenewstime.com/wp-json/newspaper/v2/");

    }

    private LinearLayoutManager setRecyclerViewOrientation() {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
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
                        return;
                    }

                    arrayListAllNews = (ArrayList<NewsModel>) response.body();

                    for (int i = 0 ; i < 3 ; i++)
                    {
                        arrayListLatestnews.add(arrayListAllNews.get(i));
                        arrayListAllNews.remove(i);
                    }

                    thumbnailUrl = arrayListAllNews.get(3).getFeaturedMedia();
                    Picasso.with(getActivity()).load(thumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrRecentNews);

                    title = arrayListAllNews.get(3).getTitle();
                    tvPostTitle.setText(title);

                    arrayListAllNews.remove(3);

                    Log.d("size", "onResponse: " + arrayListAllNews.size());


                    HomeLatestNewsAdapter homeLatestNewsAdapter = new HomeLatestNewsAdapter(getActivity(),arrayListLatestnews,"latestNews");
                    recyclerViewLatestNews.setAdapter(homeLatestNewsAdapter);

                    HomeLatestNewsAdapter homeMoreNewsAdapter = new HomeLatestNewsAdapter(getActivity(),arrayListAllNews,"moreNews");
                    recyclerViewMoretNews.setAdapter(homeMoreNewsAdapter);

                }

                @Override
                public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                }
            });
        }
}
