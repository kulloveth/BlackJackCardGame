package com.kulloveth.moviesapp.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.ActivityMainBinding
import com.kulloveth.moviesapp.movies.MoviesFragment
import com.kulloveth.moviesapp.signin.SignInActivity

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup bottomNavigation with Navigation Component
        val bottomNavView: BottomNavigationView = binding.navView;
        val navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )
        NavigationUI.setupWithNavController(bottomNavView, navController)


    }













}
