package com.iti.mobile.covid19tracker.features.subscriptions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.utils.SUBSCRIBED
import com.iti.mobile.covid19tracker.utils.UN_SUBSCRIBED
import kotlinx.android.synthetic.main.country_card_view.view.*

class SubscriptionsAdapter: ListAdapter<Country, SubscriptionsAdapter.CountriesViewHolder>(CountriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_card_view, parent, false))

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.setCountryData(getItem(position))
    }

    inner class CountriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val detailsCountryCardLayoutBinding : DetailsCountryCardLayoutBinding =
            DetailsCountryCardLayoutBinding.inflate(LayoutInflater.from(itemView.context),itemView.findViewById(R.id.details_layout))

        init{
            itemView.hide_details_button.setOnClickListener {itemView.performClick()}
            itemView.info_button.setOnClickListener {itemView.performClick()}

            itemView.setOnClickListener {
                if(itemView.details_layout.isVisible) {
                    itemView.info_button.visibility = View.VISIBLE
                    itemView.hide_details_button.visibility = View.GONE
                    itemView.details_layout.visibility = View.GONE
                }else {
                    itemView.info_button.visibility = View.GONE
                    itemView.hide_details_button.visibility = View.VISIBLE
                    itemView.details_layout.visibility = View.VISIBLE
                }
            }
        }

        fun setCountryData(countryData: Country){
            itemView.flag_imageview.load(countryData.countryInfo?.flag)
            itemView.country_textview.text = countryData.country
            itemView.confirmed_cases.text = countryData.active.toString()
            itemView.deaths_cases.text = countryData.deaths.toString()
            itemView.recovered_cases.text = countryData.recovered.toString()
            detailsCountryCardLayoutBinding.criticalCases.text = countryData.critical.toString()
            detailsCountryCardLayoutBinding.todayCases.text = countryData.todayCases.toString()
            detailsCountryCardLayoutBinding.todayDeathsCases.text = countryData.todayDeaths.toString()
            if(countryData.subscription == SUBSCRIBED){
                itemView.subscribe_button.setImageResource(R.drawable.ic_notifications_black_24dp)
            }else {
                itemView.subscribe_button.setImageResource(R.drawable.ic_miscellaneous)
            }
            itemView.subscribe_button.setOnClickListener{
                if (countryData.subscription == SUBSCRIBED){
                    itemView.subscribe_button.setImageResource(R.drawable.ic_miscellaneous)
                    countryData.subscription = UN_SUBSCRIBED
                }  else {
                    itemView.subscribe_button.setImageResource(R.drawable.ic_notifications_black_24dp)
                    countryData.subscription = SUBSCRIBED
                }
            }
        }
    }

}

private class CountriesDiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(
        oldItem: Country,
        newItem: Country
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Country,
        newItem: Country
    ): Boolean {
        return oldItem == newItem
    }
}