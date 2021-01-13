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

public class BusinessFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutBusiness;
    GridLayoutManager gridLayoutManager;
    View view;
    TextView tvPostTitle,tvReadMore;
    ImageView imageViewBusiness;
    RelativeLayout imgBackButton;
    LinearLayout lootieAnmationParentlayout;

    Context context;
    List<String> thumbnailUrl;
    String title;
    InterfaceApi interfaceApi;
    Call<List<NewsModel>> callForBusiness;
    ArrayList<NewsModel> arrayListBusiness;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

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
        lootieAnmationParentlayout=view.findViewById(R.id.lootie_animation_parent_layout);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());
        arrayListBusiness = new ArrayList<>();

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

        return view;
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

        getBusinessNews("https://livenewstime.com/wp-json/wp/v2/",6);

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getBusinessNews(String url,int newsCategoryID)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForBusiness = interfaceApi.getAllCategoriesNews(newsCategoryID);
            callForBusiness.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (!response.isSuccessful())
                    {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }
                    arrayListBusiness = (ArrayList<NewsModel>) response.body();

                    try {
                        thumbnailUrl = arrayListBusiness.get(0).getFeaturedMedia();
                        Picasso.with(getActivity()).load(thumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(imageViewBusiness);
                    } catch (Exception e) {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
                    }

                    title = arrayListBusiness.get(0).getTitle();
                    tvPostTitle.setText(title);

                    arrayListBusiness.remove(0);


                    AllNewsCategoriesAdapter allNewsCategoriesAdapter = new AllNewsCategoriesAdapter(getActivity(),arrayListBusiness,"readMoreNews");
                    recyclerViewMoreAboutBusiness.setAdapter(allNewsCategoriesAdapter);

                    parentAnimationHide();

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


