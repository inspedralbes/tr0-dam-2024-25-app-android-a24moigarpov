package com.example.juegoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.juegoandroid.R
import com.squareup.picasso.Picasso

class QuestionActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val questionText = findViewById<TextView>(R.id.questionText)
        val questionImage = findViewById<ImageView>(R.id.questionImage)
        val answer1 = findViewById<Button>(R.id.answer1)
        val answer2 = findViewById<Button>(R.id.answer2)
        val answer3 = findViewById<Button>(R.id.answer3)
        val answer4 = findViewById<Button>(R.id.answer4)

        // Lista de preguntas y respuestas
        val preguntas = listOf(
            Question("¿Qué coche es Bumblebee?", "https://images.hdqwalls.com/wallpapers/bumblebee-4k-2020-t5.jpg",
                listOf("Ferrari", "Audi", "BMW", "Chevrolet Camaro"), 3)
            // Puedes añadir más preguntas aquí
        )

        // Cargar la primera pregunta
        loadQuestion(preguntas, questionText, questionImage, answer1, answer2, answer3, answer4)
    }

    private fun loadQuestion(preguntas: List<Question>, questionText: TextView, questionImage: ImageView,
                             answer1: Button, answer2: Button, answer3: Button, answer4: Button) {
        if (currentQuestionIndex < preguntas.size) {
            val preguntaActual = preguntas[currentQuestionIndex]

            questionText.text = preguntaActual.pregunta
            Picasso.get().load(preguntaActual.imagen).into(questionImage)
            answer1.text = preguntaActual.respuestas[0]
            answer2.text = preguntaActual.respuestas[1]
            answer3.text = preguntaActual.respuestas[2]
            answer4.text = preguntaActual.respuestas[3]

            answer1.setOnClickListener { checkAnswer(0, preguntas) }
            answer2.setOnClickListener { checkAnswer(1, preguntas) }
            answer3.setOnClickListener { checkAnswer(2, preguntas) }
            answer4.setOnClickListener { checkAnswer(3, preguntas) }
        } else {
            // Navegar a la pantalla de resultados
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalQuestions", preguntas.size)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAnswer(selectedAnswerIndex: Int, preguntas: List<Question>) {
        val preguntaActual = preguntas[currentQuestionIndex]
        if (selectedAnswerIndex == preguntaActual.correctAnswerIndex) {
            correctAnswers++
        }
        currentQuestionIndex++
        loadQuestion(preguntas, findViewById(R.id.questionText), findViewById(R.id.questionImage),
            findViewById(R.id.answer1), findViewById(R.id.answer2),
            findViewById(R.id.answer3), findViewById(R.id.answer4))
    }
}

// Clase de datos para manejar las preguntas
data class Question(
    val pregunta: String,
    val imagen: String,
    val respuestas: List<String>,
    val correctAnswerIndex: Int // Índice de la respuesta correcta
)
