package com.iti.mobile.covid19tracker.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.ActivityMainBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        (application as Covid19App).appComponent.controllerComponent(ControllerModule(this)).inject(this)
        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            //do Noting.
        }
    }

    private fun setupBottomNavigation (){
//        binding.bottomNavigation.itemIconTintList = null
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
         NavigationUI.setupWithNavController(binding.bottomNavigation,navController)
    }
}
