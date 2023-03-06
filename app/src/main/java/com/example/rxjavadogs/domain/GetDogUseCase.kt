package com.example.rxjavadogs.domain

import android.database.Observable
import com.example.rxjavadogs.view.Breed
import com.example.rxjavadogs.view.Dog
import com.example.rxjavadogs.view.ImageUrl

/*
TODO Replace the invoke() being used in this method with one that returns Observable type and
 subscribe to it
*/
fun main() {
    val dogUseCase = GetDogUseCase()
    dogUseCase { dog ->
        println(dog)
    }
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
        TODO(
            "Refactor the code in GetDogUseCase#invoke(callback: (Dog) -> Unit)" +
                "so that it returns an Observable that emits the expected data.",
        )
    }
}
