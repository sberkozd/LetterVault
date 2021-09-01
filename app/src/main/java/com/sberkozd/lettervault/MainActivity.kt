package com.sberkozd.lettervault

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back)

        // toolbar.navigationIcon.apply { colorFilter(resources.getColor(R.color.white)) }

        toolbar.setupWithNavController(navHostFragment.navController)


        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }


//
//    private fun saveData(){
//
//        val notificationPreference : String
//        val themePreference : String
//
//        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.apply{
//            putBoolean("DARKMODE_KEY", binding.darkModeSwitch.isChecked)
//            putBoolean("NOTIFICATION_KEY", binding.notificationSwitch.isChecked)
//        }.apply()
//
//        Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun loadPreferences(){
//        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        val savedBooleanDarkMode = sharedPreferences.getBoolean("DARKMODE_KEY", false)
//        val savedBooleanNotification = sharedPreferences.getBoolean("NOTIFICATION_KEY", true)
//
//        binding.darkModeSwitch.isChecked = savedBooleanDarkMode
//        binding.notificationSwitch.isChecked = savedBooleanNotification
//    }


    override fun onSupportNavigateUp(): Boolean {
        return if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
        }
    }
}