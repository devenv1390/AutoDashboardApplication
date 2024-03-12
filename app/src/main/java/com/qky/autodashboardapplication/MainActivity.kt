package com.qky.autodashboardapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.qky.autodashboardapplication.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainVm = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val carSpeed = findViewById<TextView>(R.id.tv_car_speed)
        val test = findViewById<Button>(R.id.btn_test)
        carSpeed.text = mainVm.data.value.toString()
        val currentSpeed = Observer<Int> { speed ->
            carSpeed.text = speed.toString()
        }
        test.setOnClickListener {
            setData()
        }
        mainVm.data.observe(this, currentSpeed)
    }

    private fun setData() {
        mainVm.changeData()
    }
}