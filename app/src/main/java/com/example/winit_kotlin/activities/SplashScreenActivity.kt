package com.example.winit_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.databinding.ActivitySplashScreenBinding
import com.example.winit_kotlin.utils.CalendarUtils

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    private val SPLASH_SCREEN_DELAY : Long = 2000;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeControls();

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        },SPLASH_SCREEN_DELAY)

    }

    fun initializeControls() {
        val preference = Preference(applicationContext)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        if (preference.getStringFromPreference(Preference.BUILD_INSTALLATIONDATE, "").isEmpty()) {
            preference.saveStringInPreference(Preference.BUILD_INSTALLATIONDATE, CalendarUtils.getOrderPostDate())
        }
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_WIDTH, displayMetrics.widthPixels)
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_HEIGHT, displayMetrics.heightPixels)
        preference.commitPreference()

        AppConstants.DATABASE_PATH = application.filesDir.toString() + "/"
        AppConstants.DEVICE_WIDTH = displayMetrics.widthPixels
        AppConstants.DEVICE_HEIGHT = displayMetrics.heightPixels
        AppConstants.CategoryIconsPath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath}/AlSeer/CategoryIcons/"
        AppConstants.productCatalogPath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath}/AlSeer/"
        AppConstants.baskinLogoPath = "${AppConstants.productCatalogPath}AlSeerLogo"
    }
}