package com.ergo.notch.umba_quiz.ui.movieDetail

import com.ergo.notch.umba_quiz.data.DataSource
import com.ergo.notch.umba_quiz.di.ActivityScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Movie Details presenter which implements  MovieDetailContract.Presenter
 */
@ActivityScope
class MovieDetailPresenter @Inject constructor(private val dataSource: DataSource)
    : MovieDetailContract.Presenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: MovieDetailContract.View


    override fun subscribe(view: MovieDetailContract.View) {
        this.view = view
    }

    override fun unSubscribe() {
        disposable.clear()

    }

    override fun requestDetails(id: Int) {
        disposable.add(
            dataSource.database.moviesDao().loadMovieById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Movie Data $it")
                    view.showDetails(it)
                }, { e ->
                    view.showError()
                    Timber.e(e)
                })
        )
    }
}