package com.example.trafficquizapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trafficquizapp.R

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val correctCount = intent.getIntExtra("correctCount", 0)
        val totalCount = intent.getIntExtra("totalCount", 1)

        val resultText: TextView = findViewById(R.id.resultText)
        resultText.text = "Correct Answers: $correctCount / $totalCount"
    }
}
