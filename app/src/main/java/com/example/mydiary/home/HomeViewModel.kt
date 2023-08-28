package com.example.mydiary.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiary.database.Diary
import com.example.mydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {
    val listDiary = MutableStateFlow<List<Diary>>(emptyList())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val listDiaryUI = listDiary.combine(_searchText) { listDiary: List<Diary>, searchText: String ->
        if (searchText.isBlank()) {
            listDiary
        } else {
            listDiary.filter {
                it.doesMatchQuery(searchText)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listDiary.value
    )

    suspend fun getAllDiary() {
        diaryRepository.getAllDiary().collect { listDiary1 ->
            listDiary.update {
                listDiary1
            }
        }
    }

    fun onKeyWordChange(keyword: String) {
        _searchText.value = keyword
    }

    fun onSortNewest() {
        listDiary.update {
            it.sortedBy { diary ->
                diary.time
            }
        }
    }

    fun onSortOldest() {
        listDiary.update {
            it.sortedByDescending { diary ->
                diary.time
            }
        }
    }

    suspend fun getDiaryWithID(id: Long) = diaryRepository.getDiaryWithID(id)
    suspend fun deleteDiary(diary: Diary) {
        diaryRepository.deleteDiary(diary = diary)
    }
}