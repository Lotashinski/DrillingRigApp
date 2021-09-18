package com.github.lotashinski.drillapp.app.service.impl

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.github.lotashinski.drillapp.app.service.IDatabaseService
import com.github.lotashinski.drillapp.app.repository.RepositoryBuilder
import com.github.lotashinski.drillapp.app.repository.impl.db.PlaceDatabase

class DatabaseService(
    private val context: Context
) : IDatabaseService {

    private var repositoryBuilder: RepositoryBuilder? = null
    private var placeDatabase: PlaceDatabase? = null

    override fun getRepositoryBuilder(): RepositoryBuilder {
        if (repositoryBuilder == null) {
            Log.i(this::class.simpleName, "create ${RepositoryBuilder::class} instant")
            val placeDatabase = getPlaceDatabase()
            repositoryBuilder = RepositoryBuilder(placeDatabase)
        }
        return repositoryBuilder as RepositoryBuilder
    }

    override fun close() {
        placeDatabase?.close()
    }


    private fun getPlaceDatabase(): PlaceDatabase {
        if (placeDatabase == null) {
            Log.i(this::class.simpleName, "create ${PlaceDatabase::class} instant")
            placeDatabase = Room
                .databaseBuilder(context, PlaceDatabase::class.java, "place-db")
                .build()
        }
        return placeDatabase as PlaceDatabase
    }

}