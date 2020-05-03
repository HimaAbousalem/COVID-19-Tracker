package com.iti.mobile.covid19tracker.features.all_countries

import android.content.Context
import android.util.DisplayMetrics
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.model.entities.Country


class CountriesAdapter(countries: List<Country>) :
    RecyclerView.Adapter<CountriesAdapter.CountriesHolder>() {
    var countries: MutableList<Country>
    init {
        this.countries = countries as MutableList<Country>
    }
    inner class CountriesHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
         val country: TextView
         val confirmed: TextView
        val recovered: TextView
        val deaths: TextView
        val subscribe: AppCompatImageButton
         val imageView: ImageView
        init {
           country = itemView.findViewById(R.id.country_textview)
            confirmed = itemView.findViewById(R.id.confirmed_cases)
            recovered = itemView.findViewById(R.id.recovered_cases)
            deaths = itemView.findViewById(R.id.deaths_cases)
            subscribe =  itemView.findViewById(R.id.subscribe_button)
            imageView = itemView.findViewById(R.id.flag_imageview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CountriesHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.country_card_view,
            parent,
            false
        ) as View

        return CountriesHolder(v)
    }

    override fun onBindViewHolder(holder: CountriesHolder, position: Int) {
        holder.imageView.load(countries[position].countryInfo?.flag)
        holder.country.text = countries[position].country
        holder.confirmed.text = countries[position].active.toString()
        holder.deaths.text = countries[position].deaths.toString()
        holder.recovered.text = countries[position].recovered.toString()
        holder.subscribe.setOnClickListener{
//          if (countries[position].subscription == SUBSCRIBED){
//           //   holder.subscribe.background  = Resources.getSystem().getDrawable(R.drawable.ic_miscellaneous)
//              countries[position].subscription = UN_SUBSCRIBED
//          }  else {
//           //   holder.subscribe.background  = Resources.getSystem().getDrawable(R.drawable.ic_globe)
//              countries[position].subscription = SUBSCRIBED
//          }

        }
    }
    fun deleteCountry() {
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return countries.size
    }
    private fun getScreenHeight(context: Context){
        val dimension = DisplayMetrics()
         context.getSystemService(Context.WINDOW_SERVICE)
        val height = dimension.heightPixels
    }
}