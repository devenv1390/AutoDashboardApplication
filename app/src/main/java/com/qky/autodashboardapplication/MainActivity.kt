package com.qky.autodashboardapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.qky.autodashboardapplication.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private val tvCarSpeed: TextView = findViewById(R.id.tv_car_speed)
    private val btnTest: Button = findViewById(R.id.btn_test)
    private val vmMain = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initClickEvent()
    }

    private fun initClickEvent() {
        btnTest.setOnClickListener {
            setData()
        }
    }

    private fun initView() {
        val currentSpeed = Observer<Int> { speed ->
            tvCarSpeed.text = speed.toString()
        }
        vmMain.speed.observe(this, currentSpeed)
    }

    private fun setData() {
        vmMain.changeData()
    }


}