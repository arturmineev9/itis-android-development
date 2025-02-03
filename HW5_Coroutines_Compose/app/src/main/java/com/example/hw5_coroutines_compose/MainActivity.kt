package com.example.hw5_coroutines_compose

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestPermission(android.Manifest.permission.POST_NOTIFICATIONS, ::requestNotificationPermission)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(mainContainer, MainFragment())
                .commit()
        }
    }

    private fun checkAndRequestPermission(permission: String, requestPermissionFunc: () -> Unit) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionFunc()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    showToast(getString(R.string.notifications_allowed))
                } else {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                        showToast(getString(R.string.notifications_denied))
                    } else {
                        showRationaleDialog()
                        showToast(getString(R.string.notifications_permanently_denied))
                    }
                }
            }
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            showToast(getString(R.string.notifications_not_required))
        }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.notification_permission_title)
            .setMessage(R.string.notification_permission_message)
            .setPositiveButton(R.string.open_settings_button) { _, _ ->
                openAppSettings()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts(Constants.PACKAGE, packageName, null)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}
