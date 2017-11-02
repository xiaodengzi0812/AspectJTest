package com.dengzi.aspectjtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dengzi.aspectjtest.checknet.CheckNet;
import com.dengzi.aspectjtest.singleclick.SingleClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @CheckNet
    public void checkNetClick(View view) {
        startActivity(new Intent(this, UserActivity.class));
    }

    @SingleClick
    @CheckNet
    public void singleClick(View view) {
        Log.e("dengzi", "singleClick");
    }

}
