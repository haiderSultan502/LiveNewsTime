    package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

    public class HomeFragment extends Fragment {

    RecyclerView recyclerViewLatestNews,recyclerViewMoretNews;
    LinearLayoutManager linearLayoutManager;
    View homeRecentNewsItem;
    View view;
    TextView tvPostTitle,tvReadMore,tvLatestNews;
    ImageView imageViewrRecentNews;
    WebsiteView websiteView = new WebsiteView();

    Context context;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForNews;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    Animation animation;
//    public static LinearLayout lootieAnimaationLayout;


    public HomeFragment()
    {

    }


    public HomeFragment(Context context) {
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_home,container,false);

        tvLatestNews=view.findViewById(R.id.latest_news);

        tvPostTitle = view.findViewById(R.id.tv_post_title);
        tvReadMore = view.findViewById(R.id.tv_readmore_recent_news);
        imageViewrRecentNews = view.findViewById(R.id.image_view_recent_news);
        homeRecentNewsItem = view.findViewById(R.id.home_Recent_News_Item);
        recyclerViewLatestNews=view.findViewById(R.id.recycler_view_latest_news);
        recyclerViewMoretNews=view.findViewById(R.id.recycler_view_more_news);
//        lootieAnimaationLayout=view.findViewById(R.id.lootie_animation_layout);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        setDataInViews();

        if (MainActivity.getHomeNews == true)
        {

            getStoreHomeNews();
        }

        homeRecentNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        return view;
    }
        private void replaceFragment() {

            Bundle bundle = new Bundle();
            bundle.putString("newsUrl",MainActivity.arrayListHomeNews.get(3).getGuid());
            websiteView.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                    .commit();
        }

//        public static void animationShow()
//        {
//            lootieAnimaationLayout.setVisibility(View.VISIBLE);
//        }
//
//        public static void animationHide()
//        {
//            lootieAnimaationLayout.setVisibility(View.GONE);
//        }

    private void setDataInViews() {

        LinearLayoutManager setOrientationToLatestNewsRecyclerView =setRecyclerViewOrientation();
        recyclerViewLatestNews.setLayoutManager(setOrientationToLatestNewsRecyclerView);
        recyclerViewLatestNews.setHorizontalScrollBarEnabled(false);


        LinearLayoutManager setOrientationToMoreNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoretNews.setLayoutManager(setOrientationToMoreNewsRecyclerView);

        getNews("https://livenewstime.com/wp-json/newspaper/v2/");

        recyclerViewScrollHorizontalAnimation();



    }

        private void recyclerViewScrollHorizontalAnimation() {

            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.recycler_view_horizontal_scroll_anim);
            recyclerViewMoretNews.setAnimation(animation);
        }

        private LinearLayoutManager setRecyclerViewOrientation() {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }

//        @Override
//        public void onResume() {
//            super.onResume();
//            MainActivity.animationShow();
//        }

        public void getNews(String url)
        {
            Log.d("TAG", "animation called ");
            MainActivity.animationShow();

            try {
                interfaceApi = RetrofitLibrary.connect(url);
                callForNews = interfaceApi.getHomeNews();
                callForNews.enqueue(new Callback<List<NewsModel>>() {
                    @Override
                    public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                        if (!response.isSuccessful())
                        {
                            MainActivity.animationHide();
                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                            return;
                        }
                        MainActivity.arrayListHomeNews = (ArrayList<NewsModel>) response.body();

                        MainActivity.getHomeNews = true;

                        for (int i = 0 ; i < 3 ; i++)
                        {
                            MainActivity.arrayListLatestHomeNews.add(MainActivity.arrayListHomeNews.get(i));
                            MainActivity.arrayListHomeNews.remove(i);
                        }



                        AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListLatestHomeNews,"latestNews");
                        recyclerViewLatestNews.setAdapter(allNewsCategoriesAdapter);

                        MainActivity.homeThumbnailUrl = MainActivity.arrayListHomeNews.get(3).getFeaturedMedia();
                        Picasso.with(getActivity()).load(MainActivity.homeThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrRecentNews);

                        MainActivity.homePostTitle = MainActivity.arrayListHomeNews.get(3).getTitle();
                        tvPostTitle.setText(MainActivity.homePostTitle);

                        MainActivity.arrayListHomeNews.remove(3);

                        AllNewsCategoriesAdapter homeMoreNewsAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListHomeNews,"moreNews");
                        recyclerViewMoretNews.setAdapter(homeMoreNewsAdapter);

                        MainActivity.animationHide();

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

        void getStoreHomeNews()
        {

            AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListLatestHomeNews,"latestNews");
            recyclerViewLatestNews.setAdapter(allNewsCategoriesAdapter);

            Picasso.with(getActivity()).load(MainActivity.homeThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrRecentNews);
            tvPostTitle.setText(MainActivity.homePostTitle);

            AllNewsCategoriesAdapter homeMoreNewsAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListHomeNews,"moreNews");
            recyclerViewMoretNews.setAdapter(homeMoreNewsAdapter);

            MainActivity.animationHide();

            recyclerViewScrollHorizontalAnimation();




        }
    }
