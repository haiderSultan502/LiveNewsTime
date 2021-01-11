package com.example.livenewstime.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewstime.R;
import com.example.livenewstime.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllNewsCategoriesAdapter extends RecyclerView.Adapter<AllNewsCategoriesAdapter.ItemViewHolder> {

    View view;
    List<String> thumbnailUrl;
    String title,readMore,checkView;
    Context context;
    ArrayList<NewsModel> arrayListNews;

    public AllNewsCategoriesAdapter(Context context, ArrayList<NewsModel> arrayListNews, String view) {
        this.arrayListNews = new ArrayList<>();
        this.checkView=view;
        this.context = context;
        this.arrayListNews = arrayListNews;
    }

    @NonNull
    @Override
    public AllNewsCategoriesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (checkView.equals("latestNews"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.latest_news_item, parent, false);
        }
        else if (checkView.equals("readMoreNews"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.read_more_item_for_all_categories, parent, false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.read_more_item, parent, false);
        }
        return new AllNewsCategoriesAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNewsCategoriesAdapter.ItemViewHolder holder, int position) {

        thumbnailUrl = arrayListNews.get(position).getThumbnailUrl();
        Picasso.with(context).load(thumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.thumbnialHomeNews);


        title = arrayListNews.get(position).getTitle();
        holder.textViewTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return arrayListNews.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnialHomeNews;
        TextView textViewTitle,textViewreadMore;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnialHomeNews = itemView.findViewById(R.id.thumbnail_news);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewreadMore=itemView.findViewById(R.id.tv_readmore);

        }

    }

}
