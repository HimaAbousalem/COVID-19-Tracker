package com.iti.mobile.covid19tracker.features.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.NavGraphNavigator
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.ActivityMainBinding
import com.iti.mobile.covid19tracker.features.all_countries.AllCountriesFragment
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryInfo
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreferenceHandler
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var localDataSource: LocalDataSource
    @Inject
    lateinit var sharedPreferenceHandler: SharedPreferenceHandler

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    lateinit var sharedPreference: SharedPreferences
     lateinit var allCountriesFragment: AllCountriesFragment
    lateinit var  navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        (application as Covid19App).appComponent.controllerComponent(ControllerModule(this)).inject(this)

        viewModel = viewModelProvidersFactory.create(MainViewModel::class.java)

        viewModel.getCountriesData("cases").observe(this, Observer {
            it.forEach { Log.d("data", it.toString()) }
        })

    }

    private fun setupBottomNavigation (){
        binding.bottomNavigation.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
         NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.settingsMenuItem) {
           //TODO:- open setting view
            return true
        }
        if (id == R.id.app_bar_search){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            if (navHostFragment != null) {
                allCountriesFragment = navHostFragment.childFragmentManager.fragments.get(0) as AllCountriesFragment
            }
            Log.d("search",allCountriesFragment.displayList.toString())
//            val searchView = item.actionView as SearchView
//            compositeDisposable.add( fromview(searchView)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { text ->
//                   // allCountriesFragment.displayList.clear()
//                    Log.d("search", "subscriber: $text")
//                    allCountriesFragment.displayList.forEach {
////                       if (it.country.startsWith(text))
////                        {
//                            allCountriesFragment.displayList.clear()
//                            allCountriesFragment.displayList.add(it)
//                            Log.d("search dis", allCountriesFragment.displayList.toString())
//                            allCountriesFragment.refereshData()
//
//                    //    }
//                    }
                }
        return super.onOptionsItemSelected(item)
    }

//    fun fromview(searchView: SearchView): Observable<String> {
//        // RxSearchView.queryTextChanges(searchView)
//        val observable = Observable
//            .create(ObservableOnSubscribe<String> { subscriber ->
//                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextChange(newText: String?): Boolean {
//                        subscriber.onNext(newText!!)
//                        return false
//                    }
//
//                    override fun onQueryTextSubmit(query: String?): Boolean {
//                        subscriber.onComplete()
//                        return false
//                    }
//                })
//            })
//            .map { text -> text.toLowerCase().trim() }
//            .debounce(250, TimeUnit.MILLISECONDS)
//            .distinct()
//            .filter { text -> text.isNotBlank() }
//        return observable
//    }

}
