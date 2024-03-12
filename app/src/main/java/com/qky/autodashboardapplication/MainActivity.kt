package com.qky.autodashboardapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.qky.autodashboardapplication.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var tvCarSpeed: TextView
    private lateinit var pbBattery: ProgressBar
    private lateinit var tvRemainNum:TextView

    private val vmMain = MainViewModel()

    private lateinit var btnTest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCarSpeed = findViewById(R.id.tv_car_speed)
        pbBattery = findViewById(R.id.pb_battery)
        btnTest = findViewById(R.id.btn_test)
        tvRemainNum = findViewById(R.id.tv_remain_num)

        initView()
        initClickEvent()
    }

    private fun initClickEvent() {
        btnTest.setOnClickListener {
            setData()
        }
    }

    private fun initView() {
        vmMain.updateRemainData()
        val currentRemain = Observer<Int> { remain->
            tvRemainNum.text = remain.toString()
        }
        vmMain.remain.observe(this,currentRemain)

        val currentBattery = Observer<Int> { battery ->
            pbBattery.progress = battery
        }
        vmMain.battery.observe(this, currentBattery)

        val currentSpeed = Observer<Int> { speed ->
            tvCarSpeed.text = speed.toString()
        }
        vmMain.speed.observe(this, currentSpeed)
    }

    private fun setData() {
        vmMain.changeData()
    }


}