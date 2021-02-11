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

public class BusinessFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutBusiness;
    GridLayoutManager gridLayoutManager;
    View view,businessLatestNewsItem;
    TextView tvPostTitle,tvReadMore,tvCategoryName,tvCategoryDetail;
    ImageView imageViewBusiness;
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
    Call<List<NewsModel>> callForBusiness;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    WebsiteView websiteView = new WebsiteView();

    public BusinessFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_business,container,false);
        recyclerViewMoreAboutBusiness=view.findViewById(R.id.recycler_view_more_about_business);
        tvPostTitle = view.findViewById(R.id.tv_title_business);
        imageViewBusiness = view.findViewById(R.id.image_view_business);
        imgBackButton=view.findViewById(R.id.img_back_btn);
        businessLatestNewsItem = view.findViewById(R.id.business_latest_News_Item);
        lootieAnmationParentlayout=view.findViewById(R.id.lootie_animation_parent_layout);
        tvCategoryName = view.findViewById(R.id.categoryNameBusiness);
        tvCategoryDetail = view.findViewById(R.id.categoryDetailBusiness);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

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

        businessLatestNewsItem.setOnClickListener(new View.OnClickListener() {
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
        bundle.putString("newsUrl", MainActivity.arrayListBusinessNews.get(0).getGuid());
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
        recyclerViewMoreAboutBusiness.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        if (MainActivity.getSportsNews == true)
        {
            parentAnimationHide();
            getStoreBusinessNews();
        }
        else
        {
            getBusinessNews(pageNumber);
        }



    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getBusinessNews(int pageNumber)
    {

        categortIDAndPageNumber = " 6 | " +  String.valueOf(pageNumber);

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForBusiness = interfaceApi.getAllCategoriesNews(categortIDAndPageNumber);
            callForBusiness.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        parentAnimationHide();
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    MainActivity.arrayListBusinessNews.addAll((ArrayList<NewsModel>) response.body());

                    MainActivity.getBusinessNews = true;

                    if (pageNumber == 1)
                    {
                        MainActivity.categoryNameBusiness = MainActivity.arrayListCategoryDetails.get(1).getName();

                        MainActivity.categoryDetailsBusiness = MainActivity.arrayListCategoryDetails.get(1).getDescription();



                        tvCategoryName.setText(MainActivity.categoryNameBusiness);

                        if (MainActivity.categoryDetailsBusiness.length() == 0 )
                        {
                            tvCategoryDetail.setVisibility(View.GONE);
                        }
                        else
                        {
                            tvCategoryDetail.setVisibility(View.VISIBLE);
                            tvCategoryDetail.setText(MainActivity.categoryDetailsBusiness);
                        }


                        MainActivity.businessThumbnailUrl = MainActivity.arrayListBusinessNews.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(MainActivity.businessThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewBusiness);

                        MainActivity.businessPostTitle = MainActivity.arrayListBusinessNews.get(0).getTitle();
                        tvPostTitle.setText(MainActivity.businessPostTitle);

                        MainActivity.arrayListBusinessNews.remove(0);


                        allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListBusinessNews,"readMoreNews");
                        recyclerViewMoreAboutBusiness.setAdapter(allNewsCategoriesAdapter);
                    }

                    allNewsCategoriesAdapter.notifyDataSetChanged();
                    allNewsCategoriesAdapter.notifyItemRangeInserted(allNewsCategoriesAdapter.getItemCount() , MainActivity.arrayListBusinessNews.size());

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

        recyclerViewMoreAboutBusiness.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                getBusinessNews(pageNumber);

                progressBar.setVisibility(View.GONE);

            }
        },5000);
    }

    private void getStoreBusinessNews() {

        tvCategoryName.setText(MainActivity.categoryNameBusiness);

        if (MainActivity.categoryDetailsBusiness.length() == 0 )
        {
            tvCategoryDetail.setVisibility(View.GONE);
        }
        else
        {
            tvCategoryDetail.setVisibility(View.VISIBLE);
            tvCategoryDetail.setText(MainActivity.categoryDetailsBusiness);
        }


        Picasso.with(getActivity()).load(MainActivity.businessThumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewBusiness);
        tvPostTitle.setText(MainActivity.businessPostTitle);

        AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),MainActivity.arrayListBusinessNews,"readMoreNews");
        recyclerViewMoreAboutBusiness.setAdapter(allNewsCategoriesAdapter);

        loadMore();
    }
}


