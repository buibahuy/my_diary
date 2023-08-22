package com.example.mydiary.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(diary: Diary)

    @Query("SELECT * FROM Diary")
    fun getAllDiary(): Flow<List<Diary>>

    @Query("SELECT * FROM Diary WHERE id =:id")
    fun getDiaryWithID(id: Long): Diary

    @Delete
    fun deleteDiary(diary: Diary)

    @Update
    fun updateDiary(diary: Diary)

//
//    fun getDiaryWithTime(time: Long): List<Diary>
//
//    fun getDiaryWithPhoto(photoLink: String): Diary
//
//    fun deleteDiary(diary: Diary)
}