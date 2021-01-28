//package apps.webscare.planetnews.Activities;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.webkit.WebViewCompat;
//
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MenuItem;
//import android.view.View;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.share.Sharer;
//import com.facebook.share.model.ShareLinkContent;
//import com.facebook.share.widget.MessageDialog;
//import com.facebook.share.widget.ShareDialog;
//import com.squareup.picasso.Picasso;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import java.io.IOException;
//import java.net.URI;
//
//import apps.webscare.planetnews.R;
//
//import com.facebook.share.widget.MessageDialog;
//
//public class PostContent extends AppCompatActivity {
//
//    ImageView thumbnailImageView, shareBtn;
//    WebView web;
//    ShareDialog shareDialog;
//    CallbackManager callbackManager;
//    static String networkUrl;
//    ImageView imageViewLoading;
//    public static Boolean webview = false, captureLinkClick;
//    Document document = null;
//    TextView titleTextView, tagsTextView;
//    ShareLinkContent content;
//    LinearLayout goBack, sharePost;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if (web.canGoBack()) {
//                        web.goBack();
//                        new MyAsynTask().execute();
//                    } else {
//                        finish();
//                    }
//                    return true;
//                case KeyEvent.ACTION_DOWN:
//
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    /*@Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        finish();
//        return super.onOptionsItemSelected(item);
//    }*/
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.content_page);
//        thumbnailImageView = findViewById(R.id.newsThumbnailImageViewId);
//        titleTextView = findViewById(R.id.titlePostContentID);
//        tagsTextView = findViewById(R.id.tagsContentID);
//        thumbnailImageView.setClipToOutline(true);
//        web = findViewById(R.id.webViewID);
//        shareDialog = new ShareDialog(this);
//        callbackManager = CallbackManager.Factory.create();
//        sharePost = findViewById(R.id.sharebtnPostID);
//        goBack = findViewById(R.id.backBtnIdPostContent);
//        goBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
////        shareBtn = findViewById(R.id.shareBtnId);
//        content = new ShareLinkContent.Builder()
//                .setContentUrl(Uri.parse(getIntent().getStringExtra("postURL")))
//                .build();
////        shareBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
////                    @Override
////                    public void onSuccess(Sharer.Result result) {
////
////                    }
////
////                    @Override
////                    public void onCancel() {
////
////                    }
////
////                    @Override
////                    public void onError(FacebookException error) {
////
////                    }
////                });
////
////                if (ShareDialog.canShow(ShareLinkContent.class)) {
////                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
////                            .setContentUrl(Uri.parse(getIntent().getStringExtra("postURL")))
////                            .build();
////                    shareDialog.show(linkContent);
////                }
////                MessageDialog.show(PostContent.this  , content);
////            }
////        });
//        imageViewLoading = findViewById(R.id.loadingImageView);
//        WebSettings webSettings = web.getSettings();
//        webSettings.setJavaScriptEnabled(true);
////        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        String thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");
//        networkUrl = getIntent().getStringExtra("postURL");
//        Picasso.with(this).load(thumbnailUrl).placeholder(this.getResources().getDrawable(R.drawable.top_placeholder_image)).into(thumbnailImageView);
//        web.getSettings().setLoadsImagesAutomatically(true);
//        Glide.with(this).load(R.drawable.loading).into(imageViewLoading);
////        web.loadUrl(networkUrl);
//        web.setWebViewClient(new myWebClient());
//        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        if (getIntent().getStringExtra("tags").equals("")){
//            tagsTextView.setVisibility(View.GONE);
//        }else {
//            tagsTextView.setText(getIntent().getStringExtra("tags"));
//        }
//        titleTextView.setText(getIntent().getStringExtra("title"));
//        new MyAsynTask().execute();
//
//        sharePost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareTextUrl();
//            }
//        });
//    }
//
//    public class myWebClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (Uri.parse(url).getHost().equals("planetnews.com")) {
//                new MyAsynTask().execute();
//                return false;
//            } else {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//                return true;
//            }
//        }
//    }
//
//    private class MyAsynTask extends AsyncTask<Void, Void, Document> {
//
//        @Override
//        protected Document doInBackground(Void... voids) {
//            removeViews();
//            return document;
//        }
//
//        private void removeViews() {
//            try {
//                document = Jsoup.connect(networkUrl).get();
//                document.getElementsByClass("td-header-menu-wrap-full").remove();
//                document.getElementsByClass("td-footer-wrapper").remove();
//                document.getElementsByClass("td-footer-container").remove();
//                document.getElementsByClass("td-footer-template-3").remove();
//                document.getElementsByClass("td-sub-footer-container").remove();
//                document.getElementsByClass("comment-form").remove();
//                document.getElementsByClass("td-crumb-container").remove();
//                document.getElementsByClass("td-category").remove();
//                document.getElementsByClass("entry-title").remove();
//                document.getElementsByClass("td-post-featured-image").remove();
////                document.getElementsByClass("td-post-sharing-visible").remove();
//                document.getElementsByClass("comments").remove();
//                document.getElementsByClass("td-post-sharing").remove();
//                document.getElementsByClass("td-ps-bg").remove();
//                document.getElementsByClass("td-ps-notext").remove();
//                document.getElementsByClass("td-post-sharing-style1").remove();
//
////                    document.getElementById("aw0").remove();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(final Document document) {
//            super.onPostExecute(document);
//            web.loadDataWithBaseURL(networkUrl, document.toString(), "text/html", "utf-8", "");
//            web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//            imageViewLoading.setVisibility(View.GONE);
//            web.setVisibility(View.VISIBLE);
//            //after adding below these two lines webview able to load the images and videos
////            web.loadUrl(networkUrl);
//
//           /* web.setWebViewClient(new WebViewClient(){
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    view.loadUrl(networkUrl);
//                    return super.shouldOverrideUrlLoading(view, request);
//                }
//
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    imageViewLoading.setVisibility(View.VISIBLE);
//                    Log.d("url",url);
//                    networkUrl=url;
//                    captureLinkClick=true;
//                    new MyAsynTask().execute();
//                    return true;
//                }
//
//            });*/
//        }
//
//    }
//
//    private void shareTextUrl() {
//        Intent share = new Intent(android.content.Intent.ACTION_SEND);
//        share.setType("text/plain");
//        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//
//        // Add data to the intent, the receiving app will decide
//        // what to do with it.
//        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
//        share.putExtra(Intent.EXTRA_TEXT, networkUrl);
//
//        startActivity(Intent.createChooser(share, "Share link!"));
//    }
//}