package com.example.mydiary.repository

import com.example.mydiary.database.Diary
import com.example.mydiary.database.DiaryDao
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao
) {
    fun insertDiary(diary: Diary) = diaryDao.insertDiary(diary = diary)
    fun getAllDiary() = diaryDao.getAllDiary()
    fun getDiaryWithID(id: Long) = diaryDao.getDiaryWithID(id)

}