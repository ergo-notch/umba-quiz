package com.ergo.notch.umba_quiz.data

import android.content.Context
import com.ergo.notch.umba_quiz.data.api.RestApi
import com.ergo.notch.umba_quiz.data.room.MoviesDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DataSource class to access database and api.
 * This object is created  through dagger (DI) and it is used
 * in dependency injection through Object Graph.
 * The scope is Singleton it instance life will be entire application
 */
@Singleton
class DataSource @Inject constructor(private val context: Context) {
    val database = MoviesDatabase.getInstance(context)
    val api = RestApi.api
}