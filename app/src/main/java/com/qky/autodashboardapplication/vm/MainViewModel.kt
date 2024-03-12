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

    private var batteryData = MutableLiveData(100)
    val battery: LiveData<Int> get() = batteryData

    private var remainData = MutableLiveData(600)
    val remain: LiveData<Int> get() = remainData

    private val remainUnit = remainData.value!!/batteryData.value!!

    fun updateRemainData(){
        remainData.value = remainUnit * batteryData.value!!
    }

    fun changeData() {
        speedData.value = speedData.value!! + 1
        if (batteryData.value!! >0){
            batteryData.value = batteryData.value!! - 10
        }
        updateRemainData()
    }
}