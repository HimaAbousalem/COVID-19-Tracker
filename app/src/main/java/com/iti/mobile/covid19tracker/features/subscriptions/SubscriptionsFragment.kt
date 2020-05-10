package com.iti.mobile.covid19tracker.features.subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.databinding.FragmentSubscriptionsBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.utils.Clickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SubscriptionsFragment : Fragment(), Clickable {

    lateinit var binding: FragmentSubscriptionsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvidersFactory
    lateinit var viewModel: SubscriptionsViewModel
    var adapter: SubscriptionsAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSubscriptionsBinding.inflate(inflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(ControllerModule(activity as AppCompatActivity)).inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SubscriptionsViewModel::class.java)

        setupRecycleView()
        setupToolbar()

        viewModel.subscribedCountriesData.observe(requireActivity(), Observer {subscription->
            if(subscription.isEmpty()){
                showNoDataLayout()
                adapter?.submitList(subscription)
            }else {
                binding.subscriptionRecycler.visibility = View.VISIBLE
                binding.noDataLayout.noDataLayout.visibility = View.GONE
                binding.noDataLayout.noDataLottieCovid.cancelAnimation()
                adapter?.submitList(subscription)
            }
        })
        return binding.root
    }

    private fun setupRecycleView() {
        binding.subscriptionRecycler.layoutManager = LinearLayoutManager(requireActivity())
        adapter = SubscriptionsAdapter(this)
        binding.subscriptionRecycler.adapter = adapter
    }

    fun showNoDataLayout (){
        binding.subscriptionRecycler.visibility = View.GONE
        binding.noDataLayout.noDataLottieCovid.setAnimation(R.raw.nonotifications)
        binding.noDataLayout.noDataLayout.visibility = View.VISIBLE
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        val toolbar = binding.appToolBar.appToolBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Subscriptions"
    }

    override fun onItemClick(country: Country) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateCountry(country)
        }
    }

}
