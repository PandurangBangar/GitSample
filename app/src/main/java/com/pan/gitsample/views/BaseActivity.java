package com.pan.gitsample.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.pan.gitsample.models.User;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Pandurang.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        getAllPermission();
    }


    public void startHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void startContactDetailActivity(User user) {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("object", user);
        startActivity(intent);
    }

    public void getAllPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasACCESS_NETWORK_STATE = checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            int hasINTERNET = checkSelfPermission(Manifest.permission.INTERNET);
            int hasACCESS_WIFI_STATE = checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE);

            List<String> permissions = new ArrayList<String>();
            if (hasACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }

            if (hasINTERNET != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.INTERNET);
            }


            if (hasACCESS_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_WIFI_STATE);
            }


            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 101);
            }
        }
    }
}
