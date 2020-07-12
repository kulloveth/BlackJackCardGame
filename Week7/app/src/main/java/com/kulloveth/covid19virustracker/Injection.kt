package com.kulloveth.covid19virustracker

import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.api.buildApiService
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import com.kulloveth.covid19virustracker.ui.NewsViewModelFactory
import com.kulloveth.covid19virustracker.ui.ViewModelFactory

object Injection {
     val apiService by lazy { buildApiService("https://corona-virus-stats.herokuapp.com/api/") }
    val newsApiService by lazy { buildApiService("https://newsapi.org/") }

     val db = CovidDatabase.invoke()
    // val remoteApi by lazy { Repository(apiService,db) }

     fun provideRepository():Repository{
        return Repository(db)
    }

    fun provideViewModelFactory():ViewModelProvider.Factory{
        return ViewModelFactory(provideRepository())
    }

    fun provideNewsViewModelFactory():ViewModelProvider.Factory{
        return NewsViewModelFactory(provideRepository())
    }
}