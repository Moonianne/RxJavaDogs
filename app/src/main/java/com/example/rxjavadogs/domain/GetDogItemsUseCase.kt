package com.example.rxjavadogs.domain

import com.example.rxjavadogs.network.DogApi
import com.example.rxjavadogs.network.DogImage
import com.example.rxjavadogs.network.Dogs
import com.example.rxjavadogs.view.Breed
import com.example.rxjavadogs.view.Dog
import com.example.rxjavadogs.view.ImageUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun main() {
    TODO()
}

class GetDogItemsUseCase {
    operator fun invoke(callback: (List<Dog>) -> Unit) {
        val dogs = mutableListOf<Dog>()
        DogApi.instance.getDogList().enqueue(object : Callback<Dogs> {
            override fun onResponse(call: Call<Dogs>, response: Response<Dogs>) {
                response.body()?.let { breedResponse ->
                    val breeds = breedResponse.breeds.keys
                    breeds.forEach { breed ->

                        DogApi.instance.getDogImage(breed).enqueue(
                            object : Callback<DogImage> {
                                override fun onResponse(
                                    call: Call<DogImage>,
                                    response: Response<DogImage>,
                                ) {
                                    response.body()?.let { imageResult ->
                                        val dog = Dog(
                                            breed = Breed(breed),
                                            imageUrl = ImageUrl(imageResult.url),
                                        )
                                        dogs.add(dog)
                                    }

                                    if (dogs.size == breeds.size) {
                                        callback(dogs)
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

            override fun onFailure(call: Call<Dogs>, t: Throwable) {
                throw t
            }
        })
    }
}
