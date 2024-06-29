package com.nazar.customview

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nazar.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val cancelToken = Any()
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomButtons.setClickListener { buttonType ->
            when (buttonType) {

                ButtonType.NEGATIVE -> {
                    Toast.makeText(this, "Negative Button Pressed", Toast.LENGTH_SHORT).show()
                }

                ButtonType.POSITIVE -> {
                    binding.bottomButtons.inProgress = true
                    handler.postDelayed({
                        binding.bottomButtons.inProgress = false
                        binding.bottomButtons.setPositiveButtonText("Was Clicked")
                        Toast.makeText(this, "Action completed", Toast.LENGTH_SHORT).show()
                    }, cancelToken, 2000L)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        handler.removeCallbacksAndMessages(cancelToken)
    }
}