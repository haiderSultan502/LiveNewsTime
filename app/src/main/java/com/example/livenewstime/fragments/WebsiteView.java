package com.example.livenewstime.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.livenewstime.R;

public class WebsiteView extends Fragment {
    View view;
    WebView webView;
    Bundle bundle;
    String newsUrl;
    RelativeLayout imgBackButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.web_view_layout, container, false);

        webView=view.findViewById(R.id.web_view);
        imgBackButton=view.findViewById(R.id.img_back_btn);


        bundle=getArguments();
        newsUrl=bundle.getString("newsUrl");

        webView.loadUrl(newsUrl);

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



}
