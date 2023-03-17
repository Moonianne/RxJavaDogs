package com.example.rxjavadogs.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsClient {
    @GET("breeds/list/all")
    fun getDogList(): Call<Breeds>

    @GET("breeds/list/all")
    fun getDogsListObservable(): Observable<Breeds>

    @GET("breed/{breed}/images/random")
    fun getDogImage(@Path("breed") breed: String): Call<BreedImage>

    @GET("breed/{breed}/images/random")
    fun getDogImageObservable(@Path("breed") breed: String): Observable<BreedImage>
}
