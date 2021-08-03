package com.sberkozd.lettervault

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment


        /*    val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.my_nav)
            navHostFragment.navController.graph = graph

            NavigationUI.setupWithNavController(binding.toolbar,navHostFragment.navController)

         */

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        toolbar.setupWithNavController(navHostFragment.navController)


        //setupActionBarWithNavController(navHostFragment.navController, AppBarConfiguration(navHostFragment.navController.graph))


    }

}