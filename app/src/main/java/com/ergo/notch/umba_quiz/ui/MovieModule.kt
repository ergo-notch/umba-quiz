package com.ergo.notch.umba_quiz.ui

import com.ergo.notch.umba_quiz.di.ActivityScope
import com.ergo.notch.umba_quiz.di.FragmentScope
import com.ergo.notch.umba_quiz.ui.movieDetail.MovieDetailContract
import com.ergo.notch.umba_quiz.ui.movieDetail.MovieDetailFragment
import com.ergo.notch.umba_quiz.ui.movieDetail.MovieDetailPresenter
import com.ergo.notch.umba_quiz.ui.movieList.MovieListContract
import com.ergo.notch.umba_quiz.ui.movieList.MovieListFragment
import com.ergo.notch.umba_quiz.ui.movieList.MovieListPresenter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Movie Module which to inject dependencies  fragment and
 * Exposes presenter
 */
@Module
abstract class MovieModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun moviesFragment(): MovieListFragment

    @ActivityScope
    @Binds
    internal abstract fun moviesPresenter(presenter: MovieListPresenter): MovieListContract.Presenter


    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun movieDetailFragment(): MovieDetailFragment

    @ActivityScope
    @Binds
    internal abstract fun movieDetailPresenter(presenter: MovieDetailPresenter): MovieDetailContract.Presenter
}