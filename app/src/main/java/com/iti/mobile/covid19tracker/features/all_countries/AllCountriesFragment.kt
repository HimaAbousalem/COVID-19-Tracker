package com.iti.mobile.covid19tracker.features.all_countries

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.google.android.material.button.MaterialButton
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.FragmentAllCountriesBinding
import com.iti.mobile.covid19tracker.databinding.SettingsViewBinding
import com.iti.mobile.covid19tracker.extension.hideKeyboard
import com.iti.mobile.covid19tracker.extension.toast
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.utils.Clickable
import com.iti.mobile.covid19tracker.utils.setupNotification
import kotlinx.coroutines.*
import javax.inject.Inject


class AllCountriesFragment : Fragment(), Clickable {
    @Inject
    lateinit var viewmodelFactory: ViewModelProvidersFactory
    lateinit var viewModel: AllCountriesViewModel
    private lateinit var binding: FragmentAllCountriesBinding
    lateinit var countriesAdapter: CountriesAdapter
    private lateinit var settingsViewBinding: SettingsViewBinding
    private lateinit var dialog: Dialog
    lateinit var displayList: MutableList<Country>
    lateinit var countriesList: List<Country>
    lateinit var mergeAdapter: MergeAdapter
    var isFirstLoading = true
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
        displayList = mutableListOf()
        countriesList = listOf()
        setupRecycleView()
        setupSettingView()
        isFirstLoading = true
        fetchData()
        setupToolbar()
        setupSwipeToRefresh()
        filterByContinent()
        return binding.root
    }

     //setup views
    private fun setupToolbar() {
        setHasOptionsMenu(true)
        val toolbar = binding.appToolBar.appToolBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "All Countries"
    }
    private fun setupRecycleView() {
        displayList = mutableListOf()
        countriesList = listOf()
        binding.allCountriesRecyclerview.setHasFixedSize(true)
        binding.allCountriesRecyclerview.layoutManager = LinearLayoutManager(activity)
        countriesAdapter = CountriesAdapter(countriesList,this)
        mergeAdapter = MergeAdapter(countriesAdapter)
        binding.allCountriesRecyclerview.adapter = mergeAdapter

    }
    private fun setupSettingView (){

        dialog = Dialog( this.requireContext() )
        dialog.setTitle("Settings")
        settingsViewBinding = SettingsViewBinding.inflate(layoutInflater)
        dialog.setContentView(settingsViewBinding.root)
        viewModel.getNotificationSettings.observe(requireActivity(), Observer { lastTime ->

            settingsViewBinding.switchGroup.isChecked = lastTime != 0L
            setupNotification(settingsViewBinding,requireActivity(),dialog,lastTime,this)
        })
        settingsViewBinding.cancelSetting.setOnClickListener {
            dialog.dismiss()
        }

    }
    private fun setupSwipeToRefresh (){
        binding.swiperefreshItems.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                var result= viewModel.pullToRefreshLogic()
                withContext(Dispatchers.Main) {
                    activity?.toast(result)
                }
                if ( binding.swiperefreshItems.isRefreshing) {
                    binding.swiperefreshItems.isRefreshing = false
                }
            }
        }
    }
    //show data
    private fun fetchData() {
        viewModel.countriesData.observe(requireActivity(), Observer { data ->
            if(data.isNotEmpty()){
                if(isFirstLoading){
                    countriesList = data
                    isFirstLoading = false
                    displayCountries(countriesList)
                }
            }else{
                showNoDataLayout()
            }
        })

    }
    private fun displayCountries(countriesList: List<Country>) {
        binding.allCountriesRecyclerview.visibility = View.VISIBLE
        binding.noDataLayout.noDataLayout.visibility = View.GONE
        countriesAdapter.countries = countriesList.toMutableList()
        mergeAdapter.adapters.last().notifyDataSetChanged()
    }
    private fun showNoDataLayout (){
        binding.allCountriesRecyclerview.visibility = View.GONE
        binding.noDataLayout.noDataLayout.visibility = View.VISIBLE
        binding.noDataLayout.noDataTextView.text = "No Internet Connection is available!"
    }

    //Search on countries
    private fun filterByContinent (){
        binding.filterGroup.forEach { button ->
            button.setOnClickListener { (button as MaterialButton).isChecked = true }
        }
        binding.filterGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            displayList.clear()
            if(binding.all.id == checkedId){
                if (isChecked){
                    displayList.addAll(countriesList)
                    displayCountries(displayList)
                }
            }
            if(binding.africa.id == checkedId){
                if (isChecked){
                    countriesList.forEach {
                        if (it.continent.equals("Africa")) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                }
            }
            if (binding.asia.id == checkedId){
                if (isChecked){
                    countriesList.forEach {
                        if (it.continent.equals("Asia")) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                }

            }
            if(binding.aust.id == checkedId){
                if (isChecked){
                    countriesList.forEach {
                        if (it.continent.equals("Australia/Oceania")) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                }

            }
            if(binding.northA.id == checkedId){
                if (isChecked){
                    countriesList.forEach {
                        if (it.continent.equals("North America")) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                }

            }
            if (binding.southA.id == checkedId){
                if (isChecked){
                    countriesList.forEach {
                        if (it.continent.equals("South America")) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                }

            }
            if(binding.euro.id == checkedId){
                if (isChecked){
                    countriesList.forEach {
                        if (it.continent.equals("Europe")) {
                            displayList.add(it)
                        }
                    }
                    displayCountries(displayList)
                }
            }
        }
    }
    private fun fromView(searchView: SearchView): MutableLiveData<String> {
        var liveData = MutableLiveData<String>()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText.toString()
                if (text.matches(Regex("[a-z A-Z]*"))) {
                    liveData.value = text.trim()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                liveData.value = query
                hideKeyboard()
                return false
            }
        })
        searchView.setOnCloseListener {
            hideKeyboard()
            binding.lottieCovid.visibility = View.VISIBLE
            binding.viewColor.visibility = View.VISIBLE
            true
        }
        searchView.setOnCloseListener{
            hideKeyboard()
            true
        }
        return liveData
    }

    //app bar
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
            item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    binding.lottieCovid.visibility = View.GONE
                    binding.viewColor.visibility = View.GONE
                    binding.filterGroup.visibility = View.GONE
                    binding.filterText.visibility = View.GONE
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    binding.lottieCovid.visibility = View.VISIBLE
                    binding.viewColor.visibility = View.VISIBLE
                    binding.filterGroup.visibility = View.VISIBLE
                    binding.filterText.visibility = View.VISIBLE
                    binding.filterGroup.check(binding.all.id)
                   return true
                }

            })
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

    override fun updateNotificationTime(time: Long, isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateNotificationSettings(time,isEnabled)
        }
    }


}

