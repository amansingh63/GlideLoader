package com.amansingh63.glideimageviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import kotlin.random.Random

class DemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        val width = Random.nextInt(512)
        val height = Random.nextInt(512)

        glideLoader.loadImage("https://picsum.photos/$width/$height")
//        glideLoader.loadImage("https://picsum.photos/500/500")

    }
}
