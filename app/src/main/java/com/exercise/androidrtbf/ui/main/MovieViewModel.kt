package com.exercise.androidrtbf.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exercise.androidrtbf.model.Movie
import com.exercise.androidrtbf.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _selected = MutableLiveData<Movie>()
    val selected: LiveData<Movie>
        get() = _selected

    fun getMovies() = viewModelScope.launch {
        _progress.value = true
        try {
            _movies.value = repository.getMovies()
        } catch (e: Exception) {
            _error.value = e
        }
        _progress.value = false
    }

    fun onMovieSelected(movie:Movie) {
        _selected.value = movie
    }
}