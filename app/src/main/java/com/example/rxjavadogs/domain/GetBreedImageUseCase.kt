package com.example.rxjavadogs.domain

import com.example.rxjavadogs.network.BreedImage
import com.example.rxjavadogs.network.DogApi
import com.example.rxjavadogs.network.DogsClient
import com.example.rxjavadogs.util.subscribe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.flatMapIterable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
TODO Replace the invoke() being used in this method with one that returns Observable type and
 subscribe to it
*/
fun main() {
    val breedImageUseCase = GetBreedImageUseCase()
    breedImageUseCase()
        .subscribe(
            onNext = { (breed, imageUrl) ->
                println("$breed : $imageUrl")
            }
        )
}
class GetBreedImageUseCase {
    private val client: DogsClient = DogApi.instance
    private val breedsUseCase = GetBreedsUseCase()

    operator fun invoke(callback: (Pair<String, String>) -> Unit) {
        breedsUseCase { dogs ->
            dogs.breeds.keys.forEach { breed ->

                client.getDogImage(breed).enqueue(
                    object : Callback<BreedImage> {
                        override fun onResponse(
                            call: Call<BreedImage>,
                            response: Response<BreedImage>,
                        ) {
                            response.body()?.let { imageResult ->
                                callback(breed to imageResult.url)
                            }
                        }

                        override fun onFailure(call: Call<BreedImage>, t: Throwable) {
                            throw t
                        }
                    },
                )
            }
        }
    }

    operator fun invoke(): Observable<Pair<String, String>> {
        return breedsUseCase()
            .map { it.breeds.keys.toList() }
            .flatMapIterable()
            .flatMap { breed ->
                client.getDogImageObservable(breed)
                    .map { dogImage -> breed to dogImage.url }
            }
    }
}
