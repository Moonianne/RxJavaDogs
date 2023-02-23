package com.example.rxjavadogs.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object DogApi {

    val instance: DogsClient by lazy(::get)

    private fun get(): DogsClient = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(DogsClient::class.java)
}
