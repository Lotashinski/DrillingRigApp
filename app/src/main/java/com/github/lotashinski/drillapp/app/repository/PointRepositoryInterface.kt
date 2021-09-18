package com.github.lotashinski.drillapp.app.repository

import com.github.lotashinski.drillapp.app.repository.model.Place
import com.github.lotashinski.drillapp.app.repository.model.Point

interface PointRepositoryInterface {
    fun save(point: Point): Point
    fun delete(point: Point): Point
    fun getByPlace(place: Place): List<Point>
}