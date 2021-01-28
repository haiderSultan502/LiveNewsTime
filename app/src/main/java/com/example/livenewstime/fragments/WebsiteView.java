package com.example.livenewstime.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.livenewstime.MainActivity;
import com.example.livenewstime.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebsiteView extends Fragment {
    View view;
    WebView webView;
    RelativeLayout imgBackButton,imgShareButton;
    public static LinearLayout lootieAnmationParentlayout;
    public static Boolean captureLinkClick;

    Bundle bundle;
    static  String newsUrl;
    Document document = null;
    String facebookPageLink = "https://www.facebook.com/";


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
        lootieAnmationParentlayout=view.findViewById(R.id.lootie_animation_parent_layout);
        imgShareButton=view.findViewById(R.id.share_btn);


        parentAnimationShow();

        bundle=getArguments();
        newsUrl=bundle.getString("newsUrl");


        captureLinkClick=false;
        new MyAsynTask().execute();

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        imgShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent toFacebookPage = new Intent(Intent.ACTION_VIEW);
//                toFacebookPage.setData(Uri.parse(facebookPageLink));
//                startActivity(toFacebookPage);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, newsUrl);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });

        return view;
    }

    private class MyAsynTask extends AsyncTask<Void,Void, Document>
    {


        @Override
        protected Document doInBackground(Void... voids) {
//            if (captureLinkClick==false) {

                try {
                    document = Jsoup.connect(newsUrl).get();

                    document.getElementsByClass("td-header-template-wrap").remove();
                    document.getElementsByClass("td-footer-template-wrap").remove();
                    document.getElementById("comments").remove();

                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//            else {
//                try {
//                    document= Jsoup.connect(newsUrl).get();
//                    Log.d("NetworkUrl",newsUrl);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }



            return document;
        }

        @Override
        protected void onPostExecute(final Document document) {
            super.onPostExecute(document);

            webView.loadDataWithBaseURL(newsUrl,document.toString(),"text/html","utf-8","");
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

//            after adding below these two lines webview able to load the images and videos
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setUseWideViewPort(true);


//            webView.loadUrl(newsUrl);
//
            parentAnimationHide();

            webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                    view.loadUrl(newsUrl);

                    parentAnimationHide();

                    return super.shouldOverrideUrlLoading(view, request);
                }
//
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                    view.loadUrl(newsUrl);
//
//                    parentAnimationHide();
////                    newsUrl=url;
////                    captureLinkClick=true;
//                    new MyAsynTask().execute();
//                    return super.shouldOverrideUrlLoading(view, url);
//                }
            });
        }

    }

    public  static void parentAnimationShow() {
        lootieAnmationParentlayout.setVisibility(View.VISIBLE);
    }
    public  static void parentAnimationHide() {
        lootieAnmationParentlayout.setVisibility(View.GONE);
    }



}
