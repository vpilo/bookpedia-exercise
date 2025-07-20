package com.plcoding.bookpedia.data.book.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StringListTypeConverter {

    @TypeConverter
    fun stringToList(value: String): List<String> =
        Json.decodeFromString(value)

    @TypeConverter
    fun listToString(list: List<String>): String =
        Json.encodeToString(list)

}
