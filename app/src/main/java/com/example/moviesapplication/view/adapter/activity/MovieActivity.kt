package com.example.moviesapplication.view.adapter.activity
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.R
import com.example.moviesapplication.ViewModel.MovieViewModel
import com.example.moviesapplication.api.response.Movie
import com.example.moviesapplication.view.adapter.MoviesRecycler
import com.example.moviesapplication.view.adapter.listeners.DetailsMovieListener
import com.example.moviesapplication.view.adapter.util.ConnectivityReceiver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity(), DetailsMovieListener ,
    ConnectivityReceiver.ConnectivityReceiverListener{


        private val mainViewModel: MovieViewModel by viewModel()

        private val popularMoviesRecycler: MoviesRecycler by inject()
        private val topRatedMoviesRecycler: MoviesRecycler by inject()
        private val upcomingMoviesRecycler: MoviesRecycler by inject()

        private lateinit var popularMovies: RecyclerView
        private lateinit var topRatedMovies: RecyclerView
        private lateinit var upcomingMovies: RecyclerView

        private var mSnackBar: Snackbar? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_movies)


            setupViews()
           setupRecyclers()

            registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))



        }

        private fun setupViews() {
            popularMovies = findViewById(R.id.popular_movies)
            topRatedMovies = findViewById(R.id.top_rated_movies)
            upcomingMovies = findViewById(R.id.upcoming_movies)
        }

        private fun setupRecyclers() {
            popularMovies.adapter = popularMoviesRecycler
            popularMoviesRecycler.detailsMovieListener = this

            topRatedMovies.adapter = topRatedMoviesRecycler
            topRatedMoviesRecycler.detailsMovieListener = this

            upcomingMovies.adapter = upcomingMoviesRecycler
            upcomingMoviesRecycler.detailsMovieListener = this
        }

        @SuppressLint("ShowToast")
        private fun setupObservers() {
            mainViewModel.initializeAsync()


            mainViewModel.popularMovie.observe(this, Observer {
                popularMoviesRecycler.setList(it)
                popularMovies.visibility = View.VISIBLE

                sfl_character.stopShimmer()
                sfl_character.visibility = View.GONE
            })

            mainViewModel.ratedMovies.observe(this, Observer {
                topRatedMoviesRecycler.setList(it)
                topRatedMovies.visibility = View.VISIBLE

                sfl_character3.stopShimmer()
                sfl_character3.visibility = View.GONE
            })

            mainViewModel.upcomingMovies.observe(this, Observer {
                upcomingMoviesRecycler.setList(it)
                upcomingMovies.visibility = View.VISIBLE


                sfl_character2.stopShimmer()
                sfl_character2.visibility = View.GONE
            })

            mainViewModel.movieError.observe(this, Observer {
                Toast.makeText(this@MovieActivity, "Ops.. Você está Offline", Toast.LENGTH_LONG)
                    .show()
            })
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private fun showMovieDetails(movie: Movie, imageView: ImageView) {
            val intent = Intent(this, MovieDetailsActivity::class.java)

            val elementShared: View = imageView
            elementShared.transitionName = "imagePoster"
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MovieActivity,
                elementShared, "imagePoster"
            )

            intent.putExtra(MovieDetailsActivity.MOVIE_BACKDROP, movie.backdropPath)
            intent.putExtra(MovieDetailsActivity.MOVIE_POSTER, movie.posterPath)
            intent.putExtra(MovieDetailsActivity.MOVIE_TITLE, movie.title)
            intent.putExtra(MovieDetailsActivity.MOVIE_RATING, movie.rating)
            intent.putExtra(MovieDetailsActivity.MOVIE_RELEASE_DATE, movie.releaseDate)
            intent.putExtra(MovieDetailsActivity.MOVIE_OVERVIEW, movie.overview)
            startActivity(intent, options.toBundle())
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun detailsMovieOnClick(movie: Movie, position: Int, imageView: ImageView) {
            showMovieDetails(movie, imageView)
        }


        private fun showConnection(isConnected: Boolean){

            if (isConnected) {
                mSnackBar?.dismiss()
                setupObservers()
            } else {
                val messageToUser = "Você está offline agora!"

                mSnackBar = Snackbar.make(
                    findViewById(R.id.layout_constraint),
                    messageToUser,
                    Snackbar.LENGTH_LONG
                )
                mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
                mSnackBar?.show()
                Toast.makeText(this@MovieActivity, "Você está offline agora!", Toast.LENGTH_LONG)
                    .show()
            }

        }

        override fun onNetworkConnectionChanged(isConnected: Boolean) {
            showConnection(isConnected)
        }

        override fun onResume() {
            super.onResume()
            ConnectivityReceiver.connectivityReceiverListener = this
        }


    }