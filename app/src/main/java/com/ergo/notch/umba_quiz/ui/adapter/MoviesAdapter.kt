package com.ergo.notch.umba_quiz.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergo.notch.umba_quiz.BuildConfig
import com.ergo.notch.umba_quiz.R
import com.ergo.notch.umba_quiz.data.model.MovieModel
import com.ergo.notch.umba_quiz.util.loadImageFromUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_row_item_layout.*

/**
 */
class MoviesAdapter(
    var movieModels: List<MovieModel>,
    private val context: Context,
    private val itemClick: (MovieModel) -> Unit = {}
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setMovies(movies: List<MovieModel>) {
        this.movieModels = movies
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return inflate.inflate(R.layout.movie_row_item_layout, parent, false)
            .run { ItemViewHolder(this, itemClick) }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).run {
            bind(movieModels[position])
        }
    }


    override fun getItemCount() = movieModels.size


    inner class ItemViewHolder(
        override val containerView: View,
        private val itemClick: (MovieModel) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bind(movieModel: MovieModel) {
            with(movieModel) {
                tvTitle.text = movieModel.title
                ivPoster.loadImageFromUrl(
                    String.format(
                        BuildConfig.POSTER_PATH,
                        movieModel.posterPath!!
                    ), context
                )
                tvOriginalTitle.text = movieModel.originalTitle
                tvAverage.text = movieModel.voteAverage.toString()
                tvReleaseDate.text = "Fecha de lanzamiento " + movieModel.releaseDate
                ivPoster.transitionName = movieModel.id.toString()

                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }


}
