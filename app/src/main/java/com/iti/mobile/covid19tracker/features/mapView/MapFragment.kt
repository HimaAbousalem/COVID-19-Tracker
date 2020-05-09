package com.iti.mobile.covid19tracker.features.mapView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.FragmentMapBinding
import com.iti.mobile.covid19tracker.features.all_countries.AllCountriesViewModel
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.entities.CountryHistoryDetails
import com.iti.mobile.covid19tracker.utils.DrawCountryHistoryData
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {
lateinit var binding: FragmentMapBinding
    @Inject
    lateinit var viewmodelFactory: ViewModelProvidersFactory
    lateinit var viewModel: MapViewModel
    lateinit  var countryHistoryDeferred: Deferred<CountryHistoryDetails>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(
            ControllerModule(
                activity as AppCompatActivity
            )
        ).inject(this)
        viewModel = ViewModelProvider(this, viewmodelFactory).get(MapViewModel::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            countryHistoryDeferred = async {
                    viewModel.getAllHistory()
                }
             val countryHistory = countryHistoryDeferred.await()
            if (countryHistoryDeferred.isCompleted) {
              //  Log.i("char compl", countryHistory.toString())
                viewModel.historyObserver.postValue(countryHistory)

            } else {
               // Log.i("graph", countryHistoryDeferred.toString())
            }
        }
        viewModel.historyObserver.observe(requireActivity(),
            Observer {
                context?.let { context -> DrawCountryHistoryData(binding,it, context) }
            })

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        countryHistoryDeferred.cancel(CancellationException("fragment detached!"))
    }

}
