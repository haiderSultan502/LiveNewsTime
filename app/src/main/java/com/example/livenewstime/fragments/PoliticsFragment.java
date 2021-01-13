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

public class PoliticsFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutPolitics;
    GridLayoutManager gridLayoutManager;
    View view;
    TextView tvPostTitle,tvReadMore;
    ImageView imageViewrPoliticsNews;

    Context context;
    List<String> thumbnailUrl;
    String title;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForpoliticsNews;
    ArrayList<NewsModel> arrayListPoliticsNews;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    public PoliticsFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_politics,container,false);
        recyclerViewMoreAboutPolitics=view.findViewById(R.id.recycler_view_more_about_politics);
        tvPostTitle = view.findViewById(R.id.tv_title_politics);
        imageViewrPoliticsNews = view.findViewById(R.id.image_view_politics);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());
        arrayListPoliticsNews = new ArrayList<>();

        setDataInViews();

        return view;
    }

    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutPolitics.setLayoutManager(setOrientationToLatestNewsRecyclerView);


        getPoliticsNews("https://livenewstime.com/wp-json/wp/v2/",14);


    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getPoliticsNews(String url,int newsCategoryID)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForpoliticsNews = interfaceApi.getAllCategoriesNews(newsCategoryID);
            callForpoliticsNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    arrayListPoliticsNews = (ArrayList<NewsModel>) response.body();

                    try {
                        thumbnailUrl = arrayListPoliticsNews.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(thumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrPoliticsNews);
                    } catch (Exception e) {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
                    }

                    title = arrayListPoliticsNews.get(0).getTitle();
                    tvPostTitle.setText(title);

                    arrayListPoliticsNews.remove(0);


                    AllNewsCategoriesAdapter homeMoreNewsAdapter = new AllNewsCategoriesAdapter(getActivity(),arrayListPoliticsNews,"readMoreNews");
                    recyclerViewMoreAboutPolitics.setAdapter(homeMoreNewsAdapter);

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
}

