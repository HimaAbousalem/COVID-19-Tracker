package com.iti.mobile.covid19tracker.features.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryInfo
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var repo:DataRepository
    @Inject
    lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        (application as Covid19App).appComponent.inject(this)
//        localDataSource.insert(Country(0,21212, "h", CountryInfo(111121, 1212,34343,"jjj" )
//           ,  2323,43453,2123123,3324 ) )
//
//        localDataSource.insert(Country(0,21212, "eee", CountryInfo(453541, 5345,34545,"rrr" )
//           , 2323,43453,2123123,3324 ) )
//
//       localDataSource.allCountries().subscribeOn(Schedulers.io())
//           .observeOn(AndroidSchedulers.mainThread())
//           .subscribe(
//               Consumer {
//                   Log.i("res", it[0].country)
//                   Log.i("res", it[1].country)
//               }
//           )
        repo.covidApi.getCountries("cases").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEach {
                    Log.d("res", it.toString())
                }
            }

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
