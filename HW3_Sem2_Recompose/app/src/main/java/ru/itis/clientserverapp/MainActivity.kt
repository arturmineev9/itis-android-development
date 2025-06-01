package ru.itis.clientserverapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
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
        checkAndRequestNotificationPermission()
    }

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val shouldOpenGraph = prefs.getBoolean(SHOULD_OPEN_GRAPH_FRAGMENT, false)

        if (shouldOpenGraph) {
            prefs.edit { putBoolean(SHOULD_OPEN_GRAPH_FRAGMENT, false) }

            if (checkAuthorization()) {
                val currentDestination = navController.currentDestination?.id
                if (currentDestination != R.id.graphPageScreen) {
                    navController.navigate(R.id.graphPageScreen)
                } else {
                    showToast(getString(R.string.toast_already_on_graph))
                }
            } else {
                showToast(getString(R.string.toast_graph_access_denied))
            }
        }
    }

    private fun setupNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(mainContainerId) as NavHostFragment
        navController = navHost.navController
        nav.setNavProvider(this)
    }

    private fun setupBottomNavigation() {
        viewBinding?.bottomNavigation?.setOnItemSelectedListener { item ->
            val remoteConfig = Firebase.remoteConfig

            if (item.itemId == R.id.graphPageScreen) {
                val isFeatureEnabled = remoteConfig.getBoolean(GRAPH_SCREEN_ACCESS_FLAG)
                if (!isFeatureEnabled) {
                    showToast(getString(R.string.toast_feature_unavailable))
                    return@setOnItemSelectedListener false
                }
            }

            val handled = item.itemId != navController.currentDestination?.id
            if (handled) {
                navController.navigate(item.itemId)
            }
            true
        }
    }

    override fun getNavController(): NavController? = navController

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val message = if (isGranted) {
            getString(R.string.toast_notifications_allowed)
        } else {
            getString(R.string.toast_notifications_denied)
        }
        showToast(message)
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val permission = Manifest.permission.POST_NOTIFICATIONS
        val hasPermission = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun checkAuthorization(): Boolean {
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::nav.isInitialized) {
            nav.clearNavProvider(this)
        }
        viewBinding = null
    }

    companion object {
        const val PREFS_NAME = "fcm_prefs"
        const val SHOULD_OPEN_GRAPH_FRAGMENT = "shouldOpenGraphFragment"
        const val GRAPH_SCREEN_ACCESS_FLAG = "graph_screen_access_flag"
    }
}
