package com.qky.autodashboardapplication

import android.animation.ValueAnimator
import android.content.pm.ActivityInfo
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
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView

    private lateinit var pbBattery: ProgressBar
    private lateinit var pbPower: ProgressBar
    private lateinit var pbRecycle: ProgressBar
    private lateinit var pbPowerCopy: ProgressBar
    private lateinit var pbRecycleCopy: ProgressBar

    private val vmMain = MainViewModel()

    private lateinit var btnTest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        setContentView(R.layout.activity_main)

        getView()
        initView()
        initClickEvent()
    }

    private fun getView() {
        tvCarSpeed = findViewById(R.id.tv_car_speed)
        tvRemainNum = findViewById(R.id.tv_remain_num)
        tvDate = findViewById(R.id.tv_date)
        tvTime = findViewById(R.id.tv_time)

        pbBattery = findViewById(R.id.pb_battery)
        pbPower = findViewById(R.id.pb_power)
        pbRecycle = findViewById(R.id.pb_recycle)
        pbPowerCopy = findViewById(R.id.pb_power_copy)
        pbRecycleCopy = findViewById(R.id.pb_recycle_copy)

        btnTest = findViewById(R.id.btn_test)
    }

    private fun initClickEvent() {
        btnTest.setOnClickListener {
            setData()
        }
    }

    private fun initView() {
        vmMain.getTime()
        vmMain.updateRemainData()

        initDateTime()
        initRemain()
        initBattery()
        initSpeed()
        initPowerRecycle()
    }

    private fun initPowerRecycle() {
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

    private fun initSpeed() {
        val currentSpeed = Observer<Int> { speed ->
            tvCarSpeed.text = speed.toString()
        }
        vmMain.speed.observe(this, currentSpeed)
    }

    private fun initBattery() {
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
    }

    private fun initRemain() {
        val currentRemain = Observer<Int> { remain ->
            tvRemainNum.text = remain.toString()
        }
        vmMain.remain.observe(this, currentRemain)
    }

    private fun initDateTime() {
        val currentDate = Observer<String> { date ->
            tvDate.text = date.toString()
        }
        vmMain.date.observe(this, currentDate)
        val currentTime = Observer<String> { time ->
            tvTime.text = time.toString()
        }
        vmMain.time.observe(this, currentTime)
    }

    private fun setData() {
        vmMain.changeData()
    }


}
