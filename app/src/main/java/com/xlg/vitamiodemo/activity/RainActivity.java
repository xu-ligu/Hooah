package com.xlg.vitamiodemo.activity;

import android.app.Activity;
import android.os.Bundle;
import com.xlg.vitamiodemo.R;
import java.util.Random;

/**
 * Created by xlg on 2016/8/6.
 *
 */
public class RainActivity extends Activity {
    //两两弹幕之间的间隔时间
    public static final int DELAY_TIME = 800;

    private Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        System.exit(0);
    }
}
