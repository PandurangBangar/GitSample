package com.pan.gitsample.views;

import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import com.pan.gitsample.R;

/*
 * Created by Pandurang.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        startMainActivity();
    }
}
