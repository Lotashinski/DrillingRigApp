package com.github.lotashinski.drillapp.app.repository.impl.db.dao

import androidx.room.*
import com.github.lotashinski.drillapp.app.repository.model.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getAll(): List<Place>

    @Query("SELECT * FROM place WHERE id = :id")
    fun get(id: Long): Place?

    @Insert
    fun insertAll(vararg places: Place)

    @Delete
    fun delete(place: Place)
}