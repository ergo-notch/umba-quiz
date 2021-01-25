package com.ergo.notch.umba_quiz.di

import android.content.Context
import com.ergo.notch.umba_quiz.App
import dagger.Binds
import dagger.Module


/**
 * App Module
 */
@Module
abstract class AppModule {

    /**
     * Expose Application as an injectable context
     */
    @Binds
    internal abstract fun bindContext(app: App): Context


}