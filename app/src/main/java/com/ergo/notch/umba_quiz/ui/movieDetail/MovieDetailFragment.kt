package com.ergo.notch.umba_quiz.ui.movieDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import com.ergo.notch.umba_quiz.BuildConfig
import com.ergo.notch.umba_quiz.R
import com.google.android.material.snackbar.Snackbar
import com.ergo.notch.umba_quiz.data.model.MovieModel
import com.ergo.notch.umba_quiz.di.ActivityScope
import com.ergo.notch.umba_quiz.util.loadImageFromUrl
import com.ergo.notch.umba_quiz.util.showSnackBar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.movie_detail_fragment_layout.*
import kotlinx.android.synthetic.main.movie_detail_fragment_layout.tvAverage
import kotlinx.android.synthetic.main.movie_detail_fragment_layout.tvOriginalTitle
import kotlinx.android.synthetic.main.movie_detail_fragment_layout.tvReleaseDate
import kotlinx.android.synthetic.main.movie_detail_fragment_layout.tvTitle
import kotlinx.android.synthetic.main.movie_row_item_layout.*
import javax.inject.Inject

/**
 * This fragment class to display details of a movie
 */
@ActivityScope
class MovieDetailFragment : DaggerFragment(), MovieDetailContract.View {

    @Inject
    override lateinit var presenter: MovieDetailContract.Presenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_detail_fragment_layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.requestDetails(requireArguments().getInt("id", 0))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.ivArrow.setOnClickListener {
            this.requireActivity().supportFragmentManager.popBackStack()
//            fragmentManager?.popBackStack()
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }


    @SuppressLint("SetTextI18n")
    override fun showDetails(movieModel: MovieModel) {
        tvTitle.text = movieModel.title
        tvOriginalTitle.text = movieModel.originalTitle
        tvReleaseDate.text = "Fecha de lanzamiento " + movieModel.releaseDate
        tvAverage.text = movieModel.voteAverage.toString()
        ivForegroundPoster.loadImageFromUrl(
            String.format(
                BuildConfig.POSTER_PATH,
                movieModel.posterPath
            ), this.requireContext()
        )
        ivBackgroundPoster.loadImageFromUrl(
            String.format(
                BuildConfig.POSTER_PATH,
                movieModel.backdropPath
            ), this.requireContext()
        )
        tvOverview.text = movieModel.overview

    }


    override fun showError() {
        view?.showSnackBar(getString(R.string.msg_failed_load), Snackbar.LENGTH_LONG)
    }

    companion object {
        fun getInstance(id: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }


}