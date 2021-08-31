package com.github.lotashinski.drillapp.repository.impl

import com.github.lotashinski.drillapp.repository.model.Place
import com.github.lotashinski.drillapp.repository.PlaceRepositoryInterface
import com.github.lotashinski.drillapp.repository.impl.db.PlaceDatabase
import com.github.lotashinski.drillapp.repository.impl.db.dao.PlaceDao


class PlaceRepository(
    private val placeDatabase: PlaceDatabase
) : PlaceRepositoryInterface {

    private var placeDao: PlaceDao? = null


    override fun getAll(): List<Place> = getPlaceDao().getAll()

    override fun save(place: Place): Place {
        getPlaceDao()
            .insertAll(place)
        return place
    }

    override fun delete(place: Place): Place {
        getPlaceDao()
            .delete(place)
        return place
    }


    private fun getPlaceDao(): PlaceDao {
        if (placeDao == null)
            placeDao = placeDatabase.placeDao()
        return placeDao as PlaceDao
    }

}