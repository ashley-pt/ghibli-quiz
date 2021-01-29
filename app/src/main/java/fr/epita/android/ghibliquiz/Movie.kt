package fr.epita.android.ghibliquiz

class Movie (
    val id : String,
    val title : String,
    val description: String,
    val director : String,
    val producer : String,
    val release_date : Int,
    val rt_score : Int,
    val people : List<String>,
    val species : List<String>,
    val locations : List<String>,
    val vehicles : List<String>,
    val url : String
) {
}