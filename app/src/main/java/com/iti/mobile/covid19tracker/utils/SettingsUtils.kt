package com.iti.mobile.covid19tracker.utils

import android.content.Context
import android.view.View
import com.iti.mobile.covid19tracker.databinding.SettingsViewBinding
import com.iti.mobile.covid19tracker.features.base.scheduleWork

fun setupNotification (binding: SettingsViewBinding , context: Context) {
    var buttonTime : Long = 0
    binding.switchGroup.isEnabled = false
    binding.toggleButtonGroup.visibility = View.VISIBLE
    binding.toggleButtonGroup.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->

        if(binding.button1.id == checkedId){
          if (isChecked)
             buttonTime = UPDATE_ONE_HOUR
        }
        if (binding.button2.id == checkedId){
            if (isChecked)
                buttonTime = UPDATE_TWO_HOUR
        }
        if(binding.button3.id == checkedId){
            if (isChecked)
                buttonTime = UPDATE_DAY
        }
    }
    binding.updateSetting.setOnClickListener {
       if (buttonTime != 0L)
            scheduleWork(buttonTime,context, SETTINGS_REQUEST)
    }
}
