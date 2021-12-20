package com.abhishek.radiustask.util

import androidx.room.TypeConverter
import com.abhishek.radiustask.pojos.Exclusions
import com.abhishek.radiustask.pojos.Options
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<Options>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<Options>::class.java).toList()

    @TypeConverter
    fun listToJson2(value: List<Exclusions>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList2(value: String) =
        Gson().fromJson(value, Array<Exclusions>::class.java).toList()
}