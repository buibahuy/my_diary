package com.example.mydiary.util

import androidx.compose.ui.text.font.FontFamily
import com.example.mydiary.ui.theme.Akayate
import com.example.mydiary.ui.theme.Alison
import com.example.mydiary.ui.theme.Vibur
import com.example.mydiary.ui.theme.WindSongFont

object FontConverter {
    const val FontWindSongFontString = "WindSongFont"
    const val AkayateFontString = "Akayate"
    const val AlisonFontString = "Alison"
    const val ViburFontString = "Vibur"

    fun getFromDatabase(font: String?): FontFamily {
        return when (font) {
            FontWindSongFontString -> WindSongFont
            AkayateFontString -> Akayate
            AlisonFontString -> Alison
            ViburFontString -> Vibur
            else -> {
                FontFamily.Default
            }
        }
    }

    fun setToDatabase(fontFamily: FontFamily) : String {
        return when (fontFamily) {
            WindSongFont -> FontWindSongFontString
            Akayate -> AkayateFontString
            Alison -> AlisonFontString
            Vibur -> ViburFontString
            else -> {
                FontFamily.Default.toString()
            }
        }
    }
}