    package com.example.livenewstime.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.LiveNewsPlayer;
import com.example.livenewstime.R;
import com.example.livenewstime.models.LiveChannelsModel;
import com.squareup.picasso.Picasso;

public class LiveChannelsAdapter extends RecyclerView.Adapter<LiveChannelsAdapter.ItemViewHolder> {

    View view;
    Context context;
    LiveChannelsModel liveChannelsModel;
    String thumbnailLiveNewsChannelCompleteUrl,thumbnailLiveNewsChannelBaseUrl;
    String check;
    int size;


    public LiveChannelsAdapter(Context context,LiveChannelsModel liveChannelsModel,String check) {
        this.context = context;
        this.liveChannelsModel = liveChannelsModel;
        this.check = check;
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
                replaceFragment(position);
            }
        });
    }

    private void replaceFragment(int position) {

        if(check == "livePlayer")
        {

        }
        else
        {
            Intent intent = new Intent(context, LiveNewsPlayer.class);
            intent.putExtra("position", position);
            context.startActivity(intent);
        }

    }

    @Override
    public int getItemCount() {
        try {
            size =  liveChannelsModel.getData().size();
        }
        catch (Exception e)
        {

        }
        return size;
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
