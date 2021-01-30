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
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.potyvideo.library.AndExoPlayerView;
import com.potyvideo.library.globalEnums.EnumResizeMode;

import java.util.ArrayList;
import java.util.List;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoView;

import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;

public class LiveNewsPlayer extends AppCompatActivity {



    //    private ExoVideoView videoView;
//    VideoView videoView;
    AndExoPlayerView andExoPlayerView;

    ImageButton imageButton;
    private View playerBelowScreen;

    TextView tvChannelTitle,tvCountryName,tvNewsContent;

    RecyclerView recyclerViewRelatedNewsChannel;

    GridLayoutManager gridLayoutManager;

    ActionBar actionBar;

    int position;
    String channelTitle,countryName,streamingLink,channelDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_news_player);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);

        imageButton = findViewById(R.id.imageBtn);

        andExoPlayerView.setShowController(true);



        imageButton.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

            public void onSwipeTop() {
//                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
//                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
//                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
//                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
                pictureInPictureMode();
            }

        });




        andExoPlayerView.setResizeMode(EnumResizeMode.FILL);


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

        andExoPlayerView.setSource(streamingLink);

        tvChannelTitle.setText(channelTitle);
        tvCountryName.setText(countryName);
        tvNewsContent.setText(channelDescription);



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


    }


    private void pictureInPictureMode()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Calculate the aspect ratio of the PiP screen.
            Rational aspectRatio = new Rational(500, 210);
            PictureInPictureParams.Builder mPictureInPictureParamsBuilder  = new PictureInPictureParams.Builder();
            mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
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

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            andExoPlayerView.setShowController(true);
//            Toast.makeText(getApplicationContext(), "controller", Toast.LENGTH_SHORT).show();

            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}

}