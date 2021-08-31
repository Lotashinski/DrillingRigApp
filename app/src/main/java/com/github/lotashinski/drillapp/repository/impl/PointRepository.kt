package com.github.lotashinski.drillapp.repository.impl

import com.github.lotashinski.drillapp.repository.model.Place
import com.github.lotashinski.drillapp.repository.model.Point
import com.github.lotashinski.drillapp.repository.PointRepositoryInterface
import com.github.lotashinski.drillapp.repository.impl.db.PlaceDatabase
import com.github.lotashinski.drillapp.repository.impl.db.dao.PointDao

class PointRepository(
    private val placeDatabase: PlaceDatabase
) : PointRepositoryInterface {

    private var pointDao: PointDao? = null


    override fun getByPlace(place: Place): List<Point> {
        return getPointDao()
            .getByPlace(place.id as Long)
    }

    override fun save(point: Point): Point {
        getPointDao()
            .insertAll(point)
        return point
    }

    override fun delete(point: Point): Point {
        getPointDao()
            .delete(point)
        return point
    }


    private fun getPointDao(): PointDao {
        if (pointDao == null)
            pointDao = placeDatabase.pointDao()
        return pointDao as PointDao
    }

}