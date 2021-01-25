package com.ergo.notch.umba_quiz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Moviet Model also used for ORM (Room) and Json object
 */
@Entity(tableName = "movies")
data class MovieModel(

    @PrimaryKey
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String?,

    val adult: Boolean?,

    val overview: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    val title: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("vote_count")
    val voteCount: String?,

    @SerializedName("video")
    val video: String?,

    @SerializedName("vote_average")
    val voteAverage: Double?
)


