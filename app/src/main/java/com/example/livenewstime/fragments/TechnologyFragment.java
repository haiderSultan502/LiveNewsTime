package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.MainActivity;
import com.example.livenewstime.R;
import com.example.livenewstime.adpater.HomeLatestNewsAdapter;
import com.example.livenewstime.adpater.PoliticsAdapter;
import com.example.livenewstime.adpater.TechnologyAdapter;

public class TechnologyFragment extends Fragment {

    RecyclerView recyclerViewMoreAboutTechnology;
    GridLayoutManager gridLayoutManager;
    View view;
    MainActivity mainActivity;

    public TechnologyFragment(Context context) {
        this.mainActivity= (MainActivity) context;
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

        setDataInViews();

        return view;
    }

    private void setDataInViews() {

        GridLayoutManager setOrientationToLatestNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewMoreAboutTechnology.setLayoutManager(setOrientationToLatestNewsRecyclerView);
        TechnologyAdapter technologyAdapter =new TechnologyAdapter(mainActivity);
        recyclerViewMoreAboutTechnology.setAdapter(technologyAdapter);

    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(mainActivity,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }
}

