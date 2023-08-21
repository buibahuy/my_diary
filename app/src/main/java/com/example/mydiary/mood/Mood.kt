package com.example.mydiary.mood

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.mydiary.R
import com.example.mydiary.ui.theme.BackgroundAngry
import com.example.mydiary.ui.theme.BackgroundConfused
import com.example.mydiary.ui.theme.BackgroundHappy
import com.example.mydiary.ui.theme.BackgroundHeartEye
import com.example.mydiary.ui.theme.BackgroundLaughing
import com.example.mydiary.ui.theme.BackgroundSad
import com.example.mydiary.ui.theme.BackgroundSurprise
import com.example.mydiary.ui.theme.BackgroundTired

sealed class Mood(
    @StringRes val name: Int,
    val icon: Int,
    val backgroundColor: Color = Color.White
) {
    object Default : Mood(name = R.string.mood_default_label, icon = R.drawable.ic_default_mood)
    object Happy : Mood(
        name = R.string.mood_happy_label,
        icon = R.drawable.ic_happy,
        backgroundColor = BackgroundHappy
    )

    object Laughing : Mood(
        name = R.string.mood_laughing_label,
        icon = R.drawable.ic_laughing,
        backgroundColor = BackgroundLaughing
    )

    object Tired : Mood(
        name = R.string.mood_tired_label,
        icon = R.drawable.ic_tired,
        backgroundColor = BackgroundTired
    )

    object Confused : Mood(
        name = R.string.mood_confused_label,
        icon = R.drawable.ic_confused,
        backgroundColor = BackgroundConfused
    )

    object HeartEye : Mood(
        name = R.string.mood_heart_eye_label,
        icon = R.drawable.ic_heart_eye,
        backgroundColor = BackgroundHeartEye
    )

    object Surprise : Mood(
        name = R.string.mood_surprise_label,
        icon = R.drawable.ic_surprise,
        backgroundColor = BackgroundSurprise
    )

    object Sad : Mood(
        name = R.string.mood_sad_label,
        icon = R.drawable.ic_sad,
        backgroundColor = BackgroundSad
    )

    object Angry : Mood(
        name = R.string.mood_angry_label,
        icon = R.drawable.ic_angry,
        backgroundColor = BackgroundAngry
    )

    companion object {
        fun getListMood(): List<Mood> {
            return listOf(
                Happy,
                Laughing,
                Tired,
                Confused,
                HeartEye,
                Surprise,
                Sad,
                Angry
            )
        }
    }

}


