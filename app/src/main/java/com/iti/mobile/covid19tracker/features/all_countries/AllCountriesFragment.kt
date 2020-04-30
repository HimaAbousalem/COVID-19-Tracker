package com.iti.mobile.covid19tracker.features.all_countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.FragmentAllCountriesBinding
import kotlinx.android.synthetic.main.activity_main.*

class AllCountriesFragment : Fragment() {
    lateinit var binding : FragmentAllCountriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllCountriesBinding.inflate(layoutInflater)

        return binding.root
    }


}
