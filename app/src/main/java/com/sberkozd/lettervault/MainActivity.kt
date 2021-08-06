package com.sberkozd.lettervault

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
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


        /*    val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.my_nav)
            navHostFragment.navController.graph = graph

            NavigationUI.setupWithNavController(binding.toolbar,navHostFragment.navController)

         */

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)



        toolbar.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener {
                nav, destination, arguments ->  }


        //setupActionBarWithNavController(navHostFragment.navController, AppBarConfiguration(navHostFragment.navController.graph))


    }

    
    override fun onSupportNavigateUp(): Boolean {
        return if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
        }
    }


}