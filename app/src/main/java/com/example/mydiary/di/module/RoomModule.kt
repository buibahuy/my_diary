package com.example.mydiary.di.module

import android.content.Context
import androidx.room.Room
import com.example.mydiary.database.DiaryDao
import com.example.mydiary.database.DiaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideDiaryDatabase(
        @ApplicationContext app: Context
    ) : DiaryDatabase = Room.databaseBuilder(
        app,
        DiaryDatabase::class.java,
        "diary_db"
    ).build()

    @Provides
    fun provideDiaryDao(db: DiaryDatabase): DiaryDao {
        return db.diaryDao()
    }
}