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
import com.example.livenewstime.models.FragmentDetailModel;
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
    View view,latestNewsItem;
    TextView tvPostTitle,tvReadMore,tvCategoryName,tvCategoryDetail;
    ImageView imageViewNews;
    WebsiteView websiteView = new WebsiteView();



    String url= "https://livenewstime.com/wp-json/wp/v2/";
    int pageNumber = 1;
    String categortIDAndPageNumber;
    AllNewsCategoriesAdapter allNewsCategoriesAdapter;
    Boolean isScrooling = false;
    int currentItem,totalItems,scrollOutItems;
    ProgressBar progressBar;

    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForNews;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    Call<List<FragmentDetailModel>> callForCategoryDetails;

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
        latestNewsItem = view.findViewById(R.id.latest_News_Item);
        tvCategoryName = view.findViewById(R.id.categoryNameNews);
        tvCategoryDetail = view.findViewById(R.id.categoryDetailNews);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        progressBar = view.findViewById(R.id.progress_bar);


        setProgressBarColor();

        MainActivity.animationShow();

        setDataInViews();

        latestNewsItem.setOnClickListener(new View.OnClickListener() {
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
        bundle.putString("newsUrl",MainActivity.arrayListNews.get(0).getGuid());
        websiteView.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                .commit();
    }



    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        if (MainActivity.getNews == true)
        {
            getStoreNews();
        }
        else
        {
            getNews(pageNumber);
        }

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getNews(int pageNumber)
    {

        categortIDAndPageNumber = " 2 | " +  String.valueOf(pageNumber);


        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForNews = interfaceApi.getAllCategoriesNews(categortIDAndPageNumber);
            callForNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    MainActivity.arrayListNews.addAll((ArrayList<NewsModel>) response.body());

                    MainActivity.getNews = true;

                    if (pageNumber == 1)
                    {
                        MainActivity.categoryNameNews = MainActivity.arrayListCategoryDetails.get(8).getName();

                        MainActivity.categoryDetailsNews = MainActivity.arrayListCategoryDetails.get(8).getDescription();

                        tvCategoryName.setText(MainActivity.categoryNameNews);

                        if (MainActivity.categoryDetailsNews.length() == 0)
                        {
                            tvCategoryDetail.setVisibility(View.GONE);
                        }
                        else
                        {
                            tvCategoryDetail.setVisibility(View.VISIBLE);
                            tvCategoryDetail.setText(MainActivity.categoryDetailsNews);
                        }


                        MainActivity.newsThumbnailUrl = MainActivity.arrayListNews.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(MainActivity.newsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewNews);

                        MainActivity.newsPostTitle = MainActivity.arrayListNews.get(0).getTitle();
                        tvPostTitle.setText(MainActivity.newsPostTitle);

                        MainActivity.arrayListNews.remove(0);


                        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListNews,"readMoreNews");
                        recyclerViewMoreAboutNews.setAdapter(allNewsCategoriesAdapter);

                    }

                    allNewsCategoriesAdapter.notifyDataSetChanged();
                    allNewsCategoriesAdapter.notifyItemRangeInserted(allNewsCategoriesAdapter.getItemCount() , MainActivity.arrayListNews.size());

                    MainActivity.animationHide();

                    loadMore();



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

    private void loadMore() {

        recyclerViewMoreAboutNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                getNews(pageNumber);

                progressBar.setVisibility(View.GONE);

            }
        },5000);
    }
    private void getStoreNews() {

        tvCategoryName.setText(MainActivity.categoryNameNews);

        if (MainActivity.categoryDetailsNews.length() == 0)
        {
            tvCategoryDetail.setVisibility(View.GONE);
        }
        else
        {
            tvCategoryDetail.setVisibility(View.VISIBLE);
            tvCategoryDetail.setText(MainActivity.categoryDetailsNews);
        }

        Picasso.with(getActivity()).load(MainActivity.newsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewNews);
        tvPostTitle.setText(MainActivity.newsPostTitle);

        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListNews,"readMoreNews");
        recyclerViewMoreAboutNews.setAdapter(allNewsCategoriesAdapter);

        loadMore();

        MainActivity.animationHide();
    }
}

