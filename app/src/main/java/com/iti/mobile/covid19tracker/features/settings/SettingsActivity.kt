package com.iti.mobile.covid19tracker.features.settings


import android.view.View
import androidx.core.view.isVisible
import com.iti.mobile.covid19tracker.databinding.ActivitySettingsBinding

   fun setupNotification (binding: ActivitySettingsBinding) {
       binding.notificationOption.setOnClickListener {
           if (binding.showNotificationSetting.isVisible) {
               binding.showNotificationSetting.visibility = View.GONE
           } else {
               binding.showNotificationSetting.visibility = View.VISIBLE
               binding.switchGroup.setOnCheckedChangeListener { _, isChecked ->
                   if (isChecked) {
                       binding.toggleButtonGroup.visibility = View.VISIBLE
                   } else {
                       binding.toggleButtonGroup.visibility = View.GONE
                   }
               }
           }
       }
       binding.toggleButtonGroup.isSingleSelection = true
       binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, _ ->
            if(checkedId == 0){
               print("0")
            }else if (checkedId == 1){
                print("1")
            }else{
                print("2")
            }
       }
   }
    fun setupUpdataButton () {

    }



