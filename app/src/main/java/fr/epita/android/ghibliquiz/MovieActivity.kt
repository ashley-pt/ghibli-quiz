package fr.epita.android.ghibliquiz

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieId: String = intent.getStringExtra("MOVIEID")
        val answerId: String = intent.getStringExtra("ANSWERID")

        val baseURL = "https://ghibliapi.herokuapp.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: MovieService = retrofit.create(MovieService::class.java)
        Log.w("RECEIVED", movieId)

        val wsCallback: Callback<Movie> = object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.w("MOVIE", "WebService call failed")
            }

            override fun onResponse(
                call: Call<Movie>, response: Response<Movie>
            ) {
                Log.d("MOVIE", "Success")

                if (response.code() == 200) {
                    val responseData = response.body()

                    if (responseData != null) {
                        Log.w("RECEIVED", responseData.title)
                        val isCorrectView: TextView = findViewById(R.id.isCorrect)
                        val answerView: TextView = findViewById(R.id.answer)
                        val titleView: TextView = findViewById(R.id.titleContent)
                        val directorView: TextView = findViewById(R.id.directorContent)
                        val yearView: TextView = findViewById(R.id.yearContent)
                        val synopsisView: TextView = findViewById(R.id.synopsisContent)
                        val charactersView: TextView = findViewById(R.id.charactersContent)

                        if (answerId == movieId) {
                            isCorrectView.append("RIGHT !")
                            isCorrectView.setTextColor(Color.parseColor("#26D91B"));
                        } else {
                            isCorrectView.append("WRONG !")
                            answerView.append("This character can be seen in " + responseData.title)
                            isCorrectView.setTextColor(Color.parseColor("#D91414"));

                        }

                        titleView.append(responseData.title)
                        directorView.append(responseData.director)
                        yearView.append(responseData.release_date.toString())
                        synopsisView.append(responseData.description)
                        val url_search = "https://www.google.com/search?q=" + responseData.title
                        val gameMore: Button = findViewById(R.id.more)
                        gameMore.setOnClickListener {
                            val directGameUrl =
                                Intent(Intent.ACTION_VIEW, Uri.parse(url_search))
                            startActivity(directGameUrl)
                        }
                    }
                }
            }
        }
        service.getMovieDetails(movieId).enqueue(wsCallback)
    }
}