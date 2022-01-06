package com.example.catcafekotlin.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    var id: Int = 0,
    var name: String? = null,
    var description: String? = null,
    var price: String? = null,
    var thumbnail: String? = null,
) : Parcelable
