package com.iti.mobile.covid19tracker.features.all_countries

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.FragmentAllCountriesBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.Country
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AllCountriesFragment : Fragment() {
    @Inject
    lateinit var viewmodelFactory: ViewModelProvidersFactory
    @Inject
    lateinit var viewModel: AllCountriesViewModel
    private lateinit var binding : FragmentAllCountriesBinding
    lateinit var countriesAdapter: CountriesAdapter
    private lateinit var allResultsAdapter: CountriesAdapter
    private lateinit var layoutManager: LinearLayoutManager
     lateinit var displayList: MutableList<Country>
     lateinit var countriesList: List<Country>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllCountriesBinding.inflate(layoutInflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(ControllerModule(
            activity as AppCompatActivity
        )).inject(this)
        viewModel = ViewModelProvider(this,viewmodelFactory).get(AllCountriesViewModel::class.java)
        displayList = mutableListOf()
        countriesList = listOf()
        setupRecycleView()
        viewModel.getCountriesData("cases").observe(requireActivity(), Observer {data ->
            countriesList = data
            displayList = data as MutableList<Country>
            displayCountries(countriesList)
        })

        return binding.root
    }

   private fun setupRecycleView (){
        layoutManager = LinearLayoutManager(activity)
        binding.allCountriesRecyclerview.setHasFixedSize(true)
        binding.allCountriesRecyclerview.layoutManager = layoutManager
    }

    fun displayCountries(countriesList: List<Country>) {
        countriesAdapter = CountriesAdapter(countriesList)
        binding.allCountriesRecyclerview.adapter = countriesAdapter
        countriesAdapter.notifyDataSetChanged()
        if (countriesAdapter.itemCount > 0) {
           // activity.visibility = View.INVISIBLE
        }
    }

    fun refereshData (){
        displayCountries(displayList)
    }


}
