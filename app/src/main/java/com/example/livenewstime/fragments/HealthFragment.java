package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class HealthFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutHealth;
    GridLayoutManager gridLayoutManager;
    View view,healthLatestNewsItem;
    TextView tvPostTitle,tvReadMore,tvCategoryName;
    ImageView imageViewHealth;
    RelativeLayout imgBackButton;
    LinearLayout lootieAnmationParentlayout;
    WebsiteView websiteView = new WebsiteView();

    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForHealth;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    public HealthFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_health,container,false);
        recyclerViewMoreAboutHealth=view.findViewById(R.id.recycler_view_more_about_health);
        tvPostTitle = view.findViewById(R.id.tv_title_health);
        imageViewHealth = view.findViewById(R.id.image_view_health);
        imgBackButton=view.findViewById(R.id.img_back_btn);
        healthLatestNewsItem = view.findViewById(R.id.health_latest_News_Item);
        lootieAnmationParentlayout=view.findViewById(R.id.lootie_animation_parent_layout);
        tvCategoryName = view.findViewById(R.id.categoryNameHealth);


        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        parentAnimationShow();
        setDataInViews();

        if (MainActivity.getHealthNews == true)
        {
            parentAnimationHide();
            getStoreHealthNews();
        }


        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        healthLatestNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        return view;
    }

    private void replaceFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("newsUrl",MainActivity.arrayListHealthNews.get(0).getGuid());
        websiteView.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                .commit();
    }



    public  void parentAnimationShow() {
        lootieAnmationParentlayout.setVisibility(View.VISIBLE);
    }
    public  void parentAnimationHide() {
        lootieAnmationParentlayout.setVisibility(View.GONE);
    }

    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutHealth.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        getHealthNews("https://livenewstime.com/wp-json/wp/v2/",7);

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getHealthNews(String url,int newsCategoryID)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForHealth = interfaceApi.getAllCategoriesNews(newsCategoryID);
            callForHealth.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        parentAnimationHide();
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    MainActivity.arrayListHealthNews = (ArrayList<NewsModel>) response.body();

                    MainActivity.getHealthNews = true;

                    MainActivity.categoryNameHealth = MainActivity.arrayListCategoryDetails.get(7).getName();

                    tvCategoryName.setText(MainActivity.categoryNameHealth);



                    MainActivity.healthThumbnailUrl = MainActivity.arrayListHealthNews.get(0).getFeaturedMedia();
                    Picasso.with(getActivity()).load(MainActivity.healthThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewHealth);

                    MainActivity.healthPostTitle = MainActivity.arrayListHealthNews.get(0).getTitle();
                    tvPostTitle.setText(MainActivity.healthPostTitle);

                    MainActivity.arrayListHealthNews.remove(0);


                    AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListHealthNews,"readMoreNews");
                    recyclerViewMoreAboutHealth.setAdapter(allNewsCategoriesAdapter);

                    parentAnimationHide();

                }

                @Override
                public void onFailure(Call<List<NewsModel>> call, Throwable t) {

                    parentAnimationHide();
                    sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            parentAnimationHide();
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }

    }

    private void getStoreHealthNews() {

        tvCategoryName.setText(MainActivity.categoryNameHealth);

        Picasso.with(getActivity()).load(MainActivity.healthThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewHealth);
        tvPostTitle.setText(MainActivity.healthPostTitle);

        AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListHealthNews,"readMoreNews");
        recyclerViewMoreAboutHealth.setAdapter(allNewsCategoriesAdapter);
    }
}


