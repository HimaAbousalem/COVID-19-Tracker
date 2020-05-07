package com.iti.mobile.covid19tracker.utils

import android.content.Context
import android.util.Log

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.FillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.entities.CountryHistoryDetails
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import kotlinx.android.synthetic.main.activity_graph_test.view.*
import kotlinx.android.synthetic.main.details_country_card_layout.view.*
import kotlinx.android.synthetic.main.statistics_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun setDataIntoGraph ( binding: DetailsCountryCardLayoutBinding, baseContext : Context, countryHistoryDetails: CountryHistoryDetails ){
   //all cases
    val casesLineDataSet = LineDataSet(countryHistoryDetails.cases?.let { datavalues(it) },"Cases")
    casesLineDataSet.setCircleColor(ContextCompat.getColor(baseContext, R.color.white))
    casesLineDataSet.setColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
    casesLineDataSet.setDrawFilled(true)
    casesLineDataSet.setFillFormatter(FillFormatter { dataSet, dataProvider ->
        return@FillFormatter binding.graphLayout.chart.axisLeft.axisMinimum
    })
    casesLineDataSet.fillAlpha = 1
    //deaths
    val deathsLineDataSet = LineDataSet(countryHistoryDetails.deaths?.let { datavalues(it) },"Deaths")
    deathsLineDataSet.setDrawFilled(true)
    deathsLineDataSet.setFillFormatter(FillFormatter { dataSet, dataProvider ->
        return@FillFormatter binding.graphLayout.chart.axisLeft.axisMinimum
    })
    deathsLineDataSet.fillAlpha = 3
    //recovered
    val recovedLineDataSet = LineDataSet(countryHistoryDetails.recovered?.let { datavalues(it) },"Recovered")



    if (Utils.getSDKInt() >= 18) {
        // drawables only supported on api level 18 and above
        casesLineDataSet.fillDrawable = ContextCompat.getDrawable(baseContext, R.drawable.graph_bakground1)
        deathsLineDataSet.fillDrawable = ContextCompat.getDrawable(baseContext, R.drawable.graph_background2)
    } else {
        casesLineDataSet.fillColor = ContextCompat.getColor(baseContext, R.color.colorAccent)
    }


    val dataSets = ArrayList<ILineDataSet>() as MutableList<ILineDataSet>
    dataSets.add(casesLineDataSet)
    dataSets.add(deathsLineDataSet)
    val xData = countryHistoryDetails.cases?.keys
    Log.i("graph",xData.toString())
    val xVals = xData!!.toTypedArray()
    setupView(binding, LineData(xVals,dataSets))


}

private fun datavalues (map : Map<String,Int>) : ArrayList<Entry>{
    var arrayList = ArrayList<Entry>()
    var i = 0
    map.values.forEach {
        it.toFloat().let { Entry(it,i) }.let { arrayList.add(it) }
        i++
    }

    return  arrayList
}


fun setupView (binding: DetailsCountryCardLayoutBinding,lineData: LineData){
    lineData.dataSets.get(0).toString()
   // Log.i("graph",lineData.dataSets.get(0).toString())
    // enable touch gestures
  //  binding.graphLayout.chart.setTouchEnabled(true)

    // set listeners
  //  binding.chart2.setOnChartValueSelectedListener()
    binding.graphLayout.chart.setDrawGridBackground(false)

    // enable scaling and dragging
    //binding.graphLayout.chart.setDragEnabled(true)
    //binding.graphLayout.chart.setScaleEnabled(true)
    // chart.setScaleXEnabled(true);
    // chart.setScaleYEnabled(true);

    // force pinch zoom along both axis
   // binding.graphLayout.chart.setPinchZoom(true)
    //binding.graphLayout.chart.animateX(1500)

    var xAxis: XAxis
    // // X-Axis Style // //
    xAxis = binding.graphLayout.chart.getXAxis()

    // vertical grid lines
    xAxis.enableGridDashedLine(10f, 10f, 0f)


    var yAxis: YAxis
    // // Y-Axis Style // //
    yAxis = binding.graphLayout.chart.getAxisLeft()

    // disable dual axis (only use LEFT axis)
    binding.graphLayout.chart.getAxisRight().setEnabled(false)

    // horizontal grid lines
    yAxis.enableGridDashedLine(10f, 10f, 0f)

    // axis range
    yAxis.setAxisMaxValue(200f)
    yAxis.setAxisMinValue(-50f)
    binding.graphLayout.chart.setDrawGridBackground(true)
   // binding.graphLayout.chart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    binding.graphLayout.chart.clear()
    binding.graphLayout.chart.data = lineData

    binding.graphLayout.chart.invalidate()
    //binding.graphLayout.chart.refreshDrawableState()


}

fun DrawCountryHistoryData ( binding: DetailsCountryCardLayoutBinding,countryHistory: CountryHistory , context: Context){
        val timeLine = countryHistory.timeLine
        if (timeLine != null) {
          setDataIntoGraph(binding, context,timeLine)
        }

}