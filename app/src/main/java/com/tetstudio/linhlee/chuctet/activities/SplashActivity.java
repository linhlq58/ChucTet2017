package com.tetstudio.linhlee.chuctet.activities;

import android.os.Bundle;
import android.os.Handler;

import com.tetstudio.linhlee.chuctet.R;

/**
 * Created by lequy on 1/18/2017.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        }, 2000);
    }
}
