package com.ergo.notch.umba_quiz.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergo.notch.umba_quiz.R
import com.google.android.material.snackbar.Snackbar
import com.ergo.notch.umba_quiz.ui.adapter.MoviesAdapter
import com.ergo.notch.umba_quiz.di.ActivityScope
import com.ergo.notch.umba_quiz.data.model.MovieModel
import com.ergo.notch.umba_quiz.ui.movieDetail.MovieDetailFragment
import com.ergo.notch.umba_quiz.util.showSnackBar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.movie_list_fragment_layout.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 */
@ActivityScope
class MovieListFragment @Inject constructor() : DaggerFragment(), MovieListContract.View {

    @Inject
    override lateinit var presenter: MovieListContract.Presenter

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_list_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter(arrayListOf(), this.requireContext()) { showDetail(it) }
        with(rvMovies) {
            setHasFixedSize(true)
            adapter = moviesAdapter
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            addItemDecoration(DividerItemDecoration(context, manager.orientation))
        }

        ptrMovies.setOnRefreshListener {
            presenter.requestRefresh()
        }

        emptyView.setOnClickListener {
            showEmptyView(false)
            presenter.requestRefresh()
        }


    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }


    override fun showLoading(active: Boolean) {
        if (active)
            ptrMovies?.takeIf {
                !it.isRefreshing
            }?.apply { isRefreshing = true } else {
            ptrMovies?.takeIf {
                it.isRefreshing
            }?.apply { isRefreshing = false }

        }
    }


    override fun showMovies(movieModels: List<MovieModel>) {
        Timber.d("Movies size %s", movieModels.size)
        moviesAdapter.setMovies(movieModels)
        showEmptyView(false)
    }


    override fun showError() {
        if (moviesAdapter.movieModels.isNullOrEmpty()) {
            showEmptyView(true)
        } else {
            view?.showSnackBar(getString(R.string.msg_failed_to_refresh), Snackbar.LENGTH_LONG)
        }
    }

    private fun showEmptyView(flag: Boolean) {
        if (flag) {
            emptyView?.visibility = View.VISIBLE
            rvMovies?.visibility = View.GONE
        } else {
            emptyView?.visibility = View.GONE
            rvMovies?.visibility = View.VISIBLE

        }
    }

    private fun showDetail(movie: MovieModel) {
        requireActivity().supportFragmentManager.beginTransaction().run {
            replace(R.id.container, MovieDetailFragment.getInstance(movie.id))
            addToBackStack(null)
            commit()
        }
    }


}