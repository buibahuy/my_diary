package com.example.mydiary.database

import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase

@Database(
    entities = [Diary::class],
    version = 1
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao():DiaryDao
}