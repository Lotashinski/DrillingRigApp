package com.github.lotashinski.drillapp.repository.impl.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.lotashinski.drillapp.repository.model.Point

@Dao
interface PointDao {
    @Query("SELECT pt.* FROM point pt INNER JOIN place pl ON pt.place_id = pl.id AND pl.id = :placeId")
    fun getByPlace(placeId: Long): List<Point>

    @Insert
    fun insertAll(vararg point: Point)

    @Delete
    fun delete(point: Point)
}