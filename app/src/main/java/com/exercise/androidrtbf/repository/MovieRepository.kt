package com.exercise.androidrtbf.repository

import com.exercise.androidrtbf.model.Movie
import retrofit2.http.GET
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieService: MovieService) {
    suspend fun getMovies() = movieService.getMovies()
}

interface MovieService {
    @GET("movies.json")
    suspend fun getMovies(): List<Movie>
}