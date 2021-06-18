package com.exercise.androidrtbf.model

import com.squareup.moshi.Json

data class Movie(val title: String, val year: String, val director: String, @field:Json(name ="posterUrl") val imageUrl: String)
