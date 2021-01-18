package com.example.livenewstime.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.R;
import com.example.livenewstime.fragments.LiveNewsPlayerFragment2;
import com.example.livenewstime.models.LiveChannelsModel;
import com.squareup.picasso.Picasso;

public class LiveChannelsAdapter extends RecyclerView.Adapter<LiveChannelsAdapter.ItemViewHolder> {

    View view;
    Context context;
    LiveChannelsModel liveChannelsModel;
    String thumbnailLiveNewsChannelCompleteUrl,thumbnailLiveNewsChannelBaseUrl;

    LiveNewsPlayerFragment2 liveNewsPlayerFragment = new LiveNewsPlayerFragment2();


    public LiveChannelsAdapter(Context context,LiveChannelsModel liveChannelsModel) {
        this.context = context;
        this.liveChannelsModel = liveChannelsModel;
    }

    @NonNull
    @Override
    public LiveChannelsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = LayoutInflater.from(context).inflate(R.layout.live_channels_item, parent, false);

        return new LiveChannelsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveChannelsAdapter.ItemViewHolder holder, int position) {

        thumbnailLiveNewsChannelBaseUrl = "https://app.newslive.com/newslive/storage/app/public/NewsTimeimages/";
        thumbnailLiveNewsChannelCompleteUrl = thumbnailLiveNewsChannelBaseUrl +  liveChannelsModel.getData().get(position).getCardImageUrl();
        Picasso.with(context).load(thumbnailLiveNewsChannelCompleteUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.thumbnialLiveChannel);

        holder.itemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
    }

    private void replaceFragment() {

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, liveNewsPlayerFragment).addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemCount() {
        return liveChannelsModel.getData().size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnialLiveChannel;
        LinearLayout itemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnialLiveChannel = itemView.findViewById(R.id.thumbnail_live_channels);
            itemClick=itemView.findViewById(R.id.item_click);

        }

    }

}
