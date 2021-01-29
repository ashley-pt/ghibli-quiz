package fr.epita.android.ghibliquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseURL = "https://ghibliapi.herokuapp.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service = retrofit.create(MovieService::class.java)
        val callback : Callback<List<Quiz>>  = object : Callback<List<Quiz>> {

            override fun onFailure(call: Call<List<Quiz>>, t: Throwable) {
                Log.w("TAG", t)
            }

            override fun onResponse(call: Call<List<Quiz>>, response: Response<List<Quiz>>) {
                if (response.code() == 200) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val randomMovieId = (0..responseData.size).random();
                        val quiz = responseData.get(randomMovieId);
                        val movie = quiz.films.get(0);
                        val movieId: String? = movie.substringAfterLast("/");
                        Log.w ( "GAME", movieId )
                        val questionView: TextView = findViewById(R.id.question)

                        Log.w("TAG", "Websrvice success : " + responseData.size)

                        val onItemClickListener = View.OnClickListener() {
                            val clickedMovie: Quiz = it.tag as Quiz
                            val toMovieDetail = Intent(this@MainActivity, MovieActivity::class.java)
                            val clickedFilms = clickedMovie.films.get(0);
                            val answerId: String? = clickedFilms.substringAfterLast("/");

                            toMovieDetail.putExtra("MOVIEID", movieId)
                            toMovieDetail.putExtra("ANSWERID", answerId)
                            startActivity(toMovieDetail)
                        }

                        val characters = listOf<Quiz>(responseData.get(randomMovieId),
                            responseData.get((0..responseData.size).random()),
                            responseData.get((0..responseData.size).random()),
                            responseData.get((0..responseData.size).random()),
                            responseData.get((0..responseData.size).random()),
                            responseData.get((0..responseData.size).random())
                            )

                        rv_movies.setHasFixedSize(true) //performance optimisation
                        rv_movies.layoutManager = LinearLayoutManager(this@MainActivity)
                        rv_movies.adapter =
                            QuizAdapter(characters, this@MainActivity, onItemClickListener)
                    }
                }
            }
        }
        service.listAllQuiz().enqueue(callback)
    }
}