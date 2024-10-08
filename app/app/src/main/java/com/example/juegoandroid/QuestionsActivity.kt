package com.example.juegoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trafficquizapp.R
import com.example.trafficquizapp.data.ApiService
import com.example.trafficquizapp.data.Pregunta
import com.example.trafficquizapp.data.PreguntesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionsActivity : AppCompatActivity() {
    private lateinit var questionText: TextView
    private lateinit var imageView: ImageView
    private lateinit var optionButtons: List<Button>
    private var preguntes: List<Pregunta> = emptyList()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        questionText = findViewById(R.id.questionText)
        imageView = findViewById(R.id.questionImage)
        optionButtons = listOf(
            findViewById(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )

        fetchPreguntes()
    }

    private fun fetchPreguntes() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://YOUR_SERVER_IP:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        service.getPreguntes().enqueue(object : Callback<PreguntesResponse> {
            override fun onResponse(call: Call<PreguntesResponse>, response: retrofit2.Response<PreguntesResponse>) {
                if (response.isSuccessful) {
                    preguntes = response.body()?.preguntes ?: emptyList()
                    showQuestion()
                }
            }

            override fun onFailure(call: Call<PreguntesResponse>, t: Throwable) {
                Toast.makeText(this@QuestionsActivity, "Error fetching questions", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showQuestion() {
        val question = preguntes[currentQuestionIndex]
        questionText.text = question.pregunta
        imageView.setImageURI(android.net.Uri.parse(question.imatge))

        optionButtons.forEachIndexed { index, button ->
            button.text = question.respostes[index]
            button.setOnClickListener {
                checkAnswer(index)
            }
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (selectedIndex == preguntes[currentQuestionIndex].resposta_correcta) {
            correctAnswersCount++
        }
        currentQuestionIndex++
        if (currentQuestionIndex < preguntes.size) {
            showQuestion()
        } else {
            showResults()
        }
    }

    private fun showResults() {
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra("correctCount", correctAnswersCount)
            putExtra("totalCount", preguntes.size)
        }
        startActivity(intent)
        finish()
    }
}