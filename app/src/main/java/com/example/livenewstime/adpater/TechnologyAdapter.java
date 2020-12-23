package com.example.livenewstime.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.R;

public class TechnologyAdapter extends RecyclerView.Adapter<TechnologyAdapter.ItemViewHolder> {
    View view;
    Context context;

    public TechnologyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TechnologyAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.politics_item, parent, false);
        return new TechnologyAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 4;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
