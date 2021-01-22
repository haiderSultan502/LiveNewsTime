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

public class TechnologyFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutTechnology;
    GridLayoutManager gridLayoutManager;
    View view,technologyLatestNewsItem;
    TextView tvPostTitle,tvReadMore;
    ImageView imageViewrtechnologyNews;
    WebsiteView websiteView = new WebsiteView();

    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForTechnologyNews;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    public TechnologyFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_technology,container,false);
        recyclerViewMoreAboutTechnology=view.findViewById(R.id.recycler_view_more_about_technology);
        tvPostTitle = view.findViewById(R.id.tv_title_technology);
        imageViewrtechnologyNews = view.findViewById(R.id.image_view_technology);
        technologyLatestNewsItem = view.findViewById(R.id.technology_latest_News_Item);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        setDataInViews();

        if (MainActivity.getTechnologyNews == true)
        {
            getStoreTechnologyNews();
        }

        technologyLatestNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        return view;
    }

    private void replaceFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("newsUrl",MainActivity.arrayListTechnologyNews.get(0).getGuid());
        websiteView.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                .commit();
    }

    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutTechnology.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        getTechnologyNews("https://livenewstime.com/wp-json/wp/v2/",3);

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getTechnologyNews(String url,int newsCategoryID)
    {
        MainActivity.animationShow();

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForTechnologyNews = interfaceApi.getAllCategoriesNews(newsCategoryID);
            callForTechnologyNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    MainActivity.arrayListTechnologyNews = (ArrayList<NewsModel>) response.body();

                    MainActivity.getTechnologyNews = true;


                    MainActivity.technologyThumbnailUrl = MainActivity.arrayListTechnologyNews.get(0).getFeaturedMedia();
                    Picasso.with(getActivity()).load(MainActivity.technologyThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrtechnologyNews);

                    MainActivity.technologyPostTitle = MainActivity.arrayListTechnologyNews.get(0).getTitle();
                    tvPostTitle.setText(MainActivity.technologyPostTitle);

                    MainActivity.arrayListTechnologyNews.remove(0);


                    AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListTechnologyNews,"readMoreNews");
                    recyclerViewMoreAboutTechnology.setAdapter(allNewsCategoriesAdapter);

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

    private void getStoreTechnologyNews() {

        Picasso.with(getActivity()).load(MainActivity.technologyThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrtechnologyNews);
        tvPostTitle.setText(MainActivity.technologyPostTitle);

        AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListTechnologyNews,"readMoreNews");
        recyclerViewMoreAboutTechnology.setAdapter(allNewsCategoriesAdapter);

        MainActivity.animationHide();
    }
}

