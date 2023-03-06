package com.example.rxjavadogs.domain

import com.example.rxjavadogs.network.DogApi
import com.example.rxjavadogs.network.Dogs
import com.example.rxjavadogs.network.DogsClient
import com.example.rxjavadogs.util.subscribe
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
TODO Replace the invoke() being used in this method with one that returns Observable type and
 subscribe to it
*/
fun main() {
    GetBreedsUseCase().run {
        invoke().subscribe(
            onNext = { dogs ->
                dogs.breeds.keys.forEach(::println)
            },
        )
    }
}

class GetBreedsUseCase {
    private val client: DogsClient = DogApi.instance
    operator fun invoke(callback: (Dogs) -> Unit) {
        client.getDogList().enqueue(object : Callback<Dogs> {
            override fun onResponse(call: Call<Dogs>, response: Response<Dogs>) {
                response.body()?.let { breedResponse ->
                    callback(breedResponse)
                }
            }

            override fun onFailure(call: Call<Dogs>, t: Throwable) {
                throw t
            }
        })
    }

    operator fun invoke(): Observable<Dogs> = client.getDogsListObservable()
}
