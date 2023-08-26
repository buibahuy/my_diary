package com.example.mydiary.diary.newdiary

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiary.database.Diary
import com.example.mydiary.mood.Mood
import com.example.mydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NewDiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var diaryState = MutableStateFlow(Diary())
        private set

    fun updateDiaryState(diary: Diary?) {
        viewModelScope.launch {
            diaryState.update {
                diary ?: Diary(time = Calendar.getInstance().time.time)
            }
        }
    }

    fun updateListImagesDiary(photos: List<String>) {
        viewModelScope.launch {
            diaryState.update {
                val listPhoto = it.photo + photos
                it.copy(photo = listPhoto)
            }
        }
    }

    fun removeListImagesDiary(index: Int) {
        viewModelScope.launch {
            diaryState.update {
                val updateListImage = it.photo.toMutableList() ?: mutableListOf()
                updateListImage.removeAt(index = index)
                it.copy(photo = updateListImage.distinct())
            }
        }
    }

    fun updateMood(mood: Mood) {
        viewModelScope.launch {
            diaryState.update {
                it.copy(mood = mood.icon)
            }
        }
    }

    fun updateTime(time: Long) {
        viewModelScope.launch {
            diaryState.update {
                it.copy(time = time)
            }
        }
    }

    fun updateTitle(title: String) {
        viewModelScope.launch {
            diaryState.update {
                it.copy(title = title)
            }
        }
    }

    fun updateContent(content: String) {
        viewModelScope.launch {
            diaryState.update {
                it.copy(content = content)
            }
        }
    }

    fun updateContentSecond(contentSecond: String) {
        viewModelScope.launch {
            diaryState.update {
                it.copy(contentSecond = contentSecond)
            }
        }
    }

    fun updateTag(tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryState.update {
                val updateListTag = it.listTag.toMutableList()
                updateListTag.add(tag)
                Log.d("Tag1234","update--$updateListTag")
                it.copy(listTag = updateListTag.toList())
            }
        }
    }

    fun removeTag(tagIndex: Int) {
        viewModelScope.launch {
            diaryState.update {
                val updateListTag = it.listTag.toMutableList()
                updateListTag.removeAt(tagIndex)
                Log.d("Tag1234","remove--$updateListTag---$tagIndex")
                it.copy(listTag = updateListTag.toList())
            }
        }
    }

    suspend fun insertDiary(diary: Diary) {
        diaryRepository.insertDiary(diary = diary)
    }

    fun updateDiary(diary: Diary) {
        diaryRepository.updateDiary(diary = diary)
    }


}