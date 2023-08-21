package com.example.mydiary.diary.overviewdiary

import androidx.lifecycle.ViewModel
import com.example.mydiary.database.Diary
import com.example.mydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverViewDiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel(){
    suspend fun deleteDiary(diary: Diary) {
        diaryRepository.deleteDiary(diary = diary)
    }
}