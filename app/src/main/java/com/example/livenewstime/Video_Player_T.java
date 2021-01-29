//package com.example.livenewstime;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.mediarouter.app.MediaRouteButton;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.database.Cursor;
//import android.media.MediaMetadata;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import tcking.github.com.giraffeplayer2.VideoInfo;
//import tcking.github.com.giraffeplayer2.VideoView;
//
//
//public class Video_Player_T extends AppCompatActivity {
//    VideoView videoView;
//    String channelTitle;
//    private ImageButton topbackbtn;
//    private PlaybackState mPlaybackState;
//    private PlaybackLocation mLocation;
//    private Timer mControllersTimer;
//    private View mControllers;
//    private Context mContext;
//    private final Handler mHandler = new Handler();
//    final String Urlnew = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
//    private boolean mControllersVisible;
//    private LinearLayout backButtonLayoutVideoPlayer;
//    ImageView playPauseBtn ;
//    //    LinearLayout backBtn;
//    RecyclerView relatedChannelsRv;
//    TextView title , description , tagsTextView;
//
//
//    //    android.graphics.drawable.AdaptiveIconDrawable@
//    public enum PlaybackLocation {
//        LOCAL,
//        REMOTE
//    }
//
//    /**
//     * List of various states that we can be in
//     */
//    public enum PlaybackState {
//        PLAYING, PAUSED, BUFFERING, IDLE
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video__player__t);
//
//        videoView = (VideoView) findViewById(R.id.video_view);
////        videoView.setVideoPath(Urlnew).getPlayer().start();
//        videoView.getVideoInfo().setAspectRatio(3);
//        relatedChannelsRv = findViewById(R.id.relatedChannelsRVIdLive);
//        title = findViewById(R.id.channelTitleIdVideoPlayer);
//        description = findViewById(R.id.channelDescTvIdVideoPlayer);
//        tagsTextView = findViewById(R.id.tagsTextViewIDVideoPlayer);
//        title.setText(getIntent().getStringExtra("Title"));
//        description.setText(getIntent().getStringExtra("Description"));
//        tagsTextView.setText(getIntent().getStringExtra("tags"));
//        relatedChannelsRv.setLayoutManager(new GridLayoutManager(Video_Player_T.this, 3 ));
//        relatedChannelsRv.setHasFixedSize(true);
//
//        playPauseBtn = findViewById(R.id.playPauseImageView);
//        playPauseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
////        alternateStreamBtn = findViewById(R.id.alternateStreamTextViewId);
////        primaryStreamBtn = findViewById(R.id.primaryStreamBtnId);
//        backButtonLayoutVideoPlayer = findViewById(R.id.backButtonLayoutVideoPlayerID);
//        backButtonLayoutVideoPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        mControllers = findViewById(R.id.controllers_video);
//        channelTitle = getIntent().getStringExtra("name");
//        videoView = (VideoView) findViewById(R.id.video_view);
//        videoView.setVideoPath(getIntent().getStringExtra("videoUrl")).getPlayer().start();
//
//
//        boolean shouldStartPlayback = true;
//        int startPosition = 0;
//        if (shouldStartPlayback) {
//            // this will be the case only if we are coming from the
//            // CastControllerActivity by disconnecting from a device
//            mPlaybackState = PlaybackState.PLAYING;
//            updatePlaybackLocation(PlaybackLocation.LOCAL);
//            if (startPosition > 0) {
//                videoView.getPlayer().seekTo(startPosition);
//            }
//            videoView.getPlayer().start();
//            startControllersTimer();
//        } else {
//            // we should load the video but pause it
//            // and show the album art.
//            mPlaybackState = PlaybackState.IDLE;
//        }
//
//        mContext = getApplicationContext();
//
//        topbackbtn = findViewById(R.id.vtpb);
//        topbackbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
////        playertopbar = findViewById(R.id.player_topbar);
//      /*  if (mColumnCount <= 1) {
//            recycleViewraleted.setLayoutManager(new LinearLayoutManager(mContext));
//        } else {
//            recycleViewraleted.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
//        }*/
//       /* recycleViewraleted.setHasFixedSize(true);
//        recycleViewraleted.setItemAnimator(new DefaultItemAnimator());
//        channelname.setText(channelTitle);
//        mActivity = this;*/
//
//        // videoView.getPlayer().setDisplayModel(GiraffePlayer.DISPLAY_FLOAT);
//        //	hideSystemUI();
//
////        countryname.setText(country_name);
////        categoryname.setText(category_name);
//////        channel_de = findViewById(R.id.description_text);
////        channel_de.setText(channel_desc);
//        VideoInfo videoInfo = new VideoInfo().setShowTopBar(true);
////        private void loadRemoteMedia(boolean autoPlay){
////            if (mCastSession == null) {
////                return;
////            }
////            final RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
////            if (remoteMediaClient == null) {
////                return;
////            }
////            remoteMediaClient.registerCallback(new RemoteMediaClient.Callback() {
////                @Override
////                public void onStatusUpdated() {
////                    remoteMediaClient.unregisterCallback(this);
////                }
////            });
////            remoteMediaClient.load(new MediaLoadRequestData.Builder()
////                    .setMediaInfo(mSelectedMedia)
////                    .setAutoplay(autoPlay)
////                    .build());
////        }
//        RetrofitClient.connectTo("https://app.newslive.com/newslive/api/");
//        Call<List<RelatedChannelsModel>> call = RetrofitClient.getInstance().getApi().getRelatedLiveChannels();
//        call.enqueue(new Callback<List<RelatedChannelsModel>>() {
//            @Override
//            public void onResponse(Call<List<RelatedChannelsModel>> call, Response<List<RelatedChannelsModel>> response) {
//                if (response.isSuccessful()){
//                    relatedChannelsModelArrayList = (ArrayList<RelatedChannelsModel>) response.body();
//                    relatedChannelsAdapter = new RelatedChannelsAdapter(relatedChannelsModelArrayList , Video_Player_T.this ,Video_Player_T.this);
//                    relatedChannelsRv.setAdapter(relatedChannelsAdapter);
//                }else {
//                    Toast.makeText(Video_Player_T.this, "Response Not Successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RelatedChannelsModel>> call, Throwable t) {
//                Toast.makeText(Video_Player_T.this, "Failed to load related channels", Toast.LENGTH_SHORT).show();
//                Log.d("Related", "onFailure: " + t.getMessage());
//            }
//        });
//
//
//
//
//
//
//    }
//
//    private class HideControllersTask extends TimerTask {
//
//        @Override
//        public void run() {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    updateControllersVisibility(false);
//                    mControllersVisible = false;
//                }
//            });
//
//        }
//    }
//
//    @Override
//    public void onBackPressed(){
//        finish();
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    public void finish() {
////        customType(videoPlayer.this, "right-to-left");
//        super.finish();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("VPOPause", "onPause() was called");
//        if (mLocation == Video_Player_T.PlaybackLocation.LOCAL) {
//            videoView.getPlayer().pause();
//            mPlaybackState = PlaybackState.PLAYING;
//        }
//
//    }
//
//    private void updatePlaybackLocation(PlaybackLocation location) {
//        mLocation = location;
//        if (location == PlaybackLocation.LOCAL) {
//            if (mPlaybackState == PlaybackState.PLAYING
//                    || mPlaybackState == PlaybackState.BUFFERING) {
//                startControllersTimer();
//            } else {
//                stopControllersTimer();
//            }
//        } else {
//            stopControllersTimer();
//            updateControllersVisibility(false);
//        }
//    }
//    private void startControllersTimer() {
//        if (mControllersTimer != null) {
//            mControllersTimer.cancel();
//        }
//        if (mLocation == PlaybackLocation.REMOTE) {
//            return;
//        }
//        mControllersTimer = new Timer();
//        mControllersTimer.schedule(new HideControllersTask(), 5000);
//    }
//
//
//    //    private class HideControllersTask extends TimerTask {
////
////        @Override
////        public void run() {
////            mHandler.post(new Runnable() {
////                @Override
////                public void run() {
////                    updateControllersVisibility(false);
////                    mControllersVisible = false;
////                }
////            });
////
////        }
////    }
//    @Override
//    protected void onDestroy() {
//        Log.d("VPODest", "onDestroy() is called");
//        stopControllersTimer();
//        super.onDestroy();
//    }
//    private void stopControllersTimer() {
//        if (mControllersTimer != null) {
//            mControllersTimer.cancel();
//        }
//    }
//
////    private void updatePlaybackLocation(PlaybackLocation location) {
////        mLocation = location;
////        if (location == PlaybackLocation.LOCAL) {
////            if (mPlaybackState == PlaybackState.PLAYING
////                    || mPlaybackState == PlaybackState.BUFFERING) {
////                startControllersTimer();
////            } else {
////                stopControllersTimer();
////            }
////        } else {
////            stopControllersTimer();
////            updateControllersVisibility(false);
////        }
////    }
//
//   /* private void stopControllersTimer() {
//        if (mControllersTimer != null) {
//            mControllersTimer.cancel();
//        }
//    }*/
//
//   /* private void startControllersTimer() {
//        if (mControllersTimer != null) {
//            mControllersTimer.cancel();
//        }
//        if (mLocation == PlaybackLocation.REMOTE) {
//            return;
//        }
//        mControllersTimer = new Timer();
//        mControllersTimer.schedule(new HideControllersTask(), 5000);
//    }*/
//
//    // should be called from the main thread
//    private void updateControllersVisibility(boolean show) {
//        if (show) {
//            getSupportActionBar().show();
//            mControllers.setVisibility(View.VISIBLE);
//        } else {
//            if (getChangingConfigurations() == Configuration.ORIENTATION_LANDSCAPE) {
//                getSupportActionBar().hide();
//            }
//            mControllers.setVisibility(View.INVISIBLE);
//        }
//    }
//
//}