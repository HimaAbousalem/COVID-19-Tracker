package com.iti.mobile.covid19tracker.features.all_countries

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.utils.Clickable
import com.iti.mobile.covid19tracker.utils.DrawCountryHistoryData
import com.iti.mobile.covid19tracker.utils.SUBSCRIBED
import com.iti.mobile.covid19tracker.utils.UN_SUBSCRIBED
import kotlinx.android.synthetic.main.details_country_card_layout.view.*


class CountriesAdapter(countries: List<Country>, val listener: Clickable) :
    RecyclerView.Adapter<CountriesAdapter.CountriesHolder>() {
    lateinit var detailsCountryCardLayoutBinding : DetailsCountryCardLayoutBinding
    var countries: MutableList<Country> = countries.toMutableList()
    lateinit var context: Context
    inner class CountriesHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val country: TextView = itemView.findViewById(R.id.country_textview)
        val confirmed: TextView = itemView.findViewById(R.id.confirmed_cases)
        val recovered: TextView = itemView.findViewById(R.id.recovered_cases)
        val deaths: TextView = itemView.findViewById(R.id.deaths_cases)
        val subscribe: AppCompatImageButton = itemView.findViewById(R.id.subscribe_button)
        val showDetailsButton: AppCompatImageButton
        val hideDetails: AppCompatImageButton
        val showingDetails : LinearLayout

        val imageView: ImageView
        init {
            imageView = itemView.findViewById(R.id.flag_imageview)
            hideDetails = itemView.findViewById(R.id.hide_details_button)
            showingDetails = itemView.findViewById(R.id.details_layout)
            showDetailsButton = itemView.findViewById(R.id.info_button)
            detailsCountryCardLayoutBinding = DetailsCountryCardLayoutBinding.inflate(LayoutInflater.from(itemView.context),itemView.findViewById(R.id.details_layout))
            hideDetails.setOnClickListener {
               itemView.performClick()
            }
            showDetailsButton.setOnClickListener {
                itemView.performClick()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CountriesHolder {
        context = parent.context
        val v = LayoutInflater.from(context).inflate(
            R.layout.country_card_view,
            parent,
            false
        ) as View
        return CountriesHolder(v)
    }

    override fun onBindViewHolder(holder: CountriesHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if(holder.showingDetails.isVisible) {
                holder.showDetailsButton.visibility = View.VISIBLE
                holder.hideDetails.visibility = View.GONE
                holder.showingDetails.visibility = View.GONE
            //   detailsCountryCardLayoutBinding.graphLayout.visibility = View.GONE
            }else {
                holder.showDetailsButton.visibility = View.GONE
                holder.hideDetails.visibility = View.VISIBLE
                holder.showingDetails.visibility = View.VISIBLE
               // detailsCountryCardLayoutBinding.graphLayout.visibility = View.VISIBLE

               //val countryName = countries[position].country
              //  listener.onItemClickGetCountryHistory(countryName, detailsCountryCardLayoutBinding)

            }
        }
        holder.imageView.load(countries[position].countryInfo?.flag)
        holder.country.text = countries[position].country
        holder.confirmed.text = countries[position].active.toString()
        holder.deaths.text = countries[position].deaths.toString()
        holder.recovered.text = countries[position].recovered.toString()
        detailsCountryCardLayoutBinding.criticalCases.text = countries[position].critical.toString()
         detailsCountryCardLayoutBinding.todayCases.text = countries[position].todayCases.toString()
        detailsCountryCardLayoutBinding.todayDeathsCases.text = countries[position].todayDeaths.toString()
        if(countries[position].subscription == SUBSCRIBED){
            holder.subscribe.setImageResource(R.drawable.ic_notifications_black_24dp)
        }else {
            holder.subscribe.setImageResource(R.drawable.ic_miscellaneous)
        }
        holder.subscribe.setOnClickListener{

          if (countries[position].subscription == SUBSCRIBED){
              countries[position].subscription = UN_SUBSCRIBED
              listener.onItemClick(countries[position])
          }  else {
              countries[position].subscription = SUBSCRIBED
              listener.onItemClick(countries[position])
          }
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

}