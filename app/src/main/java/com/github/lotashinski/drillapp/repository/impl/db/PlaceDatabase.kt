package com.github.lotashinski.drillapp.repository.impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.lotashinski.drillapp.repository.impl.db.dao.PlaceDao
import com.github.lotashinski.drillapp.repository.model.Place
import com.github.lotashinski.drillapp.repository.impl.db.dao.PointDao
import com.github.lotashinski.drillapp.repository.model.Point

@Database(
    entities = [Place::class, Point::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    value = [JavaDataToStringConverter::class]
)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun pointDao(): PointDao
}