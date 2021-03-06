package app.webscare.livenewstime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.webscare.livenewstime.Interface.InterfaceApi;

import app.webscare.livenewstime.R;

import app.webscare.livenewstime.fragments.BusinessFragment;
import app.webscare.livenewstime.fragments.HealthFragment;
import app.webscare.livenewstime.fragments.HomeFragment;
import app.webscare.livenewstime.fragments.LiveNewsFragment;
import app.webscare.livenewstime.fragments.NewsFragment;
import app.webscare.livenewstime.fragments.PoliticsFragment;
import app.webscare.livenewstime.fragments.SearchFragment;
import app.webscare.livenewstime.fragments.SportsFragment;
import app.webscare.livenewstime.fragments.TechnologyFragment;
import app.webscare.livenewstime.models.FragmentDetailModel;
import app.webscare.livenewstime.models.LiveChannelsModel;
import app.webscare.livenewstime.models.NewsModel;
import app.webscare.livenewstime.otherClasses.RetrofitLibrary;
import app.webscare.livenewstime.otherClasses.SweetAlertDialogGeneral;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    LinearLayout btnDrawerLayout;
    ImageView imgSearch;
    RelativeLayout rlSearchLayout;
    FrameLayout frameLayout,frameLayoutWebView;
    Boolean checkSearchStatus = false;
    EditText edtSearch;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView faceBook,twitter,instagram,youtube;
    public static LinearLayout lootieAnimaationLayout;

    public static boolean getHomeNews=false,getPoloiticsNews=false,getNews=false,getTechnologyNews=false,getHealthNews=false,getSportsNews=false,getBusinessNews=false,getLiveNews,getFragmentDetails;
    public static ArrayList<NewsModel> arrayListHomeNews,arrayListPoliticsNews,arrayListNews,arrayListTechnologyNews,arrayListHealthNews,arrayListSportsNews,arrayListBusinessNews;
    public static ArrayList<FragmentDetailModel> arrayListCategoryDetails;
    public static LiveChannelsModel liveChannelsModel;
    public static ArrayList<NewsModel> arrayListLatestHomeNews;
    public static List<String> homeThumbnailUrl,politicsThumbnailUrl,newsThumbnailUrl,technologyThumbnailUrl,healthThumbnailUrl,sportsThumbnailUrl,businessThumbnailUrl;
    public static String homePostTitle,politicsPostTitle,newsPostTitle,technologyPostTitle,healthPostTitle,sportsPostTitle,businessPostTitle;
    public static String categoryNameLive,categoryNameHome,categoryNameNews,categoryNamepolitics,categoryNameTechnolohy,categoryNameSports,categoryNameBusiness,categoryNameHealth;
    public static String categoryDetailsPolitics,categoryDetailsNews,categoryDetailsTechnology,categoryDetailsHealth,categoryDetailsSports,categoryDetailsBusiness,categoryDetailsLive;

    int index;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    Call<List<FragmentDetailModel>> callForCategoryDetails;
    InterfaceApi interfaceApi;

    public MainActivity() {
    }

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
        frameLayoutWebView=findViewById(R.id.frame_layout_parent);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btnDrawerLayout = findViewById(R.id.btn_drawer);
        lootieAnimaationLayout=findViewById(R.id.lootie_animation_layout);

        faceBook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        instagram = findViewById(R.id.instagram);
        youtube = findViewById(R.id.youtube);

//        animationShow();

        setNavigationDrawer();

        initializeArrayList();

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(MainActivity.this);

        getCategoryDetail();


        HomeFragment homeFragment = new HomeFragment(this);
        replaceFrag(homeFragment);


        setDataInViews();

        imgSearch.setOnClickListener(this);
        btnDrawerLayout.setOnClickListener(this);
        faceBook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        instagram.setOnClickListener(this);
        youtube.setOnClickListener(this);
    }

    private void initializeArrayList() {

        arrayListHomeNews = new ArrayList<>();
        arrayListLatestHomeNews = new ArrayList<>();

        arrayListPoliticsNews = new ArrayList<>();

        arrayListNews = new ArrayList<>();

        arrayListTechnologyNews = new ArrayList<>();

        arrayListHealthNews = new ArrayList<>();

        arrayListSportsNews = new ArrayList<>();

        arrayListBusinessNews = new ArrayList<>();

        arrayListCategoryDetails = new ArrayList<>();

        liveChannelsModel = new LiveChannelsModel();
    }

    public static void animationShow()
    {

        lootieAnimaationLayout.setVisibility(View.VISIBLE);
    }
    public static void animationHide()
    {
        lootieAnimaationLayout.setVisibility(View.GONE);
    }
    private void replaceFrag(Fragment frag) {

//        animationShow();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    private void replaceFragWithParentFramelayout(Fragment frag) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_parent,frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null);
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
                        LiveNewsFragment liveNewsFragment = new LiveNewsFragment(MainActivity.this);
                        replaceFrag(liveNewsFragment);
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

    public void getCategoryDetail()
    {
        {

            try {
                interfaceApi = RetrofitLibrary.connect("https://livenewstime.com/wp-json/Newspaper/v2/");
                callForCategoryDetails = interfaceApi.getCategoryDetail();
                callForCategoryDetails.enqueue(new Callback<List<FragmentDetailModel>>() {
                    @Override
                    public void onResponse(Call<List<FragmentDetailModel>> call, Response<List<FragmentDetailModel>> response) {
                        if (!response.isSuccessful())
                        {
                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                            return;
                        }
                        arrayListCategoryDetails = (ArrayList<FragmentDetailModel>) response.body();

//                        animationHide();
                    }

                    @Override
                    public void onFailure(Call<List<FragmentDetailModel>> call, Throwable t) {
                        sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                    }
                });
            }
            catch (Exception e)
            {
                sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
            }

        }
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
                break;

            case R.id.btn_drawer:

                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;

            case R.id.facebook:
                viewSocialMediaPage("https://www.facebook.com/Live-News-Team-105637421562498\n");
                break;

            case R.id.twitter:
                viewSocialMediaPage("https://twitter.com/LiveNewsTime2");
                break;

            case R.id.instagram:
                viewSocialMediaPage("https://www.instagram.com/livenewstime/");
                break;

            case R.id.youtube:
                viewSocialMediaPage("https://www.youtube.com/channel/UC7Y4BY5bHh0SssVerfEmm-A/about");
                break;

        }
    }

    private void viewSocialMediaPage(String socialMediaLink) {
        Intent socialMediaPage = new Intent(Intent.ACTION_VIEW);
        socialMediaPage.setData(Uri.parse(socialMediaLink));
        startActivity(socialMediaPage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        //this functuion is called because when we back press then unInitialize the boolean variables othervise if not uninitialize then when app reopen then app perform some wrong operation because static varaibale has no set new values , there fore set new valuse of static avr before exit the app;

        setFalseStaticBooleanVariales();
        finish();

    }

    private void setFalseStaticBooleanVariales() {
        getHomeNews = false;
        getPoloiticsNews = false;
        getNews = false;
        getTechnologyNews = false;
        getSportsNews = false;
        getBusinessNews = false;
        getHealthNews = false;
        getLiveNews = false;
    }

    private void setNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                // check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.health) {
                    HealthFragment healthFragment = new HealthFragment(MainActivity.this);
                    replaceFragWithParentFramelayout(healthFragment);
                }
                else if (itemId == R.id.sports) {
                    SportsFragment sportsFragment = new SportsFragment(MainActivity.this);
                    replaceFragWithParentFramelayout(sportsFragment);
                }
                else if (itemId == R.id.business) {
                    BusinessFragment businessFragment = new BusinessFragment(MainActivity.this);
                    replaceFragWithParentFramelayout(businessFragment);
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
}