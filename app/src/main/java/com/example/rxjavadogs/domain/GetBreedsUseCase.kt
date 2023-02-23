package com.example.rxjavadogs.domain

import com.example.rxjavadogs.network.DogApi
import com.example.rxjavadogs.network.Dogs
import com.example.rxjavadogs.network.DogsClient
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
        invoke { it.breeds.keys.forEach(::println) }
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

    operator fun invoke(): Observable<List<String>> {
        TODO(
            "Refactor the code in GetBreedsUseCase#invoke(callback: (List<String>) -> Unit)" +
                "so that it returns an Observable that emits the expected data.",
        )
    }
}
