package com.masdika.ufcfighterrecord

import android.content.res.Configuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrientationViewModel : ViewModel() {

    private val _orientation = MutableLiveData<String>()
    val orientation: LiveData<String> get() = _orientation

    fun updateOrientation(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> _orientation.value = "LANDSCAPE"
            Configuration.ORIENTATION_PORTRAIT -> _orientation.value = "PORTRAIT"
        }
    }
}
