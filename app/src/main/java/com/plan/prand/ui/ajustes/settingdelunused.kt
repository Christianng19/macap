package com.plan.prand.ui.ajustes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class settingdelunused : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ajustes Fragment"
    }
    val text: LiveData<String> = _text
}