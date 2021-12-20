package com.abhishek.radiustask

import android.app.Dialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.abhishek.radiustask.adapter.FacilityAdapter
import com.abhishek.radiustask.databinding.ActivityMainBinding
import com.abhishek.radiustask.databinding.ErrorDialogBoxLayoutBinding
import com.abhishek.radiustask.util.DatabaseUpdateJobService


class MainActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]


        mainViewModel.errorType.observe(this) { errorType: Int -> showError(errorType) }

        binding.facilitiesRecyclerView.adapter =
            FacilityAdapter(this, listener = mainViewModel.listener)

        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setJobScheduler()
    }

    private fun setJobScheduler() {
        val jobScheduler = applicationContext
            .getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val componentName = ComponentName(this,
            DatabaseUpdateJobService::class.java)

        val jobInfo = JobInfo.Builder(1, componentName)
            .setPeriodic(86400000).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true).build()

        jobScheduler.schedule(jobInfo)
    }


    private fun showError(errorType: Int) {
        if (errorType != 0) {
            showDialog(errorType == 2)
            if (Build.VERSION.SDK_INT < 26) {
                (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(100)
            } else {
                (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(
                    100,
                    10))
            }
            mainViewModel.onErrorShown()
        }
    }

    private fun showDialog(isNetworkIssue: Boolean) {
        val dialog = Dialog(this)
        val bindingRoot = ErrorDialogBoxLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingRoot.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        if (isNetworkIssue) {
            bindingRoot.messageTextView.text = "   Please check Your   \n Internet Connection   "
            bindingRoot.goBack.text = "RETRY"
            bindingRoot.goBack.setOnClickListener {
                dialog.hide()
                if (!checkNetwork()) {
                    showDialog(true)
                } else {
                    mainViewModel.getAllData()
                }
            }
        } else {
            bindingRoot.goBack.setOnClickListener { dialog.hide() }
        }
        dialog.show()
    }

    private fun checkNetwork(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

}