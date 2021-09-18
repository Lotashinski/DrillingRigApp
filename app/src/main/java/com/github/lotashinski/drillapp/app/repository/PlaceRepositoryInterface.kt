package com.github.lotashinski.drillapp.app.repository

import com.github.lotashinski.drillapp.app.repository.model.Place

interface PlaceRepositoryInterface {
    fun getAll(): List<Place>
    fun save(place: Place): Place
    fun delete(place: Place): Place
}