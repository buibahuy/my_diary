package com.example.mydiary.datetime

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.regex.Pattern

@SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
fun Long?.formatLongToDate(pattern: String ="EEEE,dd/MM/yyyy,HH:mm"): String {
 val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(this ?: 0)
}