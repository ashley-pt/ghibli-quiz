package fr.epita.android.ghibliquiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET(value = "people")
    fun listAllQuiz() : Call<List<Quiz>>

    @GET(value = "films")
    fun listAllFilms() : Call<List<Movie>>

    @GET(value = "films/{id}")
    fun getMovieDetails(@Path("id") id : String) : Call<Movie>
}