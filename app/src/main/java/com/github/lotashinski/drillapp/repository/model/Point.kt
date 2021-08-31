package com.github.lotashinski.drillapp.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "point",
    foreignKeys = [
        ForeignKey(
            entity = Place::class,
            parentColumns = ["id"],
            childColumns = ["place_id"],
            onDelete = CASCADE
        )
    ]
)
class Point(
    var value: Float,
    @ColumnInfo(name = "place_id") var placeId: Long
) {
    @PrimaryKey
    var id: Long? = null
}