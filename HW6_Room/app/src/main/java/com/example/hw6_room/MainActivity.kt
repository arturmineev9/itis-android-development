package com.example.hw6_room

import android.content.Context
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
import com.example.hw6_room.screens.MainScreenFragment
import com.example.hw6_room.screens.entry.WelcomeFragment
import com.example.hw6_room.utils.SessionManager

class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setCustomTheme()
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        sessionManager = SessionManager(this)


        initNavController()

    }


    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (sessionManager.isLoggedIn()) {
            navController?.navigate(R.id.memesListFragment)
        } else {
            navController?.navigate(R.id.welcomeFragment)
        }

        if (navController != null) {
            viewBinding?.bottomNavigation?.setupWithNavController(navController)
        }
    }
    
    fun setBottomNavigationVisibility(isVisible: Boolean) {
        if (isVisible) {
            viewBinding?.bottomNavigation?.visibility = View.VISIBLE
        } else {
            viewBinding?.bottomNavigation?.visibility = View.GONE
        }
    }


    private fun setCustomTheme() {
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        var theme = sharedPreferences.getInt("APP_THEME", R.style.AppTheme_red)
        when (theme) {
            -1739917 -> theme = R.style.AppTheme_red
            -1023342 -> theme = R.style.AppTheme_pink
            -4560696 -> theme = R.style.AppTheme_purple
            -6982195 -> theme = R.style.AppTheme_dark_purple
            -8812853 -> theme = R.style.AppTheme_dark_blue
            -10177034 -> theme = R.style.AppTheme_blue
            -11549705 -> theme = R.style.AppTheme_cyan
            -11677471 -> theme = R.style.AppTheme_cloud
            -11684180 -> theme = R.style.AppTheme_emerald
            -8271996 -> theme = R.style.AppTheme_dark_green
            -5319295 -> theme = R.style.AppTheme_green
            -2300043 -> theme = R.style.AppTheme_grass
            -10929 -> theme = R.style.AppTheme_dark_yellow
            -18611 -> theme = R.style.AppTheme_orange
            -30107 -> theme = R.style.AppTheme_dark_orange
            -6190977 -> theme = R.style.AppTheme_brown
            -7297874 -> theme = R.style.AppTheme_dark_gray
            else -> {
                theme = R.style.AppTheme_dark_purple
            }
        }
        setTheme(theme)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

}