package com.example.hskmaster.components

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*

@Composable
fun ChartView(scores: List<Int>) {

    AndroidView(factory = { context ->

        val chart = LineChart(context)

        val entries = scores.mapIndexed { index, score ->
            Entry(index.toFloat(), score.toFloat())
        }

        val dataSet = LineDataSet(entries, "Score")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        chart.data = LineData(dataSet)
        chart.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            600
        )

        chart
    })
}