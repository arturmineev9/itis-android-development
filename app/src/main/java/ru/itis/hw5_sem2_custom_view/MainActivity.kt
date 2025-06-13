package ru.itis.hw5_sem2_custom_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.*
import ru.itis.hw5_sem2_custom_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.circularView.apply {
            sectorCount = 5
            sectorColors = listOf(
                Color.RED,
                Color.BLUE,
                Color.YELLOW,
                Color.GREEN,
                Color.MAGENTA
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}