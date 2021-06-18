package com.exercise.androidrtbf.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.exercise.androidrtbf.R
import com.exercise.androidrtbf.databinding.AdpaterMovieBinding
import com.exercise.androidrtbf.databinding.FragmentListBinding
import com.exercise.androidrtbf.model.Movie
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Adapter {
            viewModel.onMovieSelected(it)
            findNavController().apply {
                navigate(R.id.action_listFragment_to_detailFragment)
                currentDestination?.label = it.title
            }
        }
        binding.recyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        viewModel.progress.observe(viewLifecycleOwner) {
            if (it) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                getString(R.string.error_format, it::class.java.simpleName),
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.try_again) {viewModel.getMovies()}
                .show()
        }

        viewModel.getMovies()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class Adapter(private val onMovieClicked: (item: Movie) -> Unit) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {
        private val list: MutableList<Movie> = mutableListOf()

        fun update(list: List<Movie>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                AdpaterMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onMovieClicked
            )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int = list.size


        class ViewHolder(
            private val binding: AdpaterMovieBinding,
            private val onMovieClicked: (item: Movie) -> Unit
        ) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: Movie) {
                with(binding) {
                    title.text = movie.title
                    director.text = movie.director
                    year.text = movie.year
                    Picasso.get()
                        .load(movie.imageUrl)
                        .into(image)

                    root.setOnClickListener { onMovieClicked(movie) }
                }
            }
        }

    }
}