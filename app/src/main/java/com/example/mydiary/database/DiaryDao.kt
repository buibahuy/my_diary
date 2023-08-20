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

    @Query("SELECT * FROM Diary WHERE id =:id")
    fun getDiaryWithID(id: Long): Diary

//
//    fun getDiaryWithTime(time: Long): List<Diary>
//
//    fun getDiaryWithPhoto(photoLink: String): Diary
//
//    fun deleteDiary(diary: Diary)
}