package com.example.trafficquizapp.data

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/preguntes")
    fun getPreguntes(): Call<PreguntesResponse>
}
