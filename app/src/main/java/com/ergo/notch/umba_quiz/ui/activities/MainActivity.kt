package com.ergo.notch.umba_quiz.ui.activities

import android.os.Bundle
import com.ergo.notch.umba_quiz.R
import com.ergo.notch.umba_quiz.ui.movieList.MovieListFragment
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragment: MovieListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fragment = supportFragmentManager
            .findFragmentById(R.id.container)
            ?: fragment

        supportFragmentManager.beginTransaction().run {
            replace(R.id.container, fragment)
            commit()
        }

    }
}
