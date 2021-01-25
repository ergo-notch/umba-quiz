package com.ergo.notch.umba_quiz.ui.movieList

import com.ergo.notch.umba_quiz.data.DataSource
import com.ergo.notch.umba_quiz.di.ActivityScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Movie List presenter which implements  MovieListContract.Presenter
 */
@ActivityScope
class MovieListPresenter @Inject constructor(private val dataSource: DataSource)
    : MovieListContract.Presenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: MovieListContract.View

    override fun subscribe(view: MovieListContract.View) {
        this.view = view
        disposable.add(dbOrApi())
    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun requestRefresh() {
        disposable.add(api())
    }



    private fun dbOrApi(): Disposable {
        return dataSource.database.moviesDao().loadMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {
                    view.showMovies(it)
                    view.showLoading(false)
                } else {
                    api()
                }

            }, { e -> Timber.e(e) })
    }




    private fun api(): Disposable {
        view.showLoading(true)
        return dataSource.api.getMovies()
            .observeOn(Schedulers.io())
            .subscribe({
                val movieDao = dataSource.database.moviesDao()
                movieDao.deleteAllMovies()
                    .subscribe({
                        Timber.d("Movies delete succeed")
                        movieDao.insertMovies(it.results)
                            .subscribe({
                                Timber.d("Movie new records store succeed")
                                movieDao.loadMovies()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        view.showMovies(it)
                                        view.showLoading(false)
                                    }, { e -> Timber.e(e) })

                            }, { e -> Timber.e(e) })
                    }, { e -> Timber.e(e) })

            }, { e ->
                Observable.just(e)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        view.showError()
                        view.showLoading(false)
                    }
                Timber.e(e)
            })
    }


}
