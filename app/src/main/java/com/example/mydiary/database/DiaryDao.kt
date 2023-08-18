package com.example.mydiary.database

import androidx.room.Dao

@Dao
interface DiaryDao {

    fun insertDiary(diary:Diary)
    fun getAllDiary() : List<Diary>

    fun getDiaryWithTime(time:Long) : List<Diary>

    fun getDiaryWithPhoto(photoLink:String) : Diary

    fun deleteDiary(diary: Diary)
}