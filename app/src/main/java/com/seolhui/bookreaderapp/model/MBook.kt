package com.seolhui.bookreaderapp.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class MBook(

    @Exclude var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    val notes: String? = null,
    val photoUrl: String? = null,
    val categories: String? = null,
    val publishedDate: String? = null,
    val rating: Double? = null,
    val description: String? = null,
    val pageCount: String? = null,
    val startedReading: Timestamp? = null,
    val finishedReading: Timestamp? = null,
    val userId: String? = null,
    val googleBookId: String? = null,

    )
