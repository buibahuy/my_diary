package com.example.mydiary.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiary.database.Diary
import com.example.mydiary.database.DiaryDao
import com.example.mydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewDiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {
   suspend fun insertDiary(diary: Diary) {
        diaryRepository.insertDiary(diary = diary)
    }
}