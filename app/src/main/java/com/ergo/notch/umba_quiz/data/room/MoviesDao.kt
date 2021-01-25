package com.ergo.notch.umba_quiz.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ergo.notch.umba_quiz.data.model.MovieModel
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Insert, update, select, delete operation/query using RxJava so thread
 * scheduling, non block ui operation can be maintained easily.
 */
@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun loadMovies(): Single<List<MovieModel>>

    @Query("SELECT * from movies where id = :id LIMIT 1")
    fun loadMovieById(id: Int): Single<MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieModel>): Completable

    @Query("DELETE FROM movies")
    fun deleteAllMovies(): Completable
}