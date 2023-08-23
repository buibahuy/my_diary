package com.example.mydiary.database

import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Diary::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao():DiaryDao
}