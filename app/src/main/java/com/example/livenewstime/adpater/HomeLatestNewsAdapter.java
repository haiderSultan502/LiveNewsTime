package com.example.livenewstime.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.MainActivity;
import com.example.livenewstime.R;

public class HomeLatestNewsAdapter extends RecyclerView.Adapter<HomeLatestNewsAdapter.ItemViewHolder> {

    View view;
    String thimbnailurl,title,readMore;
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

//        thimbnailurl = mainActivity.news.get(position).getThumbnailUrl().get(position);
//        Picasso.with(mainActivity).load(thimbnailurl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.thumbnialHomeNews);

//        title = mainActivity.news.get(position).getTitle();
//        holder.textViewTitle.setText(title);



    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnialHomeNews;
        TextView textViewTitle,textViewreadMore;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnialHomeNews = itemView.findViewById(R.id.thumbnail_home_news);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewreadMore=itemView.findViewById(R.id.tv_readmore);

        }

    }

}
