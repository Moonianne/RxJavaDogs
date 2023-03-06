package com.example.rxjavadogs.domain

import com.example.rxjavadogs.network.DogApi
import com.example.rxjavadogs.network.DogImage
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
    val breedImageUseCase = GetBreedImageUseCase()
    breedImageUseCase { (breed, imageUrl) ->
        println("$breed : $imageUrl")
    }
}
class GetBreedImageUseCase {
    private val client: DogsClient = DogApi.instance
    private val breedsUseCase = GetBreedsUseCase()

    operator fun invoke(callback: (Pair<String, String>) -> Unit) {
        breedsUseCase { dogs ->
            dogs.breeds.keys.forEach { breed ->

                client.getDogImage(breed).enqueue(
                    object : Callback<DogImage> {
                        override fun onResponse(
                            call: Call<DogImage>,
                            response: Response<DogImage>,
                        ) {
                            response.body()?.let { imageResult ->
                                callback(breed to imageResult.url)
                            }
                        }

                        override fun onFailure(call: Call<DogImage>, t: Throwable) {
                            throw t
                        }
                    },
                )
            }
        }
    }

    operator fun invoke(): Observable<List<Pair<String, String>>> {
        TODO(
            "Refactor the code in GetBreedImageUseCase#invoke(callback: (List<Pair<String, String>>) -> Unit)" +
                "so that it returns an Observable that emits the expected data.",
        )
    }
}
