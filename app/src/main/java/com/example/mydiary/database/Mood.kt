package com.example.mydiary.database

import androidx.annotation.StringRes
import com.example.mydiary.R

sealed class Mood(
    @StringRes val name: Int,
    val icon: Int,
) {
    object Default : Mood(name = R.string.mood_default_label, icon = R.drawable.ic_happy)
    object Happy : Mood(name = R.string.mood_happy_label, icon = R.drawable.ic_happy)
    object Laughing : Mood(name = R.string.mood_laughing_label, icon = R.drawable.ic_laughing)
    object Tired : Mood(name = R.string.mood_tired_label, icon = R.drawable.ic_tired)
    object Confused : Mood(name = R.string.mood_confused_label, icon = R.drawable.ic_confused)
    object HeartEye : Mood(name = R.string.mood_heart_eye_label, icon = R.drawable.ic_heart_eye)
    object Surprise : Mood(name = R.string.mood_surprise_label, icon = R.drawable.ic_surprise)
    object Sad : Mood(name = R.string.mood_sad_label, icon = R.drawable.ic_sad)
    object Angry : Mood(name = R.string.mood_angry_label, icon = R.drawable.ic_angry)
}
