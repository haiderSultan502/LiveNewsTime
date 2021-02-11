package app.webscare.livenewstime.adpater;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import app.webscare.livenewstime.R;
import app.webscare.livenewstime.fragments.WebsiteView;
import app.webscare.livenewstime.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllNewsCategoriesAdapter extends RecyclerView.Adapter<AllNewsCategoriesAdapter.ItemViewHolder> {

    View view;

    List<String> thumbnailUrl;
    String title,readMore,checkView;
    Context context;
    ArrayList<NewsModel> arrayListNews;

    WebsiteView websiteView = new WebsiteView();


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
        else if (checkView.equals("searchNews"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.read_more_item, parent, false);
        }
        return new AllNewsCategoriesAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNewsCategoriesAdapter.ItemViewHolder holder, int position) {

        thumbnailUrl = arrayListNews.get(position).getFeaturedMedia();
        Picasso.with(context).load(thumbnailUrl.get(0)).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.thumbnialHomeNews);


        title = arrayListNews.get(position).getTitle();
        holder.textViewTitle.setText(title);

        holder.itemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("newsUrl",arrayListNews.get(position).getGuid());
                websiteView.setArguments(bundle);

                replaceFragment();
            }
        });
    }

    private void replaceFragment() {

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_parent, websiteView).addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemCount() {
        return arrayListNews.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnialHomeNews;
        TextView textViewTitle,textViewreadMore;
        LinearLayout itemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnialHomeNews = itemView.findViewById(R.id.thumbnail_news);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewreadMore=itemView.findViewById(R.id.tv_readmore);
            itemClick=itemView.findViewById(R.id.item_click);

        }

    }

}
