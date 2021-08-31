package com.github.lotashinski.drillapp.app

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.github.lotashinski.drillapp.repository.RepositoryBuilder
import com.github.lotashinski.drillapp.repository.impl.db.PlaceDatabase


class App : Application() {

    private var repositoryBuilder: RepositoryBuilder? = null
    private var placeDatabase: PlaceDatabase? = null


    private fun getPlaceDatabase(): PlaceDatabase {
        if (placeDatabase == null) {
            Log.i(this::class.toString(), "create ${PlaceDatabase::class} instant")
            placeDatabase = Room
                .databaseBuilder(this, PlaceDatabase::class.java, "place-db")
                .build()
        }
        return placeDatabase as PlaceDatabase
    }

    fun getRepositoryBuilder(): RepositoryBuilder {
        if (repositoryBuilder == null) {
            Log.i(this::class.toString(), "create ${RepositoryBuilder::class} instant")
            val placeDatabase = getPlaceDatabase()
            repositoryBuilder = RepositoryBuilder(placeDatabase)
        }
        return repositoryBuilder as RepositoryBuilder
    }

}