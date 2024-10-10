package com.example.juegoandroid

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.juegoandroid.network.Pregunta
import com.example.juegoandroid.network.RetrofitClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private lateinit var preguntas: List<Pregunta>
    private lateinit var timerText: TextView
    private var timer: CountDownTimer? = null
    private var timeElapsed: Long = 0 // Variable para almacenar el tiempo transcurrido

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        timerText = findViewById(R.id.timerText) // Inicializa el TextView del temporizador
        startTimer() // Inicia el temporizador

        val questionText = findViewById<TextView>(R.id.questionText)
        val questionImage = findViewById<ImageView>(R.id.questionImage)
        val answer1 = findViewById<Button>(R.id.answer1)
        val answer2 = findViewById<Button>(R.id.answer2)
        val answer3 = findViewById<Button>(R.id.answer3)
        val answer4 = findViewById<Button>(R.id.answer4)

        // Obtener preguntas del servidor
        fetchPreguntas { preguntasList ->
            preguntas = preguntasList
            loadQuestion(questionText, questionImage, answer1, answer2, answer3, answer4)
        }
    }

    private fun startTimer() {
        // Configura el temporizador para contar hasta que el usuario termine el test
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) { // Usar Long.MAX_VALUE para que nunca se acabe automáticamente
            override fun onTick(millisUntilFinished: Long) {
                timeElapsed += 1 // Incrementa el tiempo transcurrido en segundos
                timerText.text = "Tiempo: $timeElapsed s" // Actualiza el TextView
            }

            override fun onFinish() {
                // Este método no se usará, pero debemos implementarlo
                // porque la clase es abstracta.
            }
        }.start()
    }

    private fun navigateToResults() {
        // Navegar a la pantalla de resultados
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("EXTRA_SCORE", correctAnswers) // Asegúrate de que la clave coincida
        intent.putExtra("totalQuestions", preguntas.size) // Asegúrate de que esto contenga el total de preguntas
        intent.putExtra("EXTRA_TIME", timeElapsed) // Pasar el tiempo total
        startActivity(intent)
        finish()
    }

    private fun fetchPreguntas(onResult: (List<Pregunta>) -> Unit) {
        // Realizar la llamada a la API para obtener las preguntas
        RetrofitClient.instance.getPreguntas().enqueue(object : Callback<List<Pregunta>> {
            override fun onResponse(call: Call<List<Pregunta>>, response: Response<List<Pregunta>>) {
                // Imprimir el código de respuesta para depuración
                Log.d("Response Code", response.code().toString())

                // Verificar si la respuesta es exitosa
                if (response.isSuccessful) {
                    val fetchedPreguntas = response.body() ?: emptyList()
                    if (fetchedPreguntas.isNotEmpty()) {
                        Log.d("JSON Response", fetchedPreguntas.toString())  // Log de preguntas obtenidas
                        onResult(fetchedPreguntas)  // Pasar las preguntas a la actividad
                    } else {
                        Toast.makeText(this@QuestionActivity, "No se encontraron preguntas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Log de error si la respuesta no es exitosa
                    Log.e("Response Error", response.errorBody()?.string() ?: "Error desconocido")
                    Toast.makeText(this@QuestionActivity, "Error al cargar preguntas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Pregunta>>, t: Throwable) {
                Toast.makeText(this@QuestionActivity, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit Error", t.message.toString())  // Log del error de conexión
            }
        })
    }

    private fun loadQuestion(
        questionText: TextView,
        questionImage: ImageView,
        answer1: Button,
        answer2: Button,
        answer3: Button,
        answer4: Button
    ) {
        if (currentQuestionIndex < preguntas.size) {
            val preguntaActual = preguntas[currentQuestionIndex]

            questionText.text = preguntaActual.pregunta
            Picasso.get().load(preguntaActual.imatge).into(questionImage)

            // Cargar las respuestas
            answer1.text = preguntaActual.respostes[0].resposta
            answer2.text = preguntaActual.respostes[1].resposta
            answer3.text = preguntaActual.respostes[2].resposta
            answer4.text = preguntaActual.respostes[3].resposta

            answer1.setOnClickListener { checkAnswer(0) }
            answer2.setOnClickListener { checkAnswer(1) }
            answer3.setOnClickListener { checkAnswer(2) }
            answer4.setOnClickListener { checkAnswer(3) }
        } else {
            // Navegar a la pantalla de resultados
            navigateToResults()
        }
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val preguntaActual = preguntas[currentQuestionIndex]
        if (preguntaActual.respostes[selectedAnswerIndex].correcta) {
            correctAnswers++
        }
        currentQuestionIndex++
        loadQuestion(
            findViewById(R.id.questionText),
            findViewById(R.id.questionImage),
            findViewById(R.id.answer1),
            findViewById(R.id.answer2),
            findViewById(R.id.answer3),
            findViewById(R.id.answer4)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel() // Asegúrate de cancelar el temporizador al destruir la actividad
    }
}
