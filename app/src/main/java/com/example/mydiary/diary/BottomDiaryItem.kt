package com.example.mydiary.diary

import com.example.mydiary.R

sealed class BottomDiaryItem(
    val text: String,
    val icon: Int,
    val route: String
) {
    object BackGround :
        BottomDiaryItem(
            text = "Background",
            icon = R.drawable.ic_diary_back_ground,
            route = "diary_back_ground"
        )

    object TextFont : BottomDiaryItem(
        text = "TextFont",
        icon = R.drawable.ic_diary_text_font,
        route = "diary_text_font"
    )

    object Image :
        BottomDiaryItem(text = "Image", icon = R.drawable.ic_diary_image, route = "diary_image")

    object Tag :
        BottomDiaryItem(text = "Tag", icon = R.drawable.ic_diary_tag, route = "diary_tag")
}
