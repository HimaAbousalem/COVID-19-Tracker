package com.iti.mobile.covid19tracker.features.all_countries

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.databinding.FragmentAllCountriesBinding
import com.iti.mobile.covid19tracker.databinding.SettingsViewBinding
import com.iti.mobile.covid19tracker.extension.hideKeyboard
import com.iti.mobile.covid19tracker.extension.toast
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.features.subscriptions.SubscriptionsAdapter

import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.utils.Clickable
import com.iti.mobile.covid19tracker.utils.DrawCountryHistoryData
import com.iti.mobile.covid19tracker.utils.setupNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AllCountriesFragment : Fragment(), Clickable {
    @Inject
    lateinit var viewmodelFactory: ViewModelProvidersFactory
    lateinit var viewModel: AllCountriesViewModel
    private lateinit var binding: FragmentAllCountriesBinding
    lateinit var countriesAdapter: CountriesAdapter
    private lateinit var allResultsAdapter: AllResultsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var settingsViewBinding: SettingsViewBinding
    private lateinit var dialog: Dialog
    lateinit var displayList: MutableList<Country>
    lateinit var countriesList: List<Country>
    lateinit var allResults: AllResults
    lateinit var mergeAdapter: MergeAdapter
    var isSearchStarted = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCountriesBinding.inflate(layoutInflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(
            ControllerModule(
                activity as AppCompatActivity
            )
        ).inject(this)
        viewModel = ViewModelProvider(this, viewmodelFactory).get(AllCountriesViewModel::class.java)
//        WorkManager.getInstance(requireActivity()).enqueue(
//            OneTimeWorkRequestBuilder<SyncWork>().build()
//        )
        displayList = mutableListOf()
        countriesList = listOf()
        setupRecycleView()
        setupSettingView()
        fetchData()
        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        val toolbar = binding.appToolBar.appToolBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "All Affected Countries"
    }


    private fun setupRecycleView() {
        displayList = mutableListOf()
        countriesList = listOf()
        allResults = AllResults()
        layoutManager = LinearLayoutManager(activity)
        binding.allCountriesRecyclerview.setHasFixedSize(true)
        binding.allCountriesRecyclerview.layoutManager = layoutManager
        //countriesAdapter = SubscriptionsAdapter( this)
        countriesAdapter = CountriesAdapter(countriesList,this)
        allResultsAdapter = AllResultsAdapter(allResults)
        allResultsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mergeAdapter = MergeAdapter(allResultsAdapter, countriesAdapter)
        binding.allCountriesRecyclerview.adapter = mergeAdapter
    }

    private fun fetchData() {
        //TODO we need to check if this is the firstTime or not + check the internet!
//        CoroutineScope(Dispatchers.IO).launch {
//            viewModel.updateDatabase()
//        }

        viewModel.countriesData.observe(requireActivity(), Observer { data ->
            if(data.isNotEmpty()){
                countriesList = data
                if(isSearchStarted)
                    displayCountries(countriesList)
            }else{
                //TODO Show NO Internet UI
                activity?.toast("No Internet!")
            }
        })
        viewModel.allCountriesResult.observe(requireActivity(), Observer {
           // Timber.d("Observer : $it")
            allResults = it
            allResultsAdapter.allResults = it
            mergeAdapter.adapters.first().notifyDataSetChanged()
        })

    }
    fun setupSettingView (){
        dialog = Dialog( this.requireContext() )
        dialog.setTitle("Settings")
        settingsViewBinding = SettingsViewBinding.inflate(layoutInflater)
        dialog.setContentView(settingsViewBinding.root)
        setupNotification(settingsViewBinding)
        settingsViewBinding.cancelSetting.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun displayCountries(countriesList: List<Country>) {
        countriesAdapter.countries = countriesList.toMutableList()
       // countriesAdapter.submitList(countriesList)
        mergeAdapter.adapters.last().notifyDataSetChanged()
        if (countriesAdapter.itemCount == 0) {
            binding.noDataLayout.noDataTextView.visibility = View.VISIBLE
            binding.noDataLayout.noDataTextView.text = "nooo"
//                Resources.getSystem().getText(R.string.no_internet)
            binding.noDataLayout.retryAgainButton.visibility = View.VISIBLE
            binding.noDataLayout.retryAgainButton.setOnClickListener {
                //TODO:- check internet and call worker
            }
            binding.allCountriesRecyclerview.visibility = View.GONE
        }else {
            binding.allCountriesRecyclerview.visibility = View.VISIBLE
            binding.noDataLayout.noDataTextView.visibility = View.GONE
        }
    }

    //Search on countries
    private fun fromView(searchView: SearchView): MutableLiveData<String> {
        var liveData = MutableLiveData<String>()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText.toString()
                if (text.matches(Regex("[a-z A-Z]*"))) {
                    liveData.value = text.trim()
                    isSearchStarted = false
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                liveData.value = query
                hideKeyboard()
                isSearchStarted = false
                return false
            }
        })
        searchView.setOnCloseListener { hideKeyboard(); true }
        return liveData
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMenuItem) {
            dialog.show()
            return true
        }
        if (item.itemId == R.id.app_bar_search) {
            val searchView = item.actionView as SearchView
            fromView(searchView).observe(this, Observer { word ->
                if (word.isNotEmpty()) {
                    displayList.clear()
                    countriesList.forEach {
                        if (it.country.contains(word, ignoreCase = true)) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                } else {
                    displayCountries(countriesList)
                    isSearchStarted = true
                }
            })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(country: Country) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateCountry(country)
        }
    }


}
