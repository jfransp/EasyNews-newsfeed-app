package com.example.newsfeed.util

import androidx.room.TypeConverter
import com.example.newsfeed.data.models.Source

class DataBaseTypeConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name, name)
    }
}
