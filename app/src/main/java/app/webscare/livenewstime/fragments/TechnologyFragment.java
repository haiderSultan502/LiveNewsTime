package app.webscare.livenewstime.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.webscare.livenewstime.Interface.InterfaceApi;
import app.webscare.livenewstime.MainActivity;
import app.webscare.livenewstime.R;
import app.webscare.livenewstime.adpater.AllNewsCategoriesAdapter;
import app.webscare.livenewstime.models.NewsModel;
import app.webscare.livenewstime.otherClasses.RetrofitLibrary;
import app.webscare.livenewstime.otherClasses.SweetAlertDialogGeneral;
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
    TextView tvPostTitle,tvReadMore,tvCategoryName,tvCategoryDetail;
    ImageView imageViewrtechnologyNews;
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
        tvCategoryName = view.findViewById(R.id.categoryNametechnology);
        tvCategoryDetail = view.findViewById(R.id.categoryDetailTechnology);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        progressBar = view.findViewById(R.id.progress_bar);

        setProgressBarColor();

        MainActivity.animationShow();
        
        setDataInViews();



        technologyLatestNewsItem.setOnClickListener(new View.OnClickListener() {
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
        bundle.putString("newsUrl",MainActivity.arrayListTechnologyNews.get(0).getGuid());
        websiteView.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                .commit();
    }

    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutTechnology.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        if (MainActivity.getTechnologyNews == true)
        {
            getStoreTechnologyNews();
        }
        else
        {
            getTechnologyNews(pageNumber);
        }

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getTechnologyNews(int pageNumber)
    {

        categortIDAndPageNumber = " 3 | " +  String.valueOf(pageNumber);

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForTechnologyNews = interfaceApi.getAllCategoriesNews(categortIDAndPageNumber);
            callForTechnologyNews.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    MainActivity.arrayListTechnologyNews.addAll((ArrayList<NewsModel>) response.body());
                    
                    MainActivity.getTechnologyNews = true;

                    if (pageNumber == 1)
                    {
                        MainActivity.categoryNameTechnolohy = MainActivity.arrayListCategoryDetails.get(12).getName();

                        MainActivity.categoryDetailsTechnology = MainActivity.arrayListCategoryDetails.get(12).getDescription();

                        tvCategoryName.setText(MainActivity.categoryNameTechnolohy);

                        if (MainActivity.categoryDetailsTechnology.length() == 0 )
                        {
                            tvCategoryDetail.setVisibility(View.GONE);
                        }
                        else
                        {
                            tvCategoryDetail.setVisibility(View.VISIBLE);
                            tvCategoryDetail.setText(MainActivity.categoryDetailsTechnology);
                        }


                        MainActivity.technologyThumbnailUrl = MainActivity.arrayListTechnologyNews.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(MainActivity.technologyThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrtechnologyNews);

                        MainActivity.technologyPostTitle = MainActivity.arrayListTechnologyNews.get(0).getTitle();
                        tvPostTitle.setText(MainActivity.technologyPostTitle);

                        MainActivity.arrayListTechnologyNews.remove(0);


                        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListTechnologyNews,"readMoreNews");
                        recyclerViewMoreAboutTechnology.setAdapter(allNewsCategoriesAdapter); 
                    }
                    
                    allNewsCategoriesAdapter.notifyDataSetChanged();
                    allNewsCategoriesAdapter.notifyItemRangeInserted(allNewsCategoriesAdapter.getItemCount() , MainActivity.arrayListTechnologyNews.size());
                    

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
        recyclerViewMoreAboutTechnology.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                getTechnologyNews(pageNumber);

                progressBar.setVisibility(View.GONE);

            }
        },5000);
    }

    private void getStoreTechnologyNews() {

        tvCategoryName.setText(MainActivity.categoryNameTechnolohy);

        if (MainActivity.categoryDetailsTechnology.length() == 0 )
        {
            tvCategoryDetail.setVisibility(View.GONE);
        }
        else
        {
            tvCategoryDetail.setVisibility(View.VISIBLE);
            tvCategoryDetail.setText(MainActivity.categoryDetailsTechnology);
        }

        Picasso.with(getActivity()).load(MainActivity.technologyThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewrtechnologyNews);
        tvPostTitle.setText(MainActivity.technologyPostTitle);

        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListTechnologyNews,"readMoreNews");
        recyclerViewMoreAboutTechnology.setAdapter(allNewsCategoriesAdapter);
        loadMore();

        MainActivity.animationHide();
    }
}

