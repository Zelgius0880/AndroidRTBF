package com.exercise.androidrtbf

import com.exercise.androidrtbf.di.AppModule
import com.exercise.androidrtbf.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieRepositoryTest {
    private val movieRepository = MovieRepository(AppModule.provideMovieService())

    @Test
    fun `get movie list`(){
        runBlocking {
            val list = movieRepository.getMovies()
            Assert.assertTrue(list.isNotEmpty())
            Assert.assertTrue(list.first().title.isNotEmpty())
            Assert.assertTrue(list.first().year.isNotEmpty())
            Assert.assertTrue(list.first().director.isNotEmpty())
            Assert.assertTrue(list.first().imageUrl.isNotEmpty())
        }
    }
}