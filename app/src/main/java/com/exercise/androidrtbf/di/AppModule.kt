package com.exercise.androidrtbf.di

import com.exercise.androidrtbf.repository.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMovieService(): MovieService =  Retrofit.Builder()
        .baseUrl("https://digitalia.be/coding_test/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()
}