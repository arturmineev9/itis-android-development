package ru.itis.clientserverapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "Task error", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }

            val token = task.result
            println("TEST TAG - Token: $token")
            Toast.makeText(this, "Token recieved", Toast.LENGTH_SHORT).show()
        }
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Уведомления разрешены!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Вы запретили уведомления!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        val permission = Manifest.permission.POST_NOTIFICATIONS
        val hasPermission = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            requestPermissionLauncher.launch(permission)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::nav.isInitialized) {
            nav.clearNavProvider(navProvider = this)
        }
        viewBinding = null
    }
}
