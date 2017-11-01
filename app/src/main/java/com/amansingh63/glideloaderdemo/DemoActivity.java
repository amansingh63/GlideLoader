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
        glideLoader.loadImage("https://www.concretepage.com/android/images/android-imageview-example-by-xml-and-programmatically-3.jpg");

    }
}
