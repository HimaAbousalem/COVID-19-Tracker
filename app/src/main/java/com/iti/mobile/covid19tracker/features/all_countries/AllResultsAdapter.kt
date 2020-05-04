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
        val country: TextView = itemView.findViewById(R.id.country_textview)
        val confirmed: TextView = itemView.findViewById(R.id.confirmed_cases)
        val recovered: TextView = itemView.findViewById(R.id.recovered_cases)
        val deaths: TextView = itemView.findViewById(R.id.deaths_cases)
        private val subscribe  : AppCompatImageButton = itemView.findViewById(R.id.subscribe_button)
        private val showDetailsButton: AppCompatImageButton = itemView.findViewById(R.id.info_button)
        private val hideDetails: AppCompatImageButton = itemView.findViewById(R.id.hide_details_button)
        private val showingDetails : LinearLayout = itemView.findViewById(R.id.details_layout)

        val detailsCountryCardLayoutBinding : DetailsCountryCardLayoutBinding =
            DetailsCountryCardLayoutBinding.inflate(LayoutInflater.from(itemView.context),itemView.findViewById(R.id.details_layout))
        val imageView: ImageView = itemView.findViewById(R.id.flag_imageview)
        init {

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