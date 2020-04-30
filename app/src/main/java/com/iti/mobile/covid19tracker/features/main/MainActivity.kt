package com.iti.mobile.covid19tracker.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.ActivityMainBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreference
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var repo:DataRepository
    @Inject
    lateinit var localDataSource: LocalDataSource
    @Inject
    lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        (application as Covid19App).appComponent.controllerComponent(ControllerModule(this)).inject(this)
//        localDataSource.insert(Country(0,21212, "h", CountryInfo(111121, 12.12,34.343,"jjj" )
//           ,  2323,43453,2123123,3324 ) )
//
//        localDataSource.insert(Country(0,21212, "eee", CountryInfo(453541, 53.45,345.45,"rrr" )
//           , 2323,43453,2123123,3324 ) )

//       localDataSource.allCountries().subscribeOn(Schedulers.io())
//           .observeOn(AndroidSchedulers.mainThread())
//           .subscribe{
//               it.forEach {
//                   Log.d("res", it.toString())
//               }
//           }
//        repo.covidApi.getCountries("cases").subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                it.forEach {
//                    Log.d("res", it.toString())
//                }
//            }
//        repo.covidApi.getFullResults().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//                Log.d("res", it.toString())
//            }
//        repo.getCountriesData("cases").subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({data-> data.forEach { Log.d("res", it.toString()) }}, {thowable-> Log.d("res", thowable.message)})

//        sharedPreference.saveAllCountriesResult(AllResults(22,34,4354,23123,12312,23213))
//        sharedPreference.allCountriesResults.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                Consumer {
//                    Log.i("res", it.toString())
//                }
//            )
    }

    fun setupBottomNavigation (){
        binding.bottomNavigation.setItemIconTintList(null);
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
         NavigationUI.setupWithNavController(binding.bottomNavigation,navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.settingsMenuItem) {
           //TODO:- open setting view
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
