package com.example.mywork120.data.local.convertor

import androidx.room.TypeConverter
import java.time.LocalDate

class convert {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDate(data: String?): LocalDate? {
        return data?.let {LocalDate.parse(it)}
    }
    @TypeConverter
    fun fromChar(date: Char?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toChar(data: String?): Char? {
        return data?.let {it[0]}
    }
}