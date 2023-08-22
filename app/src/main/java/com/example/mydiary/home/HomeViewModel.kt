package com.example.mydiary.home

import androidx.lifecycle.ViewModel
import com.example.mydiary.database.Diary
import com.example.mydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {
    val listDiary = MutableStateFlow<List<Diary>>(emptyList())
    suspend fun getAllDiary() {
        diaryRepository.getAllDiary().collect { listDiary1 ->
            listDiary.update {
                listDiary1
            }
        }
    }

    suspend fun getDiaryWithID(id: Long) = diaryRepository.getDiaryWithID(id)
    suspend fun deleteDiary(diary: Diary) {
        diaryRepository.deleteDiary(diary = diary)
    }
}