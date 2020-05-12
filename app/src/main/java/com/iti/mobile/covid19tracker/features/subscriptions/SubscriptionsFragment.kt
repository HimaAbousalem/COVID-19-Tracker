package com.iti.mobile.covid19tracker.features.subscriptions

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.databinding.FragmentSubscriptionsBinding
import com.iti.mobile.covid19tracker.databinding.SettingsViewBinding
import com.iti.mobile.covid19tracker.features.base.Covid19App
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.utils.Clickable
import com.iti.mobile.covid19tracker.utils.setupNotification
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
    @Inject
    lateinit var dataRepository: DataRepository
    lateinit var viewModel: SubscriptionsViewModel
    private lateinit var dialog: Dialog
    private lateinit var settingsViewBinding: SettingsViewBinding
    var adapter: SubscriptionsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSubscriptionsBinding.inflate(inflater)
        (activity?.application as Covid19App).appComponent.controllerComponent(ControllerModule(activity as AppCompatActivity)).inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SubscriptionsViewModel::class.java)

        setupRecycleView()
        setupToolbar()
        setupSettingView()
        viewModel.subscribedCountriesData.observe(requireActivity(), Observer {subscription->
            if(subscription.isEmpty()){
                showNoDataLayout()
                adapter?.submitList(subscription)
            }else {
              //  binding.lottieCovidSub.visibility = View.VISIBLE
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
       // binding.lottieCovidSub.visibility = View.GONE
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
    private fun setupSettingView (){
        dialog = Dialog( this.requireContext() )
        dialog.setTitle("Settings")
        settingsViewBinding = SettingsViewBinding.inflate(layoutInflater)
        dialog.setContentView(settingsViewBinding.root)
        settingsViewBinding.cancelSetting.setOnClickListener {
            dialog.dismiss()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.settings_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMenuItem2) {
            val time =  dataRepository.getNotificationSettings()
            settingsViewBinding.switchGroup.isChecked = time != 0L
            setupNotification(settingsViewBinding,requireActivity(),dialog,time,this)
            dialog.show()
            return true
        }
        return false
    }

    override fun updateNotificationTime(time: Long, isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateNotificationSettings(time,isEnabled)
        }
    }

}
