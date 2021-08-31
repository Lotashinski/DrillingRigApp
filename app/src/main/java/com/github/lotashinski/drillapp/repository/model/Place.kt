package com.github.lotashinski.drillapp.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "place"
)
class Place(
    var title: String,
    var description: String?,
    var createdAt: Date
) {
    @PrimaryKey
    var id: Long? = null

    constructor(title: String, description: String?)
            : this(title, description, Date(System.currentTimeMillis()))
}