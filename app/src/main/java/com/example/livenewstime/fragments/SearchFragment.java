package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.Interface.InterfaceApi;
import com.example.livenewstime.R;
import com.example.livenewstime.adpater.AllNewsCategoriesAdapter;
import com.example.livenewstime.models.NewsModel;
import com.example.livenewstime.otherClasses.RetrofitLibrary;
import com.example.livenewstime.otherClasses.SweetAlertDialogGeneral;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    RecyclerView recyclerViewSearchtNews;
    LinearLayoutManager linearLayoutManager;
    View view;

    String searchKeyword;
    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForSearchNews;
    ArrayList<NewsModel> arrayListSearchNews;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;




    public SearchFragment(Context context,String searchKeyword) {
        this.context=context;
        this.searchKeyword=searchKeyword;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_search,container,false);
        recyclerViewSearchtNews=view.findViewById(R.id.recycler_view_search_news);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());
        arrayListSearchNews = new ArrayList<>();

        setDataInViews();

        return view;
    }

    private void setDataInViews() {

        LinearLayoutManager setOrientationToLatestNewsRecyclerView =setRecyclerViewOrientation();
        recyclerViewSearchtNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        getSearchNews("https://livenewstime.com/wp-json/Newspaper/v2/",searchKeyword);

    }

    private LinearLayoutManager setRecyclerViewOrientation() {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

    public void getSearchNews(String url,String searchKeyword)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForSearchNews = interfaceApi.getSearchNews(searchKeyword);
            callForSearchNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    arrayListSearchNews = (ArrayList<NewsModel>) response.body();

                    AllNewsCategoriesAdapter allNewsSearchAdapter = new AllNewsCategoriesAdapter(getActivity(),arrayListSearchNews,"searchNews");
                    recyclerViewSearchtNews.setAdapter(allNewsSearchAdapter);

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
