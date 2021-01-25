package com.ergo.notch.umba_quiz

import com.ergo.notch.umba_quiz.data.DataSource
import com.ergo.notch.umba_quiz.data.model.MovieModel
import com.ergo.notch.umba_quiz.data.model.ResponseModel
import com.ergo.notch.umba_quiz.ui.movieList.MovieListContract
import com.ergo.notch.umba_quiz.ui.movieList.MovieListPresenter
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UmbaTest {

    @Mock
    private lateinit var view: MovieListContract.View

    @Mock
    private lateinit var dataSource: DataSource

    @Mock
    private lateinit var presenter: MovieListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MovieListPresenter(dataSource)
    }

    @Test
    fun loadMovies() {


        val movies = ResponseModel<MovieModel>(results = arrayListOf(), page = 1)
        // Use retrofit-mock to create your mockResponse.
        // See: https://github.com/square/retrofit/tree/master/retrofit-mock
        Mockito.`when`(dataSource.api.getMovies()).thenReturn(Observable.just(movies))
        presenter.requestRefresh()
        Mockito.verify(view).showMovies(movies.results)
    }
}