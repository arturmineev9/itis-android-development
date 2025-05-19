package ru.itis.clientserverapp.graph_screen.compose

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.github.mikephil.charting.data.Entry
import ru.itis.clientserverapp.graph_screen.databinding.ChartViewBinding
import java.util.Collections


@Composable
fun LineChartView(values: List<Float>) {
    AndroidView(
        factory = { ctx ->
            val binding = ChartViewBinding.inflate(LayoutInflater.from(ctx))
            val chart = binding.lineChart

            val entries = values.mapIndexed { index, y -> Entry(index.toFloat(), y) }
            Collections.sort(entries, EntryXComparator())

            val dataSet = LineDataSet(entries, "График").apply {
                color = Color.BLUE
                setDrawFilled(true)
                setDrawCircles(true)
                circleRadius = 5f
                setCircleColor(Color.BLUE)
                lineWidth = 2f
                valueTextSize = 12f

                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(
                        Color.BLUE,
                        Color.TRANSPARENT
                    )
                )
                fillDrawable = gradientDrawable
                fillAlpha = 255
            }

            chart.data = LineData(dataSet)
            chart.description = Description().apply { text = "" }
            chart.axisRight.isEnabled = false
            chart.axisLeft.isEnabled = true
            chart.xAxis.isEnabled = true
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.legend.isEnabled = false

            chart.invalidate()

            binding.root
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}
