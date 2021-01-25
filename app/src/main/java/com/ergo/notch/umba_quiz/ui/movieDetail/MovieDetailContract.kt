package com.ergo.notch.umba_quiz.ui.movieDetail

import com.ergo.notch.umba_quiz.ui.BasePresenter
import com.ergo.notch.umba_quiz.ui.BaseView
import com.ergo.notch.umba_quiz.data.model.MovieModel

/**
 * Movie Detail contract between view and presenter of MVP
 */
interface MovieDetailContract {

    interface Presenter : BasePresenter<View> {
        fun requestDetails(id : Int)
    }


    interface View : BaseView<Presenter> {

        fun showDetails(movieModel : MovieModel)

        fun showError()

    }
}