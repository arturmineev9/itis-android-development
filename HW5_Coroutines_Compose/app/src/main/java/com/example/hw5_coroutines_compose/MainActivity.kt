package com.example.hw5_coroutines_compose

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw5_coroutines_compose.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private var mainContainer = R.id.main_container_id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        supportFragmentManager.beginTransaction()
            .add(mainContainer, MainFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}
