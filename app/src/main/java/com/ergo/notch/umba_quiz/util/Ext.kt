package com.ergo.notch.umba_quiz.util

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ergo.notch.umba_quiz.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Functional extension of View to show Snack Bar
 */
fun View.showSnackBar(message: String, duration: Int) {

    Snackbar.make(this, message, duration).show()
}

/**
 * Functional extension TextView to convert FontAwesome text to icon
 * using stringResId
 */
fun TextView.toIcon(stringResId: Int) {
    toIcon(context.getString(stringResId))
}

/**
 * Functional extension TextView to convert FontAwesome text to icon
 */
fun TextView.toIcon(text: CharSequence? = this.text) {
    gravity = Gravity.CENTER
    typeface = Typeface.createFromAsset(
        context.assets,
        "fonts/FontAwesome.ttf"
    )
    this.text = text
}

fun AppCompatImageView.loadImageFromUrl(url: String, context: Context) {
    Glide.with(context)
        .load(url)
        .placeholder(android.R.drawable.ic_menu_report_image)
        .error(android.R.drawable.stat_notify_error)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}



