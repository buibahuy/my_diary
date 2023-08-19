package com.example.mydiary.home

import androidx.lifecycle.ViewModel
import com.example.mydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    suspend fun getAllDiary() = diaryRepository.getAllDiary()
}