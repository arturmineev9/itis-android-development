package ru.itis.hw5_sem2_custom_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.*
import androidx.core.content.ContextCompat
import ru.itis.hw5_sem2_custom_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val context = binding.pieChartView.context
        binding.pieChartView.apply {
            sectorCount = 6
            sectorColors = listOf(
                ContextCompat.getColor(context, R.color.chart_red),
                ContextCompat.getColor(context, R.color.chart_blue),
                ContextCompat.getColor(context, R.color.chart_orange),
                ContextCompat.getColor(context, R.color.chart_cyan),
                ContextCompat.getColor(context, R.color.chart_grey),
                ContextCompat.getColor(context, R.color.chart_yellow)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
