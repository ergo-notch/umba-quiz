package com.ergo.notch.umba_quiz.data.api

import com.ergo.notch.umba_quiz.BuildConfig
import com.ergo.notch.umba_quiz.data.model.ResponseModel
import com.ergo.notch.umba_quiz.data.model.MovieModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


/**
 * Api invoke through this class. it use Retrofit and OKHttp as client.
 */
interface RestApi {


    @GET("/3/movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String =
            BuildConfig.MOVIES_API_KEY,
        @Query("language") language: String = "es-MX"
    ): Observable<ResponseModel<MovieModel>>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String =
            BuildConfig.MOVIES_API_KEY,
        @Query("language") language: String = "es-MX"
    ): Observable<ResponseModel<MovieModel>>


    companion object {

        @get:Synchronized
        var adapter: Retrofit? = null
            private set

        val api: RestApi
            @Synchronized get() {
                if (adapter == null) {
                    initRestAdapter()
                }
                return adapter!!.create(RestApi::class.java)
            }


        @Synchronized
        private fun initRestAdapter() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            // use OkHttp client
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                    requestBuilder.method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .addInterceptor(loggingInterceptor)
                .build()

            adapter = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BuildConfig.SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io())
                )
                .build()
        }


    }


}


