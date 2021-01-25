package com.ergo.notch.umba_quiz.di

import com.ergo.notch.umba_quiz.ui.MovieModule
import com.ergo.notch.umba_quiz.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity Binding module, it uses @ContributesAndroidInjector.
 * It will be used to create subComponent Scope Instance and  the life
 * of the scope instance which like as long as the activity & Fragment.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MovieModule::class])
    internal abstract fun mainActivity(): MainActivity

}