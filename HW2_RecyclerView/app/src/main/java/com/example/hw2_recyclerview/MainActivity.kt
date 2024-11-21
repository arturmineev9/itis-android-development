package com.example.hw2_recyclerview

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw2_recyclerview.databinding.ActivityMainBinding
import com.example.hw2_recyclerview.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private var viewBinding : ActivityMainBinding? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(mainContainerId, MainFragment())
                .commit()
        }
    }
}