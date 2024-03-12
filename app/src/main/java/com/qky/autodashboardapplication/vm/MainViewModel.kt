package com.qky.autodashboardapplication.vm

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.delay

/**
 * MainViewModel class
 *
 * @author kaiyuan.qin
 * @date 2024/3/12
 */
class MainViewModel : ViewModel() {
    private var testData = MutableLiveData<Int>(0)
    val data: LiveData<Int> get() = testData

    fun changeData() {
        testData.value = testData.value!! + 1
    }
}