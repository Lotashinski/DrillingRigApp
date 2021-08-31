package com.github.lotashinski.drillapp.repository

import android.util.Log
import com.github.lotashinski.drillapp.repository.impl.PlaceRepository
import com.github.lotashinski.drillapp.repository.impl.PointRepository
import com.github.lotashinski.drillapp.repository.impl.db.PlaceDatabase


class RepositoryBuilder(
    private val placeDatabase: PlaceDatabase
) {

    private var placeRepository: PlaceRepositoryInterface? = null
    private var pointRepository: PointRepositoryInterface? = null

    fun getPlaceRepository(): PlaceRepositoryInterface {
        if (placeRepository == null) {
            Log.i(this::class.toString(), "create ${PlaceRepository::class} instant")
            placeRepository = PlaceRepository(placeDatabase)
        }
        return placeRepository as PlaceRepositoryInterface
    }

    fun getPointRepository(): PointRepositoryInterface {
        if (pointRepository == null) {
            Log.i(this::class.toString(), "create ${PointRepository::class} instant")
            pointRepository = PointRepository(placeDatabase)
        }
        return pointRepository as PointRepositoryInterface
    }

}