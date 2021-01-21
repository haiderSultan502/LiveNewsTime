package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.livenewstime.adpater.LiveChannelsAdapter;
import com.example.livenewstime.models.FragmentDetailModel;
import com.example.livenewstime.models.LiveChannelsModel;
import com.example.livenewstime.models.NewsModel;
import com.example.livenewstime.otherClasses.RetrofitLibrary;
import com.example.livenewstime.otherClasses.SweetAlertDialogGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveNewsFragment extends Fragment {

    RecyclerView recyclerViewLiveNewsChannels;
    GridLayoutManager gridLayoutManager;
    View view;
    TextView tvLiveChannelDescription,tvCategoryName;

    Context context;
    InterfaceApi interfaceApi;
    Call<LiveChannelsModel> callForLiveChannels;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    Call<List<FragmentDetailModel>> callForFragmentDetails;
    String categoryName;

    public LiveNewsFragment()
    {

    }

    public LiveNewsFragment(Context context) {
        this.context= context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_live_news,container,false);
        recyclerViewLiveNewsChannels=view.findViewById(R.id.recycler_view_live_channels);
        tvLiveChannelDescription = view.findViewById(R.id.tv_live_channel_description);
        tvCategoryName = view.findViewById(R.id.categoryName);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(getActivity());

        setDataInViews();

        if (MainActivity.getLiveNews == true)
        {
            MainActivity.animationHide();
            getFragmentDetail();
            getStoreLiveNews();
        }

        return view;

    }

    public void getFragmentDetail()
    {
        {

            try {
                interfaceApi = RetrofitLibrary.connect("https://livenewstime.com/wp-json/Newspaper/v2/");
                callForFragmentDetails = interfaceApi.getFragmnetDetail();
                callForFragmentDetails.enqueue(new Callback<List<FragmentDetailModel>>() {
                    @Override
                    public void onResponse(Call<List<FragmentDetailModel>> call, Response<List<FragmentDetailModel>> response) {
                        if (!response.isSuccessful())
                        {
                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                            return;
                        }
                        MainActivity.arrayListListFragmentDetails = (ArrayList<FragmentDetailModel>) response.body();

                        MainActivity.getFragmentDetails =  true;

                        categoryName = MainActivity.arrayListListFragmentDetails.get(15).getName();

                        tvCategoryName.setText(categoryName);
                    }

                    @Override
                    public void onFailure(Call<List<FragmentDetailModel>> call, Throwable t) {
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


    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewLiveNewsChannels.setLayoutManager(setOrientationToLatestNewsRecyclerView);

        getFragmentDetail();
        getLiveNewsChannels("https://app.newslive.com/newslive/api/");

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(context,3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    public void getLiveNewsChannels(String url)
    {

        try {
            interfaceApi = RetrofitLibrary.connect(url);
            callForLiveChannels = interfaceApi.getLiveChannels();
            callForLiveChannels.enqueue(new Callback<LiveChannelsModel>() {
                @Override
                public void onResponse(Call<LiveChannelsModel> call, Response<LiveChannelsModel> response) {
                    if (!response.isSuccessful())
                    {
                        MainActivity.animationHide();
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }

                    MainActivity.liveChannelsModel = response.body();

                    MainActivity.getLiveNews = true;


                    LiveChannelsAdapter liveChannelsAdapter = new LiveChannelsAdapter(context,MainActivity.liveChannelsModel,"livePlayers");
                    recyclerViewLiveNewsChannels.setAdapter(liveChannelsAdapter);

                    MainActivity.animationHide();
                }

                @Override
                public void onFailure(Call<LiveChannelsModel> call, Throwable t) {
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

    private void getStoreLiveNews() {

        categoryName = MainActivity.arrayListListFragmentDetails.get(15).getName();

        tvCategoryName.setText(categoryName);

        LiveChannelsAdapter liveChannelsAdapter = new LiveChannelsAdapter(context,MainActivity.liveChannelsModel,"livePlayers");
        recyclerViewLiveNewsChannels.setAdapter(liveChannelsAdapter);
    }

}
