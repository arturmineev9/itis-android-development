package com.example.hw1_fragments

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw1_fragments.databinding.ActivityMainBinding
import com.example.hw1_fragments.fragments.FirstFragment

class MainActivity : AppCompatActivity() {

    private var viewBinding : ActivityMainBinding? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction()
            .add(mainContainerId, FirstFragment())
            .commit()


    }

}