package com.ergo.notch.umba_quiz.ui.movieList

import com.ergo.notch.umba_quiz.ui.BasePresenter
import com.ergo.notch.umba_quiz.ui.BaseView
import com.ergo.notch.umba_quiz.data.model.MovieModel

/**
 * Movie list contract between view and presenter of MVP
 */
interface MovieListContract {
    interface Presenter : BasePresenter<View> {
        fun requestRefresh()
    }

    interface View : BaseView<Presenter> {

        fun showLoading(active: Boolean)

        fun showMovies(movieModels : List<MovieModel>)

        fun showError()

    }
}