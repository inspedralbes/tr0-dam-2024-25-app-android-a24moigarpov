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

        // Lista de preguntas y respuestas hardcoded
        val preguntas = listOf(
            Question(
                "Quina d'aquestes marques de cotxes és la del famós personatge de Transformers 'Bumblebee'?",
                "https://images.hdqwalls.com/wallpapers/bumblebee-4k-2020-t5.jpg",
                listOf("Ferrari", "Audi", "BMW", "Chevrolet Camaro"),
                3 // Índice de la respuesta correcta
            ),
            Question(
                "Quina marca de tecnologia fabrica l'iPhone?",
                "https://imagenes.20minutos.es/files/image_990_556/uploads/imagenes/2024/01/23/muchos-moviles.jpeg",
                listOf("Huawei", "Xiaomi", "Apple", "Samsung"),
                2
            ),
            Question(
                "Quina marca és la responsable de crear les pel·lícules de Spider-Man?",
                "https://getwallpapers.com/wallpaper/full/0/c/c/243743.jpg",
                listOf("DC", "Sony", "Warner", "HBO"),
                1
            ),
            Question(
                "Quina és la marca creadora de la famosa aplicació de futbol Fantasy?",
                "https://s2.sportstatics.com/relevo/www/multimedia/202307/21/media/cortadas/fantasy-horizontal-RprVFwyKMnhsCrEuBbGIpZJ-1200x648@Relevo.jpg",
                listOf("Sport", "Marca", "Fifa", "New Balance"),
                1
            ),
            Question(
                "Quina és la marca de les famoses zapatilles que porta Miles Morales a la seva pel·lícula animada?",
                "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/21ef627d-4c05-4ec0-8ba9-307583f2e9d4/dfztnww-e97a7150-33fc-4680-86fc-9eae21c62e18.jpg",
                listOf("Adidas", "Puma", "Nike", "Reebok"),
                2
            ),
            Question(
                "Quina marca de videojocs té com a mascota un eriçó blau?",
                "https://wallpapers.com/images/featured/shadow-the-hedgehog-57go7t3m1is5ixgx.jpg",
                listOf("Nintendo", "Sega", "Sony", "Atari"),
                1
            ),
            Question(
                "Quina marca de càmeres és coneguda pel model EOS?",
                "https://static.nationalgeographic.es/files/styles/image_3200/public/mm10052_20221117_399_cropped.jpg?w=1600&h=900",
                listOf("Canon", "Nikon", "Sony", "Olympus"),
                0
            ),
            Question(
                "Quina d'aquestes empreses és la creadora del videojoc 'The Last of Us'?",
                "https://i.redd.it/6mp2dh37lyg41.jpg",
                listOf("Ubisoft", "Riot Games", "Naughty Dog", "Activision Blizzard"),
                2
            ),
            Question(
                "Quina és la marca encarregada de produir la següent pel·lícula, 'Mid90s'?",
                "https://www.acuartaparede.com/wp-content/uploads/2019/06/Mid90s-soundtrack-available-to-stream-now.jpeg",
                listOf("Sony", "A24", "Netflix", "Disney"),
                1
            ),
            Question(
                "Quina de les següents marques comercials és famosa per desenvolupar i publicar la sèrie de videojocs 'grand theft auto'?",
                "https://media.revistagq.com/photos/6013bf464a3723647e5c158b/16:9/w_2560%2Cc_limit/grand-theft-auto-v-videojuego.jpg",
                listOf("Activision", "Ubisoft", "Electronic Arts", "Rockstar games"),
                3
            ),
            Question(
                "Quina de les següents marques d'automòbils és coneguda per la seva associació amb la pel·lícula 'Batman Begins' i altres films de la franquícia 'Batman'?",
                "https://thecollision.org/wp-content/uploads/2020/07/TDK4-1.jpg",
                listOf("Lamborghini", "Chevrolet", "Mercedes-Benz", "Ford"),
                3
            )
        )

        // Cargar la primera pregunta
        loadQuestion(preguntas, questionText, questionImage, answer1, answer2, answer3, answer4)
    }

    private fun loadQuestion(
        preguntas: List<Question>, questionText: TextView, questionImage: ImageView,
        answer1: Button, answer2: Button, answer3: Button, answer4: Button
    ) {
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
        loadQuestion(
            preguntas, findViewById(R.id.questionText), findViewById(R.id.questionImage),
            findViewById(R.id.answer1), findViewById(R.id.answer2),
            findViewById(R.id.answer3), findViewById(R.id.answer4)
        )
    }
}

// Clase de datos para manejar las preguntas
data class Question(
    val pregunta: String,
    val imagen: String,
    val respuestas: List<String>,
    val correctAnswerIndex: Int // Índice de la respuesta correcta
)
