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

public class SportsFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutSports;
    GridLayoutManager gridLayoutManager;
    View view,sportsLatestNewsItem;
    TextView tvPostTitle,tvReadMore,tvCategoryName;
    ImageView imageViewSports;
    RelativeLayout imgBackButton;
    LinearLayout lootieAnmationParentlayout;


    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForSports;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    WebsiteView websiteView = new WebsiteView();

    public SportsFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_sports,container,false);
        recyclerViewMoreAboutSports=view.findViewById(R.id.recycler_view_more_about_sports);
        tvPostTitle = view.findViewById(R.id.tv_title_sports);
        imageViewSports = view.findViewById(R.id.image_view_sports);
        imgBackButton=view.findViewById(R.id.img_back_btn);
        sportsLatestNewsItem = view.findViewById(R.id.sports_latest_News_Item);
        lootieAnmationParentlayout=view.findViewById(R.id.lootie_animation_parent_layout);
        tvCategoryName = view.findViewById(R.id.categoryNameSports);


        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());
        MainActivity.arrayListSportsNews = new ArrayList<>();

        parentAnimationShow();

        setDataInViews();

        if (MainActivity.getSportsNews == true)
        {
            parentAnimationHide();
            getStoreSportsNews();
        }

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        sportsLatestNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        return view;
    }


    private void replaceFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("newsUrl",MainActivity.arrayListSportsNews.get(0).getGuid());
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
        recyclerViewMoreAboutSports.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        getSportsNews("https://livenewstime.com/wp-json/wp/v2/",4);

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getSportsNews(String url,int newsCategoryID)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForSports = interfaceApi.getAllCategoriesNews(newsCategoryID);
            callForSports.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        parentAnimationHide();
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }

                    MainActivity.arrayListSportsNews = (ArrayList<NewsModel>) response.body();

                    MainActivity.getSportsNews = true;

                    MainActivity.categoryNameSports = MainActivity.arrayListCategoryDetails.get(11).getName();

                    tvCategoryName.setText(MainActivity.categoryNameSports);


                    MainActivity.sportsThumbnailUrl = MainActivity.arrayListSportsNews.get(0).getFeaturedMedia();
                    Picasso.with(getActivity()).load(MainActivity.sportsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewSports);

                    MainActivity.sportsPostTitle = MainActivity.arrayListSportsNews.get(0).getTitle();
                    tvPostTitle.setText(MainActivity.sportsPostTitle);

                    MainActivity.arrayListSportsNews.remove(0);


                    AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListSportsNews,"readMoreNews");
                    recyclerViewMoreAboutSports.setAdapter(allNewsCategoriesAdapter);

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
    private void getStoreSportsNews() {

        tvCategoryName.setText(MainActivity.categoryNameSports);

        Picasso.with(getActivity()).load(MainActivity.sportsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewSports);
        tvPostTitle.setText(MainActivity.sportsPostTitle);

        AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListSportsNews,"readMoreNews");
        recyclerViewMoreAboutSports.setAdapter(allNewsCategoriesAdapter);
    }
}


