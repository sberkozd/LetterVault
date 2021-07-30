package com.sberkozd.lettervault

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.sberkozd.lettervault.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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

        toolbar.title = "LetterVault"

        toolbar.setupWithNavController(navHostFragment.navController)

        //setupActionBarWithNavController(navHostFragment.navController, AppBarConfiguration(navHostFragment.navController.graph))


    }

}