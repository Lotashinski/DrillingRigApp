package com.github.lotashinski.drillapp.repository

import com.github.lotashinski.drillapp.repository.model.Place
import com.github.lotashinski.drillapp.repository.model.Point

interface PointRepositoryInterface {
    fun save(point: Point): Point
    fun delete(point: Point): Point
    fun getByPlace(place: Place): List<Point>
}