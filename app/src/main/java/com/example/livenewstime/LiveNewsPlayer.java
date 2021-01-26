package com.example.livenewstime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Rational;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class LiveNewsPlayer extends AppCompatActivity implements GestureDetector.OnGestureListener {



    private ExoVideoView videoView;

    private View playerBelowScreen;

    TextView tvChannelTitle,tvCountryName,tvNewsContent;

    RecyclerView recyclerViewRelatedNewsChannel;

    GridLayoutManager gridLayoutManager;

    ActionBar actionBar;

    int position;
    String channelTitle,countryName,streamingLink,channelDescription;

    static final String TAG = "swipe positoion";
    float x1,x2,y1,y2;
    static int MIN_DISTANCE = 150;
    GestureDetector gestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_news_player);

        this.gestureDetector = new GestureDetector(LiveNewsPlayer.this,this);

        videoView = findViewById(R.id.videoView);

        actionBar = getActionBar();


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

//        videoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(LiveNewsPlayer.this, "get ", Toast.LENGTH_SHORT).show();
//            }
//        });


//        SimpleMediaSource mediaSource = new SimpleMediaSource("https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4");
        SimpleMediaSource mediaSource = new SimpleMediaSource(streamingLink);

        mediaSource.setDisplayName("Live");

//        videoView.play(mediaSource, false);

        videoView.play(mediaSource);

        tvChannelTitle.setText(channelTitle);
        tvCountryName.setText(countryName);
        tvNewsContent.setText(channelDescription);




//        videoView.setOnTouchListener(new View.OnTouchListener() {
//            float dX, dY;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                if (event.getAction() == MotionEvent.ACTION_MOVE)
//                {
//
//                        pictureInPictureMode();
//                        Toast.makeText(LiveNewsPlayer.this, "get", Toast.LENGTH_SHORT).show();
//
//                    return true;
//                }
//                return true ;
//            }
//        });


//        liveChannelsModel.getData().remove(position);

        LiveChannelsAdapter liveChannelsAdapter = new LiveChannelsAdapter(getApplicationContext(),liveChannelsModel);
        recyclerViewRelatedNewsChannel.setAdapter(liveChannelsAdapter);

        liveChannelsAdapter.setOnItemClickListener(new LiveChannelsAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                finish();
                Intent intent = new Intent(LiveNewsPlayer.this, LiveNewsPlayer.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });




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
        tvChannelTitle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                Toast.makeText(LiveNewsPlayer.this, "Title", Toast.LENGTH_SHORT).show();

                pictureInPictureMode();

//                Display d = getWindowManager()
//                        .getDefaultDisplay();
//                Point p = new Point();
//                d.getSize(p);
//                int width = p.x;
//                int height = p.y;
//
//                Rational ratio
//                        = new Rational(width, height);
//                PictureInPictureParams.Builder
//                        pip_Builder
//                        = new PictureInPictureParams
//                        .Builder();
//
//                pip_Builder.setAspectRatio(ratio).build();
//                enterPictureInPictureMode(pip_Builder.build());
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        gestureDetector.onTouchEvent(event);

        switch (event.getAction())
        {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:

                x2 = event.getX();
                y2 = event.getY();

                float valueX = x2 - x1 ;
                float valueY = y2 - y1 ;

                if (Math.abs(valueX) > MIN_DISTANCE)
                {
                    if (x2 > x1 )
                    {
                        Toast.makeText(this, "rightdd Swiped", Toast.LENGTH_SHORT).show();
                        pictureInPictureMode();
                    }
                    else
                    {
                        Toast.makeText(this, "Leftdd swiped", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (Math.abs(valueY) > MIN_DISTANCE)
                {
                    if (y2 > y1 )
                    {
                        Toast.makeText(this, "bottom Swiped", Toast.LENGTH_SHORT).show();
                        pictureInPictureMode();
                    }
                    else
                    {
                        Toast.makeText(this, "top swiped", Toast.LENGTH_SHORT).show();
//                        pictureInPictureMode();
                    }
                }

        }








        return super.onTouchEvent(event);
    }

    private void pictureInPictureMode() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        // Calculate the aspect ratio of the PiP screen.
        Rational aspectRatio = new Rational(350, 180);
        PictureInPictureParams.Builder mPictureInPictureParamsBuilder  = new PictureInPictureParams.Builder();
        mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());


//            Display d = getWindowManager().getDefaultDisplay();
//                Point p = new Point();
//                d.getSize(p);
//                int width = p.x;
//                int height = p.y;
//
//                Rational ratio
//                        = new Rational(width, height);
//                PictureInPictureParams.Builder
//                        pip_Builder
//                        = new PictureInPictureParams
//                        .Builder();
//
//                pip_Builder.setAspectRatio(ratio).build();
//                enterPictureInPictureMode(pip_Builder.build());

        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode)
        {
//            actionBar.hide();
            playerBelowScreen.setVisibility(View.GONE);
//            videoView.setVisibility(View.VISIBLE);


        }
        else
        {
//            actionBar.show();

            playerBelowScreen.setVisibility(View.VISIBLE);
        }
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}