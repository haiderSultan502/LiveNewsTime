package com.example.livenewstime.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.R;

public class HomeLatestNewsAdapter extends RecyclerView.Adapter<HomeLatestNewsAdapter.ItemViewHolder> {

    View view;
    Context context;

    public HomeLatestNewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeLatestNewsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.latest_news_item, parent, false);
        return new HomeLatestNewsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLatestNewsAdapter.ItemViewHolder holder, int position) {

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
