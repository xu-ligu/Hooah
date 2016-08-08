package com.xlg.vitamiodemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.xlg.vitamiodemo.R;


public class SplashActivity extends Activity {
    private ImageView iv_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_layout);
        iv_start=(ImageView)findViewById(R.id.iv_start);
        iv_start.setImageResource(R.drawable.start);
        initImage();


    }

    private void initImage(){
            final ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(3000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                 startAvtivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

            iv_start.startAnimation(scaleAnimation);
        }


    public void startAvtivity(){
        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
       startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }


}
