package com.amansingh63.glideloaderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amansingh63.glideloader.GlideLoader;

public class DemoActivity extends AppCompatActivity {
    GlideLoader glideLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        glideLoader = (GlideLoader) findViewById(R.id.glideLoader);

        glideLoader.setErrorImageResId(R.mipmap.ic_launcher_round);
        glideLoader.setPlaceHolderImageResId(R.mipmap.ic_launcher);
        glideLoader.loadImage("https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Android_Studio_icon.svg/1200px-Android_Studio_icon.svg.png");

    }
}
