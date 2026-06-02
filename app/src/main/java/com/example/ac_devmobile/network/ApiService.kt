package com.example.ac_devmobile.network

import com.example.ac_devmobile.model.Produto
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("produtos")
    fun listar(): Call<List<Produto>>

    @POST("produtos")
    fun salvar(@Body produto: Produto): Call<Produto>

    @PUT("produtos/{id}")
    fun atualizar(@Path("id") id: Long, @Body produto: Produto): Call<Produto>

    @DELETE("produtos/{id}")
    fun deletar(@Path("id") id: Long): Call<Void>
}
