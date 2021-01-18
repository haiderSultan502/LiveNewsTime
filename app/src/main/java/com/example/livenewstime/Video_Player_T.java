package com.example.livenewstime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tcking.github.com.giraffeplayer2.DefaultMediaController;
import tcking.github.com.giraffeplayer2.DefaultPlayerListener;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.PlayerManager;
import tcking.github.com.giraffeplayer2.VideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class Video_Player_T extends AppCompatActivity {

    String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
//        String url = "https://content.uplynk.com/channel/3324f2467c414329b3b0cc5cd987b6be.m3u8";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__player__t);



        button = findViewById(R.id.btn_full);

        PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "multiple_requests", 1L));

        VideoView videoView = (VideoView) findViewById(R.id.video_view);

        videoView.setVideoPath(url).getPlayer().start();

        videoView.getPlayer().aspectRatio(3);



//        videoView.getPlayer().setDisplayModel(GiraffePlayer.DISPLAY_FULL_WINDOW);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setPlayerListener(new DefaultPlayerListener()
                {
                    @Override
                    public void onDisplayModelChange(int oldModel, int newModel) {
                        if (oldModel == GiraffePlayer.STATE_PLAYING)
                        {
                            videoView.getPlayer().pause();
                        }
                    }
                });
            }
        });


//        videoView.setPlayerListener(new DefaultPlayerListener()
//        {
//            @Override
//            public void onDisplayModelChange(int oldModel, int newModel) {
//                if(oldModel == GiraffePlayer.STATE_IDLE)
//                {
//
//                    Toast.makeText(Video_Player_T.this, "grt state", Toast.LENGTH_SHORT).show();
//
//                    if (videoView.getPlayer().isPlaying())
//                    {
//                        videoView.getPlayer().start();
//                    }
////                    videoView.getVideoInfo().setPortraitWhenFullScreen(true);
////
////                    videoView.getPlayer().toggleFullScreen();
//                }
//            }
//        });

    }




}