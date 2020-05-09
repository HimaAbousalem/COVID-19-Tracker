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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.FragmentMapBinding
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.entities.CountryHistoryDetails


fun setDataIntoGraph ( binding: FragmentMapBinding, baseContext : Context, countryHistoryDetails: CountryHistoryDetails ){
    //all cases
    val casesLineDataSet = LineDataSet(countryHistoryDetails.cases?.let { datavalues(it) },"Cases")
    casesLineDataSet.setCircleColor(ContextCompat.getColor(baseContext, R.color.white))
    casesLineDataSet.setColor(ContextCompat.getColor(baseContext, R.color.cases))
    casesLineDataSet.setDrawFilled(true)
    casesLineDataSet.setFillFormatter(FillFormatter { dataSet, dataProvider ->
        return@FillFormatter binding.chart.axisLeft.axisMinimum
    })
    casesLineDataSet.fillAlpha = 3
    //recovered
    val recovedLineDataSet = LineDataSet(countryHistoryDetails.recovered?.let { datavalues(it) },"Recovered")
    recovedLineDataSet.setDrawFilled(true)
    recovedLineDataSet.setCircleColor(ContextCompat.getColor(baseContext, R.color.white))
    recovedLineDataSet.setColor(ContextCompat.getColor(baseContext, R.color.recovered))
    recovedLineDataSet.setFillFormatter(FillFormatter { dataSet, dataProvider ->
        return@FillFormatter binding.chart.axisLeft.axisMinimum
    })
    recovedLineDataSet.fillAlpha = 3

    //deaths
    val deathsLineDataSet = LineDataSet(countryHistoryDetails.deaths?.let { datavalues(it) },"Deaths")
    deathsLineDataSet.setDrawFilled(true)
    deathsLineDataSet.setCircleColor(ContextCompat.getColor(baseContext, R.color.white))
    deathsLineDataSet.setColor(ContextCompat.getColor(baseContext, R.color.deaths))
    deathsLineDataSet.setFillFormatter(FillFormatter { dataSet, dataProvider ->
        return@FillFormatter binding.chart.axisLeft.axisMinimum
    })
    deathsLineDataSet.fillAlpha = 3

    if (Utils.getSDKInt() >= 18) {
        // drawables only supported on api level 18 and above
        casesLineDataSet.fillDrawable = ContextCompat.getDrawable(baseContext,  R.drawable.graph_background3)
        recovedLineDataSet.fillDrawable = ContextCompat.getDrawable(baseContext, R.drawable.graph_bakground1)
        deathsLineDataSet.fillDrawable = ContextCompat.getDrawable(baseContext,R.drawable.graph_background2)
    } else {
        casesLineDataSet.fillColor = ContextCompat.getColor(baseContext, R.color.cases)
        deathsLineDataSet.fillColor = ContextCompat.getColor(baseContext, R.color.deaths)
        recovedLineDataSet.fillColor = ContextCompat.getColor(baseContext, R.color.recovered)
    }


    val dataSets = ArrayList<ILineDataSet>() as MutableList<ILineDataSet>
    dataSets.add(casesLineDataSet)
    dataSets.add(recovedLineDataSet)
    dataSets.add(deathsLineDataSet)

    val xData = countryHistoryDetails.cases?.keys
   // Log.i("graph",xData.toString())
    val xVals = xData!!.toTypedArray()
    setupView(binding, LineData(xVals,dataSets) , baseContext)

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


fun setupView (binding: FragmentMapBinding,lineData: LineData ,baseContext: Context){
    //chart description
    binding.chart.setDescription("World Wide")
    binding.chart.setDescriptionColor(R.color.colorPrimaryDark)
   // binding.chart.setDescriptionPosition(binding.chart.xChartMin + 10,binding.chart.yChartMax -50)
    binding.chart.setDescriptionTextSize(20f)
    // enable touch gestures
     binding.chart.setTouchEnabled(true)
    binding.chart.zoomIn()
    // set listeners
   // binding.chart.setOnChartValueSelectedListener()
    // enable scaling and dragging
    binding.chart.setDragEnabled(true)
    binding.chart.setScaleEnabled(true)
    binding.chart.setScaleXEnabled(true);
    binding.chart.setScaleYEnabled(true);

    // force pinch zoom along both axis
     //binding.chart.setPinchZoom(true)
   // binding.chart.animateX(2000)
   binding.chart.animateXY(2000,2000)

    var xAxis: XAxis
    // // X-Axis Style // //
    xAxis = binding.chart.getXAxis()
    // vertical grid lines
    xAxis.enableGridDashedLine(10f, 20f, 2f)
    xAxis.textColor = ContextCompat.getColor(baseContext, R.color.colorPrimary)
    xAxis.axisLineColor = ContextCompat.getColor(baseContext, R.color.colorPrimary)

    var yAxis: YAxis
    // // Y-Axis Style // //
    yAxis = binding.chart.getAxisLeft()
    yAxis.textColor = ContextCompat.getColor(baseContext, R.color.colorPrimary)
    yAxis.axisLineColor = ContextCompat.getColor(baseContext, R.color.colorPrimary)
    // disable dual axis (only use LEFT axis)
    binding.chart.getAxisRight().setEnabled(false)

    // horizontal grid lines
    yAxis.enableGridDashedLine(10f, 20f, 2f)

    // axis range
//    yAxis.setAxisMaxValue(200f)
   // yAxis.setAxisMinValue(0f)
    binding.chart.setDrawGridBackground(true)
    // binding.chart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    binding.chart.clear()
    binding.chart.data = lineData
    binding.chart.invalidate()


}

fun DrawCountryHistoryData ( binding: FragmentMapBinding,countryHistory: CountryHistoryDetails , context: Context){
    setDataIntoGraph(binding, context,countryHistory)

}