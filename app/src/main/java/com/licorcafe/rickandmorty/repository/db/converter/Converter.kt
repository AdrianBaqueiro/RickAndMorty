package com.licorcafe.rickandmorty.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converter {
    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        return Gson().toJson(list)
    }
}