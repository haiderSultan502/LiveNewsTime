package com.example.livenewstime.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
    TextView tvPostTitle,tvReadMore,tvCategoryName,tvCategoryDetail;
    ImageView imageViewSports;
    RelativeLayout imgBackButton;
    LinearLayout lootieAnmationParentlayout;

    ProgressBar progressBar;
    String url= "https://livenewstime.com/wp-json/wp/v2/";
    int pageNumber = 1;
    String categortIDAndPageNumber;
    Boolean isScrooling = false;
    int currentItem,totalItems,scrollOutItems;
    AllNewsCategoriesAdapter allNewsCategoriesAdapter;


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
        tvCategoryDetail = view.findViewById(R.id.categoryDetailSports);


        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());
        MainActivity.arrayListSportsNews = new ArrayList<>();

        progressBar = view.findViewById(R.id.progress_bar);

        setProgressBarColor();

        parentAnimationShow();

        setDataInViews();



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

    private void setProgressBarColor() {
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(getActivity(), R.color.readMore), PorterDuff.Mode.SRC_IN );
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
        if (MainActivity.getSportsNews == true)
        {
            parentAnimationHide();
            getStoreSportsNews();
        }
        else
        {
            getSportsNews(pageNumber);
        }


    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getSportsNews(int pageNumber)
    {

        categortIDAndPageNumber = " 4 | " +  String.valueOf(pageNumber);

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForSports = interfaceApi.getAllCategoriesNews(categortIDAndPageNumber);
            callForSports.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        parentAnimationHide();
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }

                    MainActivity.arrayListSportsNews.addAll((ArrayList<NewsModel>) response.body());

                    MainActivity.getSportsNews = true;

                    if (pageNumber == 1)
                    {
                        MainActivity.categoryNameSports = MainActivity.arrayListCategoryDetails.get(11).getName();

                        MainActivity.categoryDetailsSports = MainActivity.arrayListCategoryDetails.get(11).getDescription();

                        tvCategoryName.setText(MainActivity.categoryNameSports);

                        if (MainActivity.categoryDetailsSports.length() < 1)
                        {
                            tvCategoryDetail.setText(getString(R.string.about_politics));
                        }
                        else
                        {
                            tvCategoryDetail.setText(MainActivity.categoryDetailsSports);
                        }


                        MainActivity.sportsThumbnailUrl = MainActivity.arrayListSportsNews.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(MainActivity.sportsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewSports);

                        MainActivity.sportsPostTitle = MainActivity.arrayListSportsNews.get(0).getTitle();
                        tvPostTitle.setText(MainActivity.sportsPostTitle);

                        MainActivity.arrayListSportsNews.remove(0);


                        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListSportsNews,"readMoreNews");
                        recyclerViewMoreAboutSports.setAdapter(allNewsCategoriesAdapter);
                    }
                    allNewsCategoriesAdapter.notifyDataSetChanged();
                    allNewsCategoriesAdapter.notifyItemRangeInserted(allNewsCategoriesAdapter.getItemCount() , MainActivity.arrayListSportsNews.size());

                    parentAnimationHide();

                    loadMore();
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

    private void loadMore() {

        recyclerViewMoreAboutSports.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override   //method called when scrolling start
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrooling = true;
                }
            }

            @Override   //method called when scrolling end
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();

                if (isScrooling && (currentItem + scrollOutItems == totalItems))
                {
                    isScrooling = false;
                    fetchData();
                }
            }
        });
    }

    private void fetchData() {

        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                pageNumber++;

                getSportsNews(pageNumber);

                progressBar.setVisibility(View.GONE);

            }
        },5000);
    }

    private void getStoreSportsNews() {

        tvCategoryName.setText(MainActivity.categoryNameSports);

        if (MainActivity.categoryDetailsSports.length() < 1)
        {
            tvCategoryDetail.setText(getString(R.string.about_politics));
        }
        else
        {
            tvCategoryDetail.setText(MainActivity.categoryDetailsSports);
        }

        Picasso.with(getActivity()).load(MainActivity.sportsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewSports);
        tvPostTitle.setText(MainActivity.sportsPostTitle);

        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListSportsNews,"readMoreNews");
        recyclerViewMoreAboutSports.setAdapter(allNewsCategoriesAdapter);

        loadMore();

    }
}


