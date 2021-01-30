package com.example.livenewstime.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
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

public class PoliticsFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutPolitics;
    GridLayoutManager gridLayoutManager;
    View view,politicsLatestNewsItem;
    TextView tvPostTitle,tvReadMore,tvCategoryName,tvCategoryDetail;
    ImageView imageViewrPoliticsNews;
    WebsiteView websiteView = new WebsiteView();

    ProgressBar progressBar;
    String url= "https://livenewstime.com/wp-json/wp/v2/";
    int pageNumber = 1;
    String categortIDAndPageNumber;
    Boolean isScrooling = false;
    int currentItem,totalItems,scrollOutItems;
    AllNewsCategoriesAdapter homeMoreNewsAdapter;


    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForpoliticsNews;
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
        politicsLatestNewsItem = view.findViewById(R.id.politics_latest_News_Item);

        tvCategoryName = view.findViewById(R.id.categoryNamePolitics);
        tvCategoryDetail = view.findViewById(R.id.categoryDetailPolitics);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());


        progressBar = view.findViewById(R.id.progress_bar);

        setProgressBarColor();

        MainActivity.animationShow();



        setDataInViews();



        politicsLatestNewsItem.setOnClickListener(new View.OnClickListener() {
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
        bundle.putString("newsUrl",MainActivity.arrayListPoliticsNews.get(0).getGuid());
        websiteView.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                .commit();
    }

    private void setDataInViews() {



        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutPolitics.setLayoutManager(setOrientationToLatestNewsRecyclerView);


        if (MainActivity.getPoloiticsNews == true)
        {
            getStorePoliticsNews();
        }
        else
        {
            getPoliticsNews(pageNumber);
        }




    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getPoliticsNews(int pageNumber)
    {

        categortIDAndPageNumber = " 14 | " +  String.valueOf(pageNumber);



        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForpoliticsNews = interfaceApi.getAllCategoriesNews(categortIDAndPageNumber);
            callForpoliticsNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        MainActivity.animationHide();
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }

                    MainActivity.arrayListPoliticsNews.addAll((ArrayList<NewsModel>) response.body());


                    MainActivity.getPoloiticsNews = true;

                    if (pageNumber ==  1)
                    {

                        MainActivity.categoryNamepolitics = MainActivity.arrayListCategoryDetails.get(9).getName();

                        MainActivity.categoryDetailsPolitics = MainActivity.arrayListCategoryDetails.get(9).getDescription();

                        tvCategoryName.setText(MainActivity.categoryNamepolitics);

                        if (MainActivity.categoryDetailsPolitics.length() < 1)
                        {
                            tvCategoryDetail.setText(getString(R.string.about_politics));
                        }
                        else
                        {
                            tvCategoryDetail.setText(MainActivity.categoryDetailsPolitics);
                        }

                        MainActivity.politicsThumbnailUrl = MainActivity.arrayListPoliticsNews.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(MainActivity.politicsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrPoliticsNews);

                        MainActivity.politicsPostTitle = MainActivity.arrayListPoliticsNews.get(0).getTitle();
                        tvPostTitle.setText(MainActivity.politicsPostTitle);

                        MainActivity.arrayListPoliticsNews.remove(0);

                        homeMoreNewsAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListPoliticsNews,"readMoreNews");
                        recyclerViewMoreAboutPolitics.setAdapter(homeMoreNewsAdapter);
                    }



                    homeMoreNewsAdapter.notifyDataSetChanged();
                    homeMoreNewsAdapter.notifyItemRangeInserted(homeMoreNewsAdapter.getItemCount() , MainActivity.arrayListPoliticsNews.size());

                    MainActivity.animationHide();

                    loadMore();

                }

                @Override
                public void onFailure(Call<List<NewsModel>> call, Throwable t) {

                    MainActivity.animationHide();
                    sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            MainActivity.animationHide();
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }

    }

    private void loadMore() {
        recyclerViewMoreAboutPolitics.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//    public void getPoliticsNews(String url,int pageNumber)
//    {
//
//        String categortIDAndPageNumber = " 14 | " +  String.valueOf(pageNumber);
//
//        try {
//            interfaceApi = RetrofitLibrary.connect(url);
//            callForpoliticsNews = interfaceApi.getAllCategoriesNewss(categortIDAndPageNumber);
//            callForpoliticsNews.enqueue(new Callback<List<NewsModel>>() {
//                @Override
//                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
//                    if (!response.isSuccessful())
//                    {
//                        MainActivity.animationHide();
//                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
//                        return;
//                    }
//
//                    MainActivity.arrayListPoliticsNews.addAll((ArrayList<NewsModel>) response.body()) ;
//
//                    homeMoreNewsAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListPoliticsNews,"readMoreNews");
//                    recyclerViewMoreAboutPolitics.setAdapter(homeMoreNewsAdapter);
//
//                    recyclerViewMoreAboutPolitics.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override   //method called when scrolling start
//                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                            super.onScrollStateChanged(recyclerView, newState);
//                            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//                            {
//                                isScrooling = true;
//                            }
//                        }
//
//                        @Override   //method called when scrolling end
//                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                            super.onScrolled(recyclerView, dx, dy);
//
//                            currentItem = gridLayoutManager.getChildCount();
//                            totalItems = gridLayoutManager.getItemCount();
//                            scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
//
//                            if (isScrooling && (currentItem + scrollOutItems == totalItems))
//                            {
//                                isScrooling = false;
//                                fetchData();
//                            }
//                        }
//                    });
//
//                }
//
//                @Override
//                public void onFailure(Call<List<NewsModel>> call, Throwable t) {
//
//                    MainActivity.animationHide();
//                    sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
//                }
//            });
//        }
//        catch (Exception e)
//        {
//            MainActivity.animationHide();
//            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
//        }
//
//    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNumber++;
                getPoliticsNews(pageNumber);

                progressBar.setVisibility(View.GONE);

            }
        },5000);
    }

    void getStorePoliticsNews()
    {
        tvCategoryName.setText(MainActivity.categoryNamepolitics);

        if (MainActivity.categoryDetailsPolitics.length() < 1)
        {
            tvCategoryDetail.setText(getString(R.string.about_politics));
        }
        else
        {
            tvCategoryDetail.setText(MainActivity.categoryDetailsPolitics);
        }

        Picasso.with(getActivity()).load(MainActivity.politicsThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrPoliticsNews);
        tvPostTitle.setText(MainActivity.politicsPostTitle);

        homeMoreNewsAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListPoliticsNews,"readMoreNews");
        recyclerViewMoreAboutPolitics.setAdapter(homeMoreNewsAdapter);

        loadMore();

        MainActivity.animationHide();
    }
}

