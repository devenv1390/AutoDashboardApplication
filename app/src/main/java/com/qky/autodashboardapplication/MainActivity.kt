package com.qky.autodashboardapplication

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.qky.autodashboardapplication.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var tvCarSpeed: TextView
    private lateinit var tvRemainNum: TextView

    private lateinit var pbBattery: ProgressBar
    private lateinit var pbPower: ProgressBar
    private lateinit var pbRecycle: ProgressBar
    private lateinit var pbPowerCopy: ProgressBar
    private lateinit var pbRecycleCopy: ProgressBar

    private val vmMain = MainViewModel()

    private lateinit var btnTest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCarSpeed = findViewById(R.id.tv_car_speed)
        tvRemainNum = findViewById(R.id.tv_remain_num)

        pbBattery = findViewById(R.id.pb_battery)
        pbPower = findViewById(R.id.pb_power)
        pbRecycle = findViewById(R.id.pb_recycle)
        pbPowerCopy = findViewById(R.id.pb_power_copy)
        pbRecycleCopy = findViewById(R.id.pb_recycle_copy)

        btnTest = findViewById(R.id.btn_test)

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
//        tvSpaceProgress.bringToFront()
        val currentRemain = Observer<Int> { remain ->
            tvRemainNum.text = remain.toString()
        }
        vmMain.remain.observe(this, currentRemain)

        val currentBattery = Observer<Int> { battery ->
            val prevBattery = pbBattery.progress
            val animator = ValueAnimator.ofInt(prevBattery, battery)
            animator.duration = 200
            animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Int
                pbBattery.progress = progress
            }
            animator.start()
        }
        vmMain.battery.observe(this, currentBattery)

        val currentSpeed = Observer<Int> { speed ->
            tvCarSpeed.text = speed.toString()
        }
        vmMain.speed.observe(this, currentSpeed)

        val currentPower = Observer<Int> { power ->
            val prevPower = pbPower.progress
            val animator = ValueAnimator.ofInt(prevPower, power)
            animator.duration = 200
            animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Int
                pbPower.progress = progress
                pbPowerCopy.progress = progress
            }
            animator.start()
        }
        vmMain.power.observe(this, currentPower)
        val currentRecycle = Observer<Int> { recycle ->
            val prevRecycle = pbRecycle.progress
            val animator = ValueAnimator.ofInt(prevRecycle, recycle)
            animator.duration = 200
            animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Int
                pbRecycle.progress = progress
                pbRecycleCopy.progress = progress
            }
            animator.start()
        }
        vmMain.recycle.observe(this, currentRecycle)
    }

    private fun setData() {
        vmMain.changeData()
    }


}
