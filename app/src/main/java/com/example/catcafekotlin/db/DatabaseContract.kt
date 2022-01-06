package com.example.catcafekotlin.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FoodColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "food"
            const val _ID = "_id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val PRICE = "price"
            const val THUMBNAIL = "thumbnail"
        }
    }
}