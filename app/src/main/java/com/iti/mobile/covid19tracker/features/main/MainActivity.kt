package com.iti.mobile.covid19tracker.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.ActivityMainBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryInfo
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreferenceHandler
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var repo:DataRepository
    @Inject
    lateinit var localDataSource: LocalDataSource
    @Inject
    lateinit var sharedPreferenceHandler: SharedPreferenceHandler

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        (application as Covid19App).appComponent.controllerComponent(ControllerModule(this)).inject(this)

        viewModel = viewModelProvidersFactory.create(MainViewModel::class.java)

        viewModel.getCountriesDat("cases").observe(this, Observer {
            it.forEach { Log.d("data", it.toString()) }
        })



//        repo.getCountries("cases")
//            .subscribeOn(Schedulers.io())
//            .flatMap { data-> Observable.fromIterable(data) }
//            .map{data-> localDataSource.insert(data)}
//            .flatMap { localDataSource.allCountries() }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                Log.d("data", it.size.toString())
//            }
      // localDataSource.update(Country(country="Egypt", subscribtion = 0))
//        Country(updated=1588368816064, country="Egypt", countryInfo= CountryInfo(id=818, lat=27.0, long=0.0, flag="https://corona.lmao.ninja/assets/img/flags/eg.png"), continent="Africa", cases=5895, todayCases=358, deaths=406, todayDeaths=14, recovered=1460, active=4029, critical=0, casesPerOneMillion=58, deathsPerOneMillion=4, tests=90000, testsPerOneMillion=870, subscribtion = 0)
//        localDataSource.allCountries().subscribe{
//            it.forEach{
//                Log.d("data", it.toString())
//            }
//        }
//
//        Observable.combineLatest(
//            repo.getCountries("cases"),
//            localDataSource.allCountries(),
//            BiFunction { api, database -> {
//                database.filter { country -> country.subscribtion == 0 }
//                    .forEach {
//                        api.forEach {ap->
//                            if(ap.country == it.country){
//                                ap.subscribtion = 0
//                            }
//                    }
//                    }
//            }
//            return@BiFunction api}).subscribe{
//            it.forEach {
//                Log.d("data", it.toString())
//            }
//        }

//
//        sharedPreferenceHandler.observingSharedPreferenceDataChange()
//            .subscribe{data -> Log.d("data", data.toString())}
//
//        for(i in 0..10){
//            val allResults = AllResults(212, i, i, 444, 121, 45)
//            sharedPreferenceHandler.saveAllCountriesResult(allResults)
//            Thread.sleep(1000)
//        }
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
        binding.bottomNavigation.itemIconTintList = null;
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
