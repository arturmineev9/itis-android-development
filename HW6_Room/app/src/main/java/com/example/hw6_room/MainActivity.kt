package com.example.hw6_room

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hw6_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            viewBinding.bottomNavigation.setupWithNavController(navController)
        }
    }

    fun setBottomNavigationVisibility(isVisible: Boolean) {
        if (isVisible) {
            viewBinding.bottomNavigation.visibility = View.VISIBLE
        } else {
            viewBinding.bottomNavigation.visibility = View.GONE
        }
    }


}