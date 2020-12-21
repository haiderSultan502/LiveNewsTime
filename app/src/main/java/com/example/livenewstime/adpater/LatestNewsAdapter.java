package com.example.livenewstime.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.R;

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ItemViewHolder> {

    View view;
    Context context;

    public LatestNewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LatestNewsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.latest_news_item, parent, false);
        return new LatestNewsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestNewsAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
