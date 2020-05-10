package com.iti.mobile.covid19tracker.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.iti.mobile.covid19tracker.databinding.SettingsViewBinding
import com.iti.mobile.covid19tracker.features.base.scheduleWork
import timber.log.Timber
import com.iti.mobile.covid19tracker.extension.toast

fun setupNotification (binding: SettingsViewBinding , context: Context, dialog: Dialog) {
    var buttonTime : Long = 0
    binding.switchGroup.isEnabled = false
    binding.toggleButtonGroup.visibility = View.VISIBLE
    binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->

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
        dialog.dismiss()
        context.toast("You will be notified after ${buttonTime}-Hour")
    }
}
