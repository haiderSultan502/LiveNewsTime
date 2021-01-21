package com.example.livenewstime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.livenewstime.adpater.LiveChannelsAdapter;
import com.example.livenewstime.models.LiveChannelsModel;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.jarvanmo.exoplayerview.media.ExoMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleQuality;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

import java.util.ArrayList;
import java.util.List;

import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;

public class LiveNewsPlayer extends AppCompatActivity  {



    private ExoVideoView videoView;

    private View playerBelowScreen;

    TextView tvChannelTitle,tvCountryName,tvNewsContent;

    RecyclerView recyclerViewRelatedNewsChannel;

    GridLayoutManager gridLayoutManager;

    int position;
    String channelTitle,countryName,streamingLink,channelDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_news_player);



        videoView = findViewById(R.id.videoView);

        playerBelowScreen = findViewById(R.id.player_below_screen);

        tvChannelTitle = findViewById(R.id.channel_title);
        tvCountryName = findViewById(R.id.country_title);
        tvNewsContent = findViewById(R.id.news_content);

        recyclerViewRelatedNewsChannel = findViewById(R.id.recycler_view_realted_live_channels);

        GridLayoutManager setOrientationToRelatedNewsRecyclerView = setRecyclerViewOrientation();
        recyclerViewRelatedNewsChannel.setLayoutManager(setOrientationToRelatedNewsRecyclerView);

        position = getIntent().getIntExtra("position",0);


        LiveChannelsModel liveChannelsModel = MainActivity.liveChannelsModel;

        channelTitle = liveChannelsModel.getData().get(position).getTitle();

        countryName = liveChannelsModel.getData().get(position).getStudio();

        channelDescription = liveChannelsModel.getData().get(position).getDescription();

        streamingLink = liveChannelsModel.getData().get(position).getVideoUrl();

        videoView.setBackListener((view, isPortrait) -> {
            if (isPortrait) {
                finish();
            }
            return false;
        });

        videoView.setOrientationListener(orientation -> {
            if (orientation == SENSOR_PORTRAIT) {
                changeToPortrait();
            } else if (orientation == SENSOR_LANDSCAPE) {
                changeToLandscape();
            }
        });


//        SimpleMediaSource mediaSource = new SimpleMediaSource("https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4");
        SimpleMediaSource mediaSource = new SimpleMediaSource(streamingLink);

        mediaSource.setDisplayName("Live");

//        videoView.play(mediaSource, false);

        videoView.play(mediaSource);

        tvChannelTitle.setText(channelTitle);
        tvCountryName.setText(countryName);
        tvNewsContent.setText(channelDescription);

//        liveChannelsModel.getData().remove(position);

        LiveChannelsAdapter liveChannelsAdapter = new LiveChannelsAdapter(getApplicationContext(),liveChannelsModel,"livePlayer");
        recyclerViewRelatedNewsChannel.setAdapter(liveChannelsAdapter);




//         this part of code is for add the qualtis of video

//        List<ExoMediaSource.Quality> qualities = new ArrayList<>();
//        ExoMediaSource.Quality quality;
//
//        for (int i = 0; i < 6; i++) {
//            SpannableString spannableString = new SpannableString("Quality" + i);
//            if (i % 2 == 0) {
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);
//                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//
//            } else {
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
//                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            }
//
//            quality = new SimpleQuality(spannableString, mediaSource.uri());
//            qualities.add(quality);
//        }
//        mediaSource.setQualities(qualities);


    }

    private GridLayoutManager setRecyclerViewOrientation() {
        gridLayoutManager=new GridLayoutManager(getApplicationContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return gridLayoutManager;
    }

    private void changeToPortrait() {

        WindowManager.LayoutParams attr = getWindow().getAttributes();
//        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        window.setAttributes(attr);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        playerBelowScreen.setVisibility(View.VISIBLE);
    }


    private void changeToLandscape() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        playerBelowScreen.setVisibility(View.GONE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Build.VERSION.SDK_INT <= 23)) {
            videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            videoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.releasePlayer();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return videoView.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}