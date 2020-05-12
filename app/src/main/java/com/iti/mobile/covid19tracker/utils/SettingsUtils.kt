package com.iti.mobile.covid19tracker.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.get
import com.google.android.material.button.MaterialButton
import com.iti.mobile.covid19tracker.databinding.SettingsViewBinding
import com.iti.mobile.covid19tracker.features.base.scheduleWork
import timber.log.Timber
import com.iti.mobile.covid19tracker.extension.toast

fun setupNotification (binding: SettingsViewBinding , context: Context, dialog: Dialog,lastTime:Long, clickable: Clickable) {
    var buttonTime : Long = 0
    binding.toggleButtonGroup.visibility = View.VISIBLE
    binding.toggleButtonGroup.forEach { button ->
        button.setOnClickListener { (button as MaterialButton).isChecked = true }
    }
    binding.updateSetting.isEnabled = false
    if (lastTime == UPDATE_ONE_HOUR){
        binding.toggleButtonGroup.check(binding.button1.id)
    }else if (lastTime == UPDATE_TWO_HOUR){
        binding.toggleButtonGroup.check(binding.button2.id)
    }else if (lastTime == UPDATE_DAY){
        binding.toggleButtonGroup.check(binding.button3.id)
    }else if (lastTime == 0L){
        binding.toggleButtonGroup.visibility = View.GONE
    }
    binding.toggleButtonGroup.addOnButtonCheckedListener { viewGroup, checkedId, isChecked ->
        if(binding.button1.id == checkedId){
          if (isChecked){
              buttonTime = UPDATE_ONE_HOUR
          }
        }
        if (binding.button2.id == checkedId){
            if (isChecked){
                buttonTime = UPDATE_TWO_HOUR
           }
        }
        if(binding.button3.id == checkedId){
            if (isChecked){
                buttonTime = UPDATE_DAY
            }
        }
        binding.updateSetting.isEnabled = buttonTime != lastTime
    }
    binding.switchGroup.setOnCheckedChangeListener { buttonView, isChecked ->

        binding.updateSetting.isEnabled = lastTime != 0L

        if (isChecked)
        {
            binding.toggleButtonGroup.visibility = View.VISIBLE
            binding.toggleButtonGroup.check(binding.button1.id)
        }else {
            binding.toggleButtonGroup.visibility = View.GONE
            buttonTime = 0
        }
    }
    binding.updateSetting.setOnClickListener {

        if (buttonTime != 0L) {
            scheduleWork(buttonTime, context, SETTINGS_REQUEST)
            clickable.updateNotificationTime(buttonTime,true)
            context.toast("You will be notified after ${buttonTime}-Hour")
        } else {
            scheduleWork(DEFAULT_UPDATE_TIME, context, SETTINGS_REQUEST)
            clickable.updateNotificationTime(0,false)
            context.toast("You won't be notified anymore!")
        }
        dialog.dismiss()

    }
}
