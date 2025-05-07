package ru.itis.clientserverapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        if (navController == null) {
            val navHost =
                supportFragmentManager.findFragmentById(mainContainerId) as NavHostFragment
            navController = navHost.navController
        }
        nav.setNavProvider(navProvider = this)
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