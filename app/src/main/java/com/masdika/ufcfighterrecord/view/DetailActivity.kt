package com.masdika.ufcfighterrecord.view

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.masdika.ufcfighterrecord.Fighter
import com.masdika.ufcfighterrecord.OrientationViewModel
import com.masdika.ufcfighterrecord.R
import com.masdika.ufcfighterrecord.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val orientationViewModel: OrientationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        windowPadding()

        orientationViewModel.orientation.observe(this, { orientation ->
            Log.d("OrientationObserve", "Current orientation: $orientation")
        })

        val data: Fighter? = savedInstanceState?.getParcelable("fighter_data")
            ?: intent.getParcelableExtra("DATA")

        data?.let {
            bindData(it)
        }

    }

    private fun bindData(data: Fighter) {
        Glide.with(this).load(data.image).into(binding.fighterImage)
        binding.fighterNameTextview.text = data.name
        binding.fighterTitleTextview.text = data.fighterTitle
        binding.fighterDivisionTextview.text = data.division
        binding.fighterRecordTextview.text = "${data.fighterWin} - ${data.fighterDraw} - ${data.fighterLoss} | (W-D-L)"
        binding.winByKnockoutTextview.text = "${data.winByKnockOut}"
        binding.winBySubmissionTextview.text = "${data.winBySubmission}"
        binding.fighterAboutTextview.text = data.about

        val takeDownAccuracyChart: PieChart = binding.takedownAccuracyChart
        val strikingAccuracyChart: PieChart = binding.strikingAccuracyChart

        setChart(strikingAccuracyChart, data.strikePercentage)
        setChart(takeDownAccuracyChart, data.takeDownPercentage)
    }

    private fun setChart(chart: PieChart, percentage: Int) {
        val entries = listOf(
            PieEntry(percentage.toFloat()),
            PieEntry(100 - percentage.toFloat())
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(
            ContextCompat.getColor(this, R.color.mainUFCColor),
            colorOnSecondaryFixedVariant
        )

        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        val dataChart = PieData(dataSet)
        dataChart.setDrawValues(false)

        chart.data = dataChart
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.transparentCircleRadius = 45f
        chart.transparentCircleRadius = 45f
        chart.setEntryLabelColor(Color.TRANSPARENT)
        chart.centerText = "${percentage}%"

        chart.invalidate()
    }

    private val colorOnSecondaryFixedVariant by lazy {
        val typedValue = TypedValue()
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnSecondaryFixedVariant, typedValue, true)
        typedValue.data
    }

    private fun windowPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        orientationViewModel.updateOrientation(newConfig.orientation)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        windowPadding()

        val data: Fighter? = intent.getParcelableExtra("DATA")
        data?.let {
            bindData(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val data = intent.getParcelableExtra<Fighter>("DATA")
        outState.putParcelable("fighter_data", data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val data: Fighter? = savedInstanceState.getParcelable("fighter_data")
        data?.let {
            bindData(data)
        }
    }

}