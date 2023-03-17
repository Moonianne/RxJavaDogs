package com.example.rxjavadogs.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsClient {
    @GET("breeds/list/all")
    fun getDogList(): Call<Breeds>

    @GET("breed/{breed}/images/random")
    fun getDogImage(@Path("breed") breed: String): Call<BreedImage>
}
