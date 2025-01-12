package com.example.hw3_viewpager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw3_viewpager.fragments.ViewPagerFragment

class MainActivity : AppCompatActivity() {

    private val mainContainerId = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(mainContainerId, ViewPagerFragment())
                .commit()
        }
    }
}