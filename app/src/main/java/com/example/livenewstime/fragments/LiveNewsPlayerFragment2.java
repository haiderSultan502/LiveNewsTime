package com.example.livenewstime.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.livenewstime.R;

import tcking.github.com.giraffeplayer2.DefaultPlayerListener;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

public class LiveNewsPlayerFragment2 extends Fragment {

    View view;
//    String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    String url = "https://content.uplynk.com/channel/3324f2467c414329b3b0cc5cd987b6be.m3u8";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_live_news_player2,container,false);

        VideoView videoView = view.findViewById(R.id.video_views);
        videoView.setVideoPath(url).getPlayer().start();

        videoView.getVideoInfo().setAspectRatio(3);


        return view;
    }
}
