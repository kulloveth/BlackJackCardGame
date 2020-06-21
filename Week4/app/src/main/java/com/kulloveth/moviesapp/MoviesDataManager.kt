package com.kulloveth.moviesapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Header
import com.kulloveth.moviesapp.models.Movie
import java.util.*
import kotlin.collections.ArrayList


/**
 * This is a viewModel class that helps to
 * manage data and simplify retaining data
 * during configuration change
 * */
class MoviesDataManager(application: Application) : AndroidViewModel(application) {


    private val TAG = MoviesDataManager::class.java.simpleName
    val context = application.applicationContext

    val KEY_FAVORITES = "KEY_FAVORITES"

    /*
    * keeps track of favorited movies
    * */
    fun isFavorite(movie: Movie): Boolean {
        return getFavorites(context)?.contains(movie.title) == true
    }

    // setting up movies to pass between fragments
    private val _movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val movieLiveData = _movieLiveData

    private val _favoriteLiveData: MutableLiveData<List<Movie>> = MutableLiveData()


    /*
    * this method sorts  the movies and arrange
    * according to its genres
    * */
    fun getMovieComposites(): List<CompositeItem>? {
        val moviesByGenre = movieList.sortedBy { it.genre }
        val genres = moviesByGenre.map { it.genre }.distinct()

        val compositeItem = mutableListOf<CompositeItem>()
        genres.let {
            genres.forEach { genre ->
                compositeItem.add(CompositeItem.withHeader(Header(genre)))
                val movies =
                    moviesByGenre.filter { it.genre == genre }.map { CompositeItem.withMovie(it) }
                compositeItem.addAll(movies)
            }
        }
        return compositeItem
    }

    fun setUpMovie(movie: Movie) {
        _movieLiveData.value = movie
    }

    /**
     * save list of movie [titles]
     * with a [key] with [PreferenceManager]
     * as a set
     * */
    fun saveList(key: String, titles: MutableList<String>, context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(key, titles.toHashSet())
        sharedPrefs.apply()
    }

    /**
     * add your favorite [movie]
     * into the list of favorites]
     * */
    fun addFavorite(movie: Movie, context: Context) {
        val favorites = getFavorites(context)
        favorites?.let {
            movie.isFavorite = true
            favorites.add(movie.title)
            saveList(KEY_FAVORITES, favorites, context)
            Log.d(TAG, "$favorites")
        }
    }


    /*
    * remove movie from list of favorites
    * */
    fun removeFavorite(movie: Movie, context: Context) {
        val favorites = getFavorites(context)
        favorites?.let {
            movie.isFavorite = false
            favorites.remove(movie.title)
            saveList(KEY_FAVORITES, favorites, context)
        }
    }

    /*
    * get favorites from using saved
    * titles in the sharedPreference
    *
    * */
    private fun getFavorites(context: Context): MutableList<String>? {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val content = sharedPrefs.all
        val movieList = mutableListOf<String>()
        for (movie in content) {
            val taskItem = ArrayList(movie.value as HashSet<String>)
            taskItem.forEach {
                movieList.add(it)
            }

        }
        return movieList
    }

    /*
    * setup the favorite movies to be observed
    * */
    fun getFavoriteMovies(context: Context): LiveData<List<Movie>>? {
        _favoriteLiveData.value = getFavorites(context)?.mapNotNull { getMovieByTitle(it) }
        Log.d(TAG, "${_favoriteLiveData.value}")
        return _favoriteLiveData

    }


    fun getMovieByTitle(title: String) = movieList.firstOrNull { it.title == title }


    //fun getMovieList(): MutableList<Movie> = movieList


    // movie data
    val movieList = mutableListOf(
        Movie(
            1,
            context.getString(R.string.artemis_title),
            context.getString(R.string.artemis_overview),
            context.getString(R.string.artemis_release_date),
            R.drawable.artemis_fowl,
            context.getString(R.string.artemis_genre)
        ),
        Movie(
            2,
            context.getString(R.string.astra_title),
            context.getString(R.string.astra_overview),
            context.getString(R.string.astra_release_date),
            R.drawable.ad_astra,
            context.getString(R.string.astra_genre)
        ),

        Movie(
            3,
            context.getString(R.string.sonic_title),
            context.getString(R.string.sonic_overview),
            context.getString(R.string.sonic_release_date),
            R.drawable.sonic,
            context.getString(R.string.sonic_genre)
        ),

        Movie(
            4,
            context.getString(R.string.da5bloods_title),
            context.getString(R.string.da5_bloods_overview),
            context.getString(R.string.da5blood_date),
            R.drawable.da_5bloods,
            context.getString(R.string.da5blood_genre)
        ),
        Movie(
            5,
            context.getString(R.string.joker_title),
            context.getString(R.string.joker_overview),
            context.getString(R.string.joker_release_date),
            R.drawable.joker,
            context.getString(R.string.joker_genre)
        ),
        Movie(
            6, context.getString(R.string.hunt_title),
            context.getString(R.string.hunt_overview),
            context.getString(R.string.hunt_release_date),
            R.drawable.the_hunt,
            context.getString(R.string.hunt_genre)
        ),
        Movie(
            7, context.getString(R.string.prey_title),
            context.getString(R.string.prey_overview),
            context.getString(R.string.prey_release_date),
            R.drawable.birds_of_prey,
            context.getString(R.string.prey_genre)
        ),
        Movie(
            8, context.getString(R.string.hobit_title),
            context.getString(R.string.hobit_overview),
            context.getString(R.string.hobit_release_date),
            R.drawable.the_hobbit,
            context.getString(R.string.hobit_genre)
        ),
        Movie(
            9, context.getString(R.string.britt_title),
            context.getString(R.string.britt_overview),
            "2019-01-25",
            R.drawable.britt_marie,
            context.getString(R.string.britt_genre)
        ),

        Movie(
            10,
            context.getString(R.string.avenger_title),
            context.getString(R.string.avenger_overview),
            context.getString(R.string.avenger_release_date),
            R.drawable.avengers,
            context.getString(R.string.avenger_genre)
        )

    )
}