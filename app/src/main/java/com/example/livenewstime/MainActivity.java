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

import com.example.livenewstime.fragments.Home;
import com.example.livenewstime.fragments.News;
import com.example.livenewstime.fragments.Politics;
import com.example.livenewstime.fragments.Technology;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    int index;
    ImageView imgSearch;
    RelativeLayout rlSearchLayout;
    FrameLayout frameLayout;
    Boolean checkSearchStatus = false;
    EditText edtSearch;
    MainActivity mainActivity;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

    }

    private void initializeView() {
        tabLayout=findViewById(R.id.tabLayout);
        imgSearch=findViewById(R.id.img_search);
        rlSearchLayout=findViewById(R.id.rl_seach_layout);
        edtSearch=findViewById(R.id.edit_text_search);
        frameLayout=findViewById(R.id.frame_layout);

        mainActivity=this;
        Home home = new Home(mainActivity);
        replaceFrag(home);
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
                        Home home = new Home(mainActivity);
                        replaceFrag(home);
                        break;
                    case 1:
                        Politics politics = new Politics(mainActivity);
                        replaceFrag(politics);
                        break;
                    case 2:
                        News news = new News(mainActivity);
                        replaceFrag(news);
                        break;
                    case 3:
                        Technology technology = new Technology(mainActivity);
                        replaceFrag(technology);
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