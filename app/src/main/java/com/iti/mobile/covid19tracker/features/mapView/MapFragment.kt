package com.iti.mobile.covid19tracker.features.mapView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iti.mobile.covid19tracker.R
import com.iti.mobile.covid19tracker.databinding.FragmentMapBinding

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {
lateinit var binding: FragmentMapBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

}
