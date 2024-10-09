package com.example.juegoandroid.network

data class Resposta(
    val resposta: String,
    val correcta: Boolean
)

data class Pregunta(
    val id: Int,
    val pregunta: String,
    val respostes: List<Resposta>,
    val imatge: String
)
