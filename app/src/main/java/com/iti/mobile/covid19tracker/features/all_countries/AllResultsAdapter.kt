package com.iti.mobile.covid19tracker.features.all_countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.model.entities.AllResults


class AllResultsAdapter(var allResults: AllResults) :
    RecyclerView.Adapter<AllResultsAdapter.AllResultsHolder>() {

    inner class AllResultsHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val country: TextView
        val confirmed: TextView
        val recovered: TextView
        val deaths: TextView
        val subscribe  : AppCompatImageButton
        val showDetailsButton: AppCompatImageButton
        val hideDetails: AppCompatImageButton
        val showingDetails : LinearLayout

        val detailsCountryCardLayoutBinding : DetailsCountryCardLayoutBinding
        val imageView: ImageView
        init {
            country = itemView.findViewById(R.id.country_textview)
            confirmed = itemView.findViewById(R.id.confirmed_cases)
            recovered = itemView.findViewById(R.id.recovered_cases)
            deaths = itemView.findViewById(R.id.deaths_cases)
            subscribe =  itemView.findViewById(R.id.subscribe_button)
            imageView = itemView.findViewById(R.id.flag_imageview)
            hideDetails = itemView.findViewById(R.id.hide_details_button)
            showingDetails = itemView.findViewById(R.id.details_layout)
            showDetailsButton = itemView.findViewById(R.id.info_button)
            detailsCountryCardLayoutBinding = DetailsCountryCardLayoutBinding.inflate(LayoutInflater.from(itemView.context),itemView.findViewById(R.id.details_layout))

            subscribe.visibility = View.GONE
            hideDetails.setOnClickListener {
                itemView.performClick()
            }
            showDetailsButton.setOnClickListener {
                itemView.performClick()
            }
            itemView.setOnClickListener {
                if(showingDetails.isVisible) {
                    showDetailsButton.visibility = View.VISIBLE
                    hideDetails.visibility = View.GONE
                    showingDetails.visibility = View.GONE
                }else {
                    showDetailsButton.visibility = View.GONE
                    hideDetails.visibility = View.VISIBLE
                    showingDetails.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AllResultsHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.country_card_view,
            parent,
            false
        ) as View
        return AllResultsHolder(v)
    }

    override fun onBindViewHolder(holder: AllResultsHolder, position: Int) {
        holder.confirmed.text = allResults.cases.toString()
        holder.deaths.text = allResults.deaths.toString()
        holder.recovered.text = allResults.recovered.toString()
        holder.detailsCountryCardLayoutBinding.criticalCases.text = allResults.critical.toString()
        holder.detailsCountryCardLayoutBinding.todayCases.text = allResults.todayCases.toString()
        holder.detailsCountryCardLayoutBinding.todayDeathsCases.text = allResults.todayDeaths.toString()

    }

    override fun getItemCount(): Int {
        var count = 1;
        if (allResults.cases == 0) {
            count = 0
        }
        return count
    }

}