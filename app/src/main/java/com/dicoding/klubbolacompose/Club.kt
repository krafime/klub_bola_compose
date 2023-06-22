package com.dicoding.klubbolacompose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Club(
    val nameClub: String,
    val fullNameClub: String,
    val photoClub: String,
    val descClub: String,
    var isFavorite: Boolean = false
) : Parcelable
