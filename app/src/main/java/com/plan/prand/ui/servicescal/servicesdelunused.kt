package com.plan.prand.ui.servicescal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class servicesdelunused : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is servicescal Fragment"
    }
    val text: LiveData<String> = _text
}