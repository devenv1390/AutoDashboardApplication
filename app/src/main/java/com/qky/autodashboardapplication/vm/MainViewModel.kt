package com.qky.autodashboardapplication.vm

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import kotlin.math.abs

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

    private var powerData = MutableLiveData(0)
    val power: LiveData<Int> get() = powerData
    private var recycleData = MutableLiveData(0)
    val recycle: LiveData<Int> get() = recycleData

    private var timeData = MutableLiveData("HH:mm")
    val time: LiveData<String> get() = timeData
    private var dateData = MutableLiveData("yyyy年MM月dd日")
    val date: LiveData<String> get() = dateData

    private val remainUnit = remainData.value!! / batteryData.value!!

    private var testFlag = true

    private fun transformPowerRecycleData() {
        if (powerData.value!! <= 0) {
            recycleData.value = abs(powerData.value!!)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    val time = System.currentTimeMillis()
                    val transformTime = SimpleDateFormat("HH:mm")
                    val transformDate = SimpleDateFormat(" yyyy年MM月dd日")
                    dateData.postValue(transformDate.format(time))
                    timeData.postValue(transformTime.format(time))
                    Thread.sleep(5000)
                }
            }
        }
    }

    fun updateRemainData() {
        remainData.value = remainUnit * batteryData.value!!
    }

    fun changeData() {
        speedData.value = speedData.value!! + 1
        if (batteryData.value!! > 0) {
            batteryData.value = batteryData.value!! - 10
        }
        if (testFlag) {
            if (powerData.value!! < 100) {
                powerData.value = powerData.value!! + 10
            }
            if (powerData.value!! >= 100) {
                testFlag = false
                powerData.value = 100
            }
        } else {
            powerData.value = powerData.value!! - 10
            if (powerData.value!! <= -100) {
                testFlag = true
                powerData.value = -100
            }
        }
        updateRemainData()
        transformPowerRecycleData()
    }
}