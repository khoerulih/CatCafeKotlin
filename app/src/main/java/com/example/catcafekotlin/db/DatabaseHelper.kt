package com.example.catcafekotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.catcafekotlin.db.DatabaseContract.FoodColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbcatcafe"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FOOD = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FoodColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FoodColumns.NAME} TEXT NOT NULL,"+
                " ${DatabaseContract.FoodColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.FoodColumns.PRICE} TEXT NOT NULL," +
                " ${DatabaseContract.FoodColumns.THUMBNAIL} BLOB)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FOOD)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}