package com.example.livenewstime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.livenewstime.fragments.HomeFragment;
import com.example.livenewstime.fragments.NewsFragment;
import com.example.livenewstime.fragments.PoliticsFragment;
import com.example.livenewstime.fragments.SearchFragment;
import com.example.livenewstime.fragments.TechnologyFragment;
import com.example.livenewstime.models.NewsModel;
import com.example.livenewstime.otherClasses.SweetAlertDialogGeneral;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    Button btnDrawerLayout;
    ImageView imgSearch;
    RelativeLayout rlSearchLayout;
    FrameLayout frameLayout;
    Boolean checkSearchStatus = false;
    EditText edtSearch;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    int index;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

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
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btnDrawerLayout = findViewById(R.id.btn_drawer);
        setNavigationDrawer();

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(MainActivity.this);
        HomeFragment homeFragment = new HomeFragment(this);
        replaceFrag(homeFragment);
        setDataInViews();

        imgSearch.setOnClickListener(this);
        btnDrawerLayout.setOnClickListener(this);
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
                        HomeFragment homeFragment = new HomeFragment(MainActivity.this);
                        replaceFrag(homeFragment);
                        break;
                    case 1:
                        PoliticsFragment politicsFragment = new PoliticsFragment(MainActivity.this);
                        replaceFrag(politicsFragment);
                        break;
                    case 2:
                        NewsFragment newsFragment = new NewsFragment(MainActivity.this);
                        replaceFrag(newsFragment);
                        break;
                    case 3:
                        TechnologyFragment technologyFragment = new TechnologyFragment(MainActivity.this);
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
                    edtSearch.setText("");
                }
                else
                {
                    rlSearchLayout.setVisibility(View.VISIBLE);
                    rlSearchLayout.scheduleLayoutAnimation();
                    checkSearchStatus=true;

                    edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if(actionId== EditorInfo.IME_ACTION_SEARCH)
                            {
                                String networkName =  edtSearch.getText().toString();
                                if (networkName.length() < 1)
                                {
                                    sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please type some keyword");
                                }
                                else {
                                    SearchFragment searchFragment = new SearchFragment(MainActivity.this,networkName);
                                    replaceFrag(searchFragment);
                                }
                            }
                            return false;
                        }
                    });

                }

            case R.id.btn_drawer:

                    drawerLayout.openDrawer(Gravity.LEFT);

        }
    }

    private void setNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                // check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.health) {
                    Toast.makeText(MainActivity.this, "health", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.sports) {
                    Toast.makeText(MainActivity.this, "sports", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.business) {
                    Toast.makeText(MainActivity.this, "business", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
}