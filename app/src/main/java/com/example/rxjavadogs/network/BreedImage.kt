package com.example.rxjavadogs.network

import com.squareup.moshi.Json

data class BreedImage(
    @field:Json(name = "message")
    val url: String,
    @field:Json(name = "status")
    val status: String,
)
