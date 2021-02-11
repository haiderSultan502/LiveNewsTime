package app.webscare.livenewstime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import app.webscare.livenewstime.R;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo=findViewById(R.id.image_logo);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        logo.startAnimation(animFadeIn);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome_Screen=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(welcome_Screen);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}