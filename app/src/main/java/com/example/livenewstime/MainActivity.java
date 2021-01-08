package com.example.livenewstime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.livenewstime.fragments.HomeFragment;
import com.example.livenewstime.fragments.NewsFragment;
import com.example.livenewstime.fragments.PoliticsFragment;
import com.example.livenewstime.fragments.TechnologyFragment;
import com.example.livenewstime.models.NewsModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    int index;
    ImageView imgSearch;
    RelativeLayout rlSearchLayout;
    FrameLayout frameLayout;
    Boolean checkSearchStatus = false;
    EditText edtSearch;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static ArrayList<NewsModel> news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

    }

    public static ArrayList<NewsModel> getNews() {
        return news;
    }

    public static void setNews(ArrayList<NewsModel> news) {
        MainActivity.news = news;
    }

    private void initializeView() {
        news = new ArrayList<>();
        tabLayout=findViewById(R.id.tabLayout);
        imgSearch=findViewById(R.id.img_search);
        rlSearchLayout=findViewById(R.id.rl_seach_layout);
        edtSearch=findViewById(R.id.edit_text_search);
        frameLayout=findViewById(R.id.frame_layout);

        HomeFragment homeFragment = new HomeFragment(getBaseContext());
        replaceFrag(homeFragment);
        setDataInViews();

        imgSearch.setOnClickListener(this);
    }

    private void replaceFrag(Fragment frag) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void setDataInViews() {

        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                index = tab.getPosition();
                switch (index)
                {
                    case 0:
                        HomeFragment homeFragment = new HomeFragment(getApplicationContext());
                        replaceFrag(homeFragment);
                        break;
                    case 1:
                        PoliticsFragment politicsFragment = new PoliticsFragment(getApplicationContext());
                        replaceFrag(politicsFragment);
                        break;
                    case 2:
                        NewsFragment newsFragment = new NewsFragment(getApplicationContext());
                        replaceFrag(newsFragment);
                        break;
                    case 3:
                        TechnologyFragment technologyFragment = new TechnologyFragment(getApplicationContext());
                        replaceFrag(technologyFragment);
                        break;
                    case 4:
                        tabLayout.getTabAt(index).setIcon(R.drawable.tv_live_white);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                index = tab.getPosition();
                if(index==4)
                {
                    tabLayout.getTabAt(index).setIcon(R.drawable.tv_live_gray);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_search:

                if (checkSearchStatus)
                {
                    rlSearchLayout.setVisibility(View.GONE);
                    checkSearchStatus = false;
                }
                else
                {
                    rlSearchLayout.setVisibility(View.VISIBLE);
                    rlSearchLayout.scheduleLayoutAnimation();
                    checkSearchStatus=true;
                }




        }
    }
}