package com.example.livenewstime.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.R;
import java.util.Timer;
import java.util.TimerTask;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

public class LiveNewsPlayerFragment extends Fragment {

    View view;
    VideoView videoView;
    String channelTitle;
    private ImageButton topbackbtn;
    private LiveNewsPlayerFragment.PlaybackState mPlaybackState;
    private LiveNewsPlayerFragment.PlaybackLocation mLocation;
    private Timer mControllersTimer;
    private View mControllers;
    private Context mContext;
    private final Handler mHandler = new Handler();
    final String Urlnew = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private boolean mControllersVisible;
    private LinearLayout backButtonLayoutVideoPlayer;
    RecyclerView relatedChannelsRv;
    TextView title , description , tagsTextView;

    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    /**
     * List of various states that we can be in
     */
    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_live_news_player,container,false);

        videoView = view.findViewById(R.id.video_view);
//        GiraffePlayer.play(getContext(), new VideoInfo(Urlnew));  //only want to play a video fullscreen
        videoView.setVideoPath(Urlnew).getPlayer().start();
        backButtonLayoutVideoPlayer = view.findViewById(R.id.backButtonLayoutVideoPlayerID);
        mControllers = view.findViewById(R.id.controllers_video);


        boolean shouldStartPlayback = true;
        int startPosition = 0;
        if (shouldStartPlayback) {
            // this will be the case only if we are coming from the
            // CastControllerActivity by disconnecting from a device
            mPlaybackState = LiveNewsPlayerFragment.PlaybackState.PLAYING;
            updatePlaybackLocation(LiveNewsPlayerFragment.PlaybackLocation.LOCAL);
            if (startPosition > 0) {
                videoView.getPlayer().seekTo(startPosition);
            }
            videoView.getPlayer().start();
            startControllersTimer();
        } else {
            // we should load the video but pause it
            // and show the album art.
            mPlaybackState = LiveNewsPlayerFragment.PlaybackState.IDLE;
        }

        mContext = getActivity();

        topbackbtn = view.findViewById(R.id.vtpb);

        VideoInfo videoInfo = new VideoInfo().setShowTopBar(true);

        return view;
    }
    private class HideControllersTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateControllersVisibility(false);
                    mControllersVisible = false;
                }
            });

        }
    }

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
//            mPlaybackState = Video_Player_T.PlaybackState.PLAYING;
//        }
//
//    }

    private void updatePlaybackLocation(LiveNewsPlayerFragment.PlaybackLocation location) {
        mLocation = location;
        if (location == LiveNewsPlayerFragment.PlaybackLocation.LOCAL) {
            if (mPlaybackState == LiveNewsPlayerFragment.PlaybackState.PLAYING
                    || mPlaybackState == LiveNewsPlayerFragment.PlaybackState.BUFFERING) {
                startControllersTimer();
            } else {
                stopControllersTimer();
            }
        } else {
            stopControllersTimer();
            updateControllersVisibility(false);
        }
    }
    private void startControllersTimer() {
        if (mControllersTimer != null) {
            mControllersTimer.cancel();
        }
        if (mLocation == LiveNewsPlayerFragment.PlaybackLocation.REMOTE) {
            return;
        }
        mControllersTimer = new Timer();
        mControllersTimer.schedule(new HideControllersTask(), 5000);
    }

//    @Override
//    protected void onDestroy() {
//        Log.d("VPODest", "onDestroy() is called");
//        stopControllersTimer();
//        super.onDestroy();
//    }
    private void stopControllersTimer() {
        if (mControllersTimer != null) {
            mControllersTimer.cancel();
        }
    }

    // should be called from the main thread
    private void updateControllersVisibility(boolean show) {
        if (show) {
//            getSupportActionBar().show();
            mControllers.setVisibility(View.VISIBLE);
        } else {
            /*if (!Utils.isOrientationPortrait(this)) {
                getSupportActionBar().hide();
            }*/
            mControllers.setVisibility(View.INVISIBLE);
        }
    }
}
