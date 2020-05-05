package com.iti.mobile.covid19tracker.features

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.FillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.ActivityGraphTestBinding


lateinit var binding: ActivityGraphTestBinding

class graph_test : OnChartValueSelectedListener, AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
        val lineDataSet = LineDataSet(datavalues()," data set 1")
        val lineDataSet2 = LineDataSet(datavalues2()," data set 2")
        lineDataSet.setCircleColor(ContextCompat.getColor(baseContext, R.color.white))
        lineDataSet.setColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))

        lineDataSet2.setDrawFilled(true)
        lineDataSet2.setFillFormatter(FillFormatter { dataSet, dataProvider ->
            return@FillFormatter binding.chart2.axisLeft.axisMinimum
        })
        lineDataSet.setDrawFilled(true)
        lineDataSet.setFillFormatter(FillFormatter { dataSet, dataProvider ->
            return@FillFormatter binding.chart2.axisLeft.axisMinimum
        })
        lineDataSet.fillAlpha = 1
        lineDataSet2.fillAlpha = 3
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            lineDataSet.fillDrawable = ContextCompat.getDrawable(baseContext,R.drawable.graph_bakground1)
            lineDataSet2.fillDrawable = ContextCompat.getDrawable(baseContext,R.drawable.graph_background2)
        } else {
            lineDataSet.fillColor = ContextCompat.getColor(baseContext, R.color.colorAccent)
        }


        val dataSets = ArrayList<ILineDataSet>() as MutableList<ILineDataSet>
        dataSets.add(lineDataSet)
        dataSets.add(lineDataSet2)
        val xVals = listOf<String>("1","2","3","4")
        val lineData = LineData(xVals,dataSets)
        binding.chart2.setDrawGridBackground(true)
        //binding.chart2.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
        binding.chart2.data = lineData
        binding.chart2.invalidate()


    }
    fun datavalues () : ArrayList<Entry>{
        var arrayList = ArrayList<Entry>()
        arrayList.add(Entry(20F,0))
        arrayList.add(Entry(30F,1))
        arrayList.add(Entry(40F,2))
        arrayList.add(Entry(50F,3))
        return  arrayList
    }
    fun datavalues2 () : ArrayList<Entry>{
        var arrayList = ArrayList<Entry>()
        arrayList.add(Entry(10F,0))
        arrayList.add(Entry(15F,1))
        arrayList.add(Entry(25F,2))
        arrayList.add(Entry(35F,3))
        return  arrayList
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }

    override fun onValueSelected(e: Entry?, dataSetIndex: Int, h: Highlight?) {
        TODO("Not yet implemented")
    }
    fun setup (){

            // Chart Style
            // disable description text
           // binding.chart2.getDescription().setEnabled(false)

            // enable touch gestures
            binding.chart2.setTouchEnabled(true)

            // set listeners
            binding.chart2.setOnChartValueSelectedListener(this)
            binding.chart2.setDrawGridBackground(false)

            // enable scaling and dragging
            binding.chart2.setDragEnabled(true)
            binding.chart2.setScaleEnabled(true)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            binding.chart2.setPinchZoom(true)
            binding.chart2.animateX(1500)


        var xAxis: XAxis
        // // X-Axis Style // //
            xAxis = binding.chart2.getXAxis()

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)


        var yAxis: YAxis
           // // Y-Axis Style // //
            yAxis = binding.chart2.getAxisLeft()

            // disable dual axis (only use LEFT axis)
            binding.chart2.getAxisRight().setEnabled(false)

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.setAxisMaxValue(200f)
            yAxis.setAxisMinValue(-50f)

    }
}
