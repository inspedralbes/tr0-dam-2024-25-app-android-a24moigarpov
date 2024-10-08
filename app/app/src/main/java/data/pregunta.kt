package com.example.trafficquizapp.data

data class Pregunta(
    val id: Int,
    val pregunta: String,
    val respostes: List<String>,
    val resposta_correcta: Int,
    val imatge: String
)

data class PreguntesResponse(
    val preguntes: List<Pregunta>
)
