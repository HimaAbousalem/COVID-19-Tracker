package com.iti.mobile.covid19tracker.features.all_countries

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.FragmentAllCountriesBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
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
        binding = FragmentAllCountriesBinding.inflate(layoutInflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(ControllerModule(
            activity as AppCompatActivity
        )).inject(this)
        viewModel = ViewModelProvider(this,viewmodelFactory).get(AllCountriesViewModel::class.java)
        displayList = mutableListOf()
        countriesList = listOf()
        setupRecycleView()
        setupToolbar()
        fetchData()

        return binding.root
    }

    private fun setupToolbar(){
        setHasOptionsMenu(true);
        val toolbar = binding.appToolBar.appToolBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "All Affected Countries"
    }
   private fun setupRecycleView (){
        layoutManager = LinearLayoutManager(activity)
        binding.allCountriesRecyclerview.setHasFixedSize(true)
        binding.allCountriesRecyclerview.layoutManager = layoutManager
    }

    private fun fetchData (){
        //TODO we need to check if this is the firstTime or not + check the internet!
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateDatabase()
        }

        viewModel.countriesData.observe(requireActivity(), Observer {data ->
            countriesList = data
            displayList = data as MutableList<Country>
            displayCountries(countriesList)
        })

        viewModel.allCountriesResult.observe(requireActivity(), Observer {
            Timber.d(it.toString())
        })
    }

    fun displayCountries(countriesList: List<Country>) {
        countriesAdapter = CountriesAdapter(countriesList)
        binding.allCountriesRecyclerview.adapter = countriesAdapter
        countriesAdapter.notifyDataSetChanged()
        if (countriesAdapter.itemCount > 0) {
           // activity.visibility = View.INVISIBLE
        }
    }

    fun fromView(searchView: SearchView): MutableLiveData< String> {
        var liveData = MutableLiveData<String>()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                       val text = newText.orEmpty()
                            .map { text -> text.toLowerCase() }
                            .distinct()
                           .toString()
                          liveData.value = text

                        return false
                    }
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        liveData.value = query
                        return false
                    }
                })

        return liveData
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMenuItem) {
            //TODO:- open setting view
            return true
        }
        if (item.itemId == R.id.app_bar_search){
            val searchView = item.actionView as SearchView
            Log.d("search","before "+ countriesList.toString())
            fromView(searchView).observe(this, Observer { word ->
                displayList.clear()
                Log.d("search", countriesList.toString())
                countriesList.forEach {
                  if(it.country.contains(word)){
                      displayList.add(it)
                  }
                }
                displayCountries(displayList)
              Log.d("search", word.toString())
        })
     }
        return super.onOptionsItemSelected(item)
    }


}
