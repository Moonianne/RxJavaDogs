package com.example.rxjavadogs.network

import com.squareup.moshi.Json

data class Dogs(
    @field:Json(name = "message")
    val breeds: Map<String, List<String>>
)
