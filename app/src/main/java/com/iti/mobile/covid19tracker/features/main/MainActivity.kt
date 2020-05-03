package com.iti.mobile.covid19tracker.features.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.ActivityMainBinding
import com.iti.mobile.covid19tracker.features.all_countries.AllCountriesFragment
import com.iti.mobile.covid19tracker.features.base.Covid19App
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel
    lateinit var  navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        (application as Covid19App).appComponent.controllerComponent(ControllerModule(this)).inject(this)

        viewModel = viewModelProvidersFactory.create(MainViewModel::class.java)

//        viewModel.getCountriesData("cases").observe(this, Observer {
//            it.forEach { Log.d("data", it.toString()) }
//        })

    }

    private fun setupBottomNavigation (){
        binding.bottomNavigation.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
         NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

    }


}
