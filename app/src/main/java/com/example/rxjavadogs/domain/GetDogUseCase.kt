package com.example.rxjavadogs.domain

import com.example.rxjavadogs.util.subscribe
import com.example.rxjavadogs.view.Breed
import com.example.rxjavadogs.view.Dog
import com.example.rxjavadogs.view.ImageUrl
import io.reactivex.rxjava3.core.Observable

/*
TODO Replace the invoke() being used in this method with one that returns Observable type and
 subscribe to it
*/
fun main() {
    val dogUseCase = GetDogUseCase()
    dogUseCase()
        .subscribe(
            onNext = { dog ->
                println(dog)
            },
        )
}

class GetDogUseCase {
    private val breedImageUseCase = GetBreedImageUseCase()

    operator fun invoke(callback: (Dog) -> Unit) {
        breedImageUseCase { (breed, imageUrl) ->
            val dog = Dog(
                breed = Breed(breed),
                imageUrl = ImageUrl(imageUrl),
            )
            callback(dog)
        }
    }

    operator fun invoke(): Observable<Dog> {
        return breedImageUseCase()
            .map { (breed, imageUrl) ->
                Dog(
                    breed = Breed(breed),
                    imageUrl = ImageUrl(imageUrl),
                )
            }
    }
}
