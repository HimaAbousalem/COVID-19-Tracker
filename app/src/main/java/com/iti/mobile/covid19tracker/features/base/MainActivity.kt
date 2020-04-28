package com.iti.mobile.covid19tracker.features.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iti.mobile.covid19tracker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
