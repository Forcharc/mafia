package com.forcharc.mafia.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatabaseTypeConverters {
    @TypeConverter
    fun stringListToGsonString(stringList: List<String>): String {
        return Gson().toJson(stringList)
    }

    @TypeConverter
    fun stringListToGsonString(string: String): List<String> {
        return Gson().fromJson(string, object : TypeToken<List<String>>() {}.type)
    }
}