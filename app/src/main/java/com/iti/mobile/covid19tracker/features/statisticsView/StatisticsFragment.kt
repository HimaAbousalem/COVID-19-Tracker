package com.iti.mobile.covid19tracker.features.statisticsView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.FragmentStatisticsBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.*
import com.iti.mobile.covid19tracker.utils.drawCountryHistoryData
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {
lateinit var binding: FragmentStatisticsBinding
    @Inject
    lateinit var viewmodelFactory: ViewModelProvidersFactory
    lateinit var viewModel: StatisticsViewModel
    lateinit  var countryHistoryDeferred: Deferred<CountryHistoryDetails>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(
            ControllerModule(
                activity as AppCompatActivity
            )
        ).inject(this)
        viewModel = ViewModelProvider(this, viewmodelFactory).get(StatisticsViewModel::class.java)

        viewModel.getAllHistory().observe(requireActivity(), Observer {
            when(it){
                is LoadingState-> { if(it.loading) Timber.d("Loading") else Timber.d("Finish Loading")}
                is SuccessState-> context?.let { context -> drawCountryHistoryData(binding, it.data, context) }
                is ErrorState -> Timber.d(it.exception)
            }
        })
        return binding.root
    }
}
