package com.iti.mobile.covid19tracker.features.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreference
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var localDataSource: LocalDataSource
    @Inject
    lateinit var sharedPreference: SharedPreference
     lateinit var allCountriesFragment: AllCountriesFragment
    lateinit var  navController: NavController
    lateinit var compositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        (application as Covid19App).appComponent.controllerComponent(ControllerModule(this)).inject(this)
         compositeDisposable = CompositeDisposable()
    }

    private fun setupBottomNavigation (){
        binding.bottomNavigation.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
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
        if (id == R.id.app_bar_search){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            if (navHostFragment != null) {
                allCountriesFragment = navHostFragment.childFragmentManager.fragments.get(0) as AllCountriesFragment
            }
            Log.d("search",allCountriesFragment.displayList.toString())
            val searchView = item.actionView as SearchView
            compositeDisposable.add( fromview(searchView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text ->
                   // allCountriesFragment.displayList.clear()
                    Log.d("search", "subscriber: $text")
                    allCountriesFragment.displayList.forEach {
//                       if (it.country.startsWith(text))
//                        {
                            allCountriesFragment.displayList.clear()
                            allCountriesFragment.displayList.add(it)
                            Log.d("search dis", allCountriesFragment.displayList.toString())
                            allCountriesFragment.refereshData()

                    //    }
                    }
                }
            )
        }
        return super.onOptionsItemSelected(item)
    }

    fun fromview(searchView: SearchView): Observable<String> {
        // RxSearchView.queryTextChanges(searchView)
        val observable = Observable
            .create(ObservableOnSubscribe<String> { subscriber ->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        subscriber.onNext(newText!!)
                        return false
                    }

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        subscriber.onComplete()
                        return false
                    }
                })
            })
            .map { text -> text.toLowerCase().trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
        return observable
    }

    fun test (){
      //  localDataSource.insert(Country(0,21212, "h", CountryInfo(111121, 12.12,34.343,"jjj" )
//           ,  2323,43453,2123123,3324 ) )
//
//        localDataSource.insert(Country(0,21212, "eee", CountryInfo(453541, 53.45,345.45,"rrr" )
//           , 2323,43453,2123123,3324 ) )

//                localDataSource.allCountries().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//                Log.d("res", it.isEmpty().toString())
//            }
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
}
