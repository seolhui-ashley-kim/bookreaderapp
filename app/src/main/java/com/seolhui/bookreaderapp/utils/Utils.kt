package com.seolhui.bookreaderapp.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp

fun formatDate(timestamp: Timestamp): String {
    val date = DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString().split(",")[0]  //Mar 12, 2023
    return date
}