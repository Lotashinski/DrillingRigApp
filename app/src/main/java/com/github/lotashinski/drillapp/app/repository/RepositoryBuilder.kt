package com.github.lotashinski.drillapp.app.repository

import android.util.Log
import com.github.lotashinski.drillapp.app.repository.impl.PlaceRepository
import com.github.lotashinski.drillapp.app.repository.impl.PointRepository
import com.github.lotashinski.drillapp.app.repository.impl.db.PlaceDatabase


class RepositoryBuilder(
    private val placeDatabase: PlaceDatabase
) {

    private var placeRepository: PlaceRepositoryInterface? = null
    private var pointRepository: PointRepositoryInterface? = null

    fun getPlaceRepository(): PlaceRepositoryInterface {
        if (placeRepository == null) {
            Log.i(this::class.simpleName, "create ${PlaceRepository::class} instant")
            placeRepository = PlaceRepository(placeDatabase)
        }
        return placeRepository as PlaceRepositoryInterface
    }

    fun getPointRepository(): PointRepositoryInterface {
        if (pointRepository == null) {
            Log.i(this::class.simpleName, "create ${PointRepository::class} instant")
            pointRepository = PointRepository(placeDatabase)
        }
        return pointRepository as PointRepositoryInterface
    }

}