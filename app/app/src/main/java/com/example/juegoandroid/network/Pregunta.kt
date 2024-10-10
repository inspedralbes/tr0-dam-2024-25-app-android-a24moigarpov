package com.example.juegoandroid.network

data class Pregunta(
    val id: Int,
    val pregunta: String,
    val respostes: List<Resposta>,
    val imatge: String
)

data class Resposta(
    val id: Int,
    val resposta: String,
    val correcta: Boolean
)
