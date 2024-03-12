package com.qky.autodashboardapplication.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * MainViewModel class
 *
 * @author kaiyuan.qin
 * @date 2024/3/12
 */
class MainViewModel : ViewModel() {
    private var speedData = MutableLiveData(0)
    val speed: LiveData<Int> get() = speedData

    fun changeData() {
        speedData.value = speedData.value!! + 1
    }
}