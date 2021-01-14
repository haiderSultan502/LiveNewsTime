package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.Interface.InterfaceApi;
import com.example.livenewstime.MainActivity;
import com.example.livenewstime.R;
import com.example.livenewstime.adpater.AllNewsCategoriesAdapter;
import com.example.livenewstime.models.NewsModel;
import com.example.livenewstime.otherClasses.RetrofitLibrary;
import com.example.livenewstime.otherClasses.SweetAlertDialogGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutNews;
    GridLayoutManager gridLayoutManager;
    View view;
    TextView tvPostTitle,tvReadMore;
    ImageView imageViewNews;

    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForNews;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    public NewsFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_news,container,false);
        recyclerViewMoreAboutNews=view.findViewById(R.id.recycler_view_more_about_news);
        tvPostTitle = view.findViewById(R.id.tv_title_news);
        imageViewNews = view.findViewById(R.id.image_view_news);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        setDataInViews();

        if (MainActivity.getNews == true)
        {
            MainActivity.animationHide();
            getStoreNews();
        }

        return view;
    }



    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        getNews("https://livenewstime.com/wp-json/wp/v2/",2);

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getNews(String url,int newsCategoryID)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForNews = interfaceApi.getAllCategoriesNews(newsCategoryID);
            callForNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    MainActivity.arrayListNews = (ArrayList<NewsModel>) response.body();

                    MainActivity.getNews = true;


                    MainActivity.newsThumbnailUrl = MainActivity.arrayListNews.get(0).getFeaturedMedia();
                    Picasso.with(getActivity()).load(MainActivity.newsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewNews);

                    MainActivity.newsPostTitle = MainActivity.arrayListNews.get(0).getTitle();
                    tvPostTitle.setText(MainActivity.newsPostTitle);

                    MainActivity.arrayListNews.remove(0);


                    AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListNews,"readMoreNews");
                    recyclerViewMoreAboutNews.setAdapter(allNewsCategoriesAdapter);

                    MainActivity.animationHide();

                }

                @Override
                public void onFailure(Call<List<NewsModel>> call, Throwable t) {

                    sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }

    }
    private void getStoreNews() {

        Picasso.with(getActivity()).load(MainActivity.newsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewNews);
        tvPostTitle.setText(MainActivity.newsPostTitle);

        AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListNews,"readMoreNews");
        recyclerViewMoreAboutNews.setAdapter(allNewsCategoriesAdapter);
    }
}

