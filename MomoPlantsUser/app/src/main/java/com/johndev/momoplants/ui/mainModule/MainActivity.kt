package com.johndev.momoplants.ui.mainModule

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.DownloadController
import com.johndev.momoplants.common.utils.buildAlertDialog
import com.johndev.momoplants.common.utils.checkSelfPermissionCompat
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.common.utils.readUrlFile
import com.johndev.momoplants.common.utils.requestPermissionsCompat
import com.johndev.momoplants.common.utils.shouldShowRequestPermissionRationaleCompat
import com.johndev.momoplants.databinding.ActivityMainBinding
import com.johndev.momoplants.ui.cartModule.view.CartFragment
import com.johndev.momoplants.ui.homeModule.view.HomeFragment
import com.johndev.momoplants.ui.ordersModule.view.OrdersFragment
import com.johndev.momoplants.ui.profileModule.view.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var downloadController: DownloadController
    private val READ_EXTERNAL_STORAGE_PERMISSION = 2004
    private val urlCode =
        "https://github.com/Johnmorales26/Momo_Plants/raw/main/versionCode.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkUpdate()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = this.findNavController(R.id.nav_host_fragment)
        //val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    /*private fun initFragment() {
        val homeFragment = HomeFragment()
        openFragment(
            fragment = homeFragment,
            fragmentManager = supportFragmentManager,
            containerId = R.id.container
        )
    }*/

    private val permissionToReadWrite: Boolean
        get() {
            val permissionGrantedResult: Int = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            return permissionGrantedResult == PackageManager.PERMISSION_GRANTED
        }

    private fun permissionForReadWrite() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), READ_EXTERNAL_STORAGE_PERMISSION
        )
    }

    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (permissionToReadWrite) {
                downloadController.enqueueDownload()
            } else {
                downloadController = DownloadController(this)
                permissionForReadWrite()
            }
        } else {
            downloadController = DownloadController(this)
            downloadController.enqueueDownload()
        }
    }

    private fun checkUpdate(){
        Thread {
            val remoteVersionCode = readUrlFile(urlCode)
            if (remoteVersionCode !== null) {
                val localVersionCode: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
                } else {
                    packageManager.getPackageInfo(packageName, 0).versionCode
                }
                Log.e("TAG", "onCreate: $remoteVersionCode, $localVersionCode")
                runOnUiThread {
                    if (remoteVersionCode.toInt() > localVersionCode) {
                        val alertDialog: AlertDialog = buildAlertDialog(
                            this,
                            R.string.new_version,
                            R.string.new_version_msg
                        )
                        alertDialog.setButton(
                            DialogInterface.BUTTON_POSITIVE, getString(R.string.btn_update)
                        ) { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                            checkStoragePermission()
                        }
                        alertDialog.setButton(
                            DialogInterface.BUTTON_NEGATIVE, getString(R.string.btn_cancel)
                        ) { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }
                }
            } else {
                Log.e("TAG", "onCreate: error checking version")
            }
        }.start()
    }

}