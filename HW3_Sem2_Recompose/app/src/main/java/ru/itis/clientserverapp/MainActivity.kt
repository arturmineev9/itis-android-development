package ru.itis.clientserverapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.clientserverapp.app.R
import ru.itis.clientserverapp.app.databinding.ActivityMainBinding
import ru.itis.clientserverapp.navigation.Nav
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Nav.Provider {

    @Inject
    lateinit var nav: Nav

    private var viewBinding: ActivityMainBinding? = null

    private val mainContainerId: Int = R.id.main_fragment_container

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)
        setupNavigation()
        setupBottomNavigation()
    }

    private fun setupNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(mainContainerId) as NavHostFragment
        navController = navHost.navController
        nav.setNavProvider(navProvider = this)
    }

    private fun setupBottomNavigation() {
        viewBinding?.bottomNavigation?.setupWithNavController(navController)
    }

    override fun getNavController(): NavController? {
        return navController
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::nav.isInitialized) {
            nav.clearNavProvider(navProvider = this)
        }
        viewBinding = null
    }
}
