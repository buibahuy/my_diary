package com.example.mydiary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertDiary(diary: Diary)
    @Query("SELECT * FROM Diary")
     fun getAllDiary(): List<Diary>
//
//    fun getDiaryWithTime(time: Long): List<Diary>
//
//    fun getDiaryWithPhoto(photoLink: String): Diary
//
//    fun deleteDiary(diary: Diary)
}