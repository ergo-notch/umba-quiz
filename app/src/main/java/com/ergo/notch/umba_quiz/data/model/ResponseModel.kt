package com.ergo.notch.umba_quiz.data.model


data class ResponseModel<T>(

    val page: Int?,
    val results: List<T>
)
