package com.github.lotashinski.drillapp.repository.impl.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object JavaDataToStringConverter {

    @JvmStatic
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)

    @TypeConverter
    @JvmStatic
    fun dateToString(value: Date): String = formatter.format(value)

    @TypeConverter
    @JvmStatic
    fun stringToDate(value: String): Date = formatter.parse(value) as Date

}