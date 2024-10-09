package com.example.juegoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.juegoandroid.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val correctAnswers = intent.getIntExtra("correctAnswers", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 11)

        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "Has acertado $correctAnswers de $totalQuestions preguntas"

        val restartButton = findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            // Reiniciar el juego
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
