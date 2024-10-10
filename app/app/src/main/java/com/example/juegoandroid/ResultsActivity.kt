package com.example.juegoandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var resultText: TextView
    private lateinit var timeText: TextView // Nueva variable para el TextView del tiempo
    private lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        resultText = findViewById(R.id.resultText)
        timeText = findViewById(R.id.timeText) // Referenciar el nuevo TextView
        restartButton = findViewById(R.id.restartButton)

        // Obtener datos del Intent
        val correctAnswers = intent.getIntExtra("EXTRA_SCORE", 0) // Cambiado a EXTRA_SCORE
        val totalQuestions = intent.getIntExtra("totalQuestions", 0) // Usar la clave correcta
        val timeElapsed = intent.getLongExtra("EXTRA_TIME", 0) // Obtener el tiempo como Long

        // Establecer texto en los TextViews
        resultText.text = "Has acertado $correctAnswers de $totalQuestions preguntas"
        timeText.text = "Tiempo: $timeElapsed s" // Mostrar el tiempo

        // Configurar el botón para reiniciar el juego
        restartButton.setOnClickListener {
            finish() // Cierra la actividad actual para volver a la anterior
        }
    }
}
