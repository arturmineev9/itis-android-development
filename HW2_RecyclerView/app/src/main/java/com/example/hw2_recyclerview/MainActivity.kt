package com.example.hw2_recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw2_recyclerview.databinding.ActivityMainBinding
import com.example.hw2_recyclerview.fragments.FirstScreenFragment

class MainActivity : AppCompatActivity() {

    private var viewBinding : ActivityMainBinding? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(mainContainerId, FirstScreenFragment())
                .commit()
        }
    }
}