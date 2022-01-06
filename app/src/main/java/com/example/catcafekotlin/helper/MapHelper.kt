package com.example.catcafekotlin.helper

import android.database.Cursor
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.catcafekotlin.db.DatabaseContract
import com.example.catcafekotlin.entity.Food

object MapHelper {

    fun mapCursorToArrayList(foodsCursor: Cursor?): ArrayList<Food> {
        val foodsList = ArrayList<Food>()

        foodsCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FoodColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FoodColumns.NAME))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.FoodColumns.DESCRIPTION))
                val price = getString(getColumnIndexOrThrow(DatabaseContract.FoodColumns.PRICE))
                val thumbnail = getString(getColumnIndexOrThrow(DatabaseContract.FoodColumns.THUMBNAIL))
                foodsList.add(Food(id, name, description, price, thumbnail))
            }
        }
        return foodsList
    }
}