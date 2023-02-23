package com.example.rxjavadogs.domain

import com.example.rxjavadogs.network.DogApi
import com.example.rxjavadogs.network.DogImage
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
    breedImageUseCase {
        it.forEach { (breed, imageUrl) ->
            println("$breed : $imageUrl")
        }
    }
}
class GetBreedImageUseCase {
    private val breedsUseCase = GetBreedsUseCase()

    operator fun invoke(callback: (List<Pair<String, String>>) -> Unit) {
        val breedsToImageUrls = mutableListOf<Pair<String, String>>()

        breedsUseCase { dogs ->
            dogs.breeds.keys.forEach { breed ->

                DogApi.instance.getDogImage(breed).enqueue(
                    object : Callback<DogImage> {
                        override fun onResponse(
                            call: Call<DogImage>,
                            response: Response<DogImage>,
                        ) {
                            response.body()?.let { imageResult ->
                                breedsToImageUrls.add(breed to imageResult.url)
                            }

                            if (breedsToImageUrls.size == dogs.breeds.keys.size) {
                                callback(breedsToImageUrls)
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
