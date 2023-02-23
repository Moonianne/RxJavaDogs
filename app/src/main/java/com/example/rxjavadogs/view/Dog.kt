package com.example.rxjavadogs.view

data class Dog(
    val breed: Breed,
    val imageUrl: ImageUrl,
)

@JvmInline
value class Breed(val value: String)

@JvmInline
value class ImageUrl(val value: String)


