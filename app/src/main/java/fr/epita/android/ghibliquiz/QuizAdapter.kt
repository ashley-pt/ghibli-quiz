package fr.epita.android.ghibliquiz

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class QuizAdapter (val data : List<Quiz>, val context : Activity, val onItemClickListener: View.OnClickListener) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quizTextView: TextView = itemView.findViewById(R.id.txt_view_name)//name
        val quizImageView: ImageView = itemView.findViewById(R.id.imageView_gender)//gender
        val ageTextView: TextView = itemView.findViewById(R.id.txt_view_age)//age
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //only creates if the pool of available viewholders is empty.
        val rowView: View = LayoutInflater.from(context).inflate(
            R.layout.list_quiz,
            parent,
            false
        )//creates view from layout through 'inflating' it
        rowView.setOnClickListener(onItemClickListener)
        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //This is where we put the data into a viewholder to pass to the system to display the view. only calls createview holder if there are no vh left in the 'ppol' of view holders
        val currentQuiz: Quiz = data[position]
        holder.quizTextView.text = currentQuiz.name//name
        holder.ageTextView.text = currentQuiz.age.toString()
        if (currentQuiz.gender == "Male")
            Glide.with(context).load(R.drawable.male).into(holder.quizImageView)//gender
        else
            Glide.with(context).load(R.drawable.female).into(holder.quizImageView)//gender
        holder.itemView.tag = currentQuiz

    }
}