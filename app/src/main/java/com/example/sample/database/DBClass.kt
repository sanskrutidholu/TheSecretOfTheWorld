package com.example.sample.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBClass(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun insertEmpty() {
        val db = this.writableDatabase
        val cv = ContentValues()
        for (x in 1..10) {
            cv.put(KEY_ID, x)
            cv.put(FAVORITE_STATUS, 0)
            db.insert(TABLE_NAME, null, cv)
        }
    }

    fun insertIntoDatabase(item_image: String?, id: Int, fav_status: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        //        cv.put(ITEM_TITLE, item_title);
//        cv.put(ITEM_DESCRIPTION, item_description);
        cv.put(ITEM_IMAGE, item_image)
        cv.put(KEY_ID, id)
        cv.put(FAVORITE_STATUS, fav_status)
        db.insert(TABLE_NAME, null, cv)
    }

    fun readAllData(id: Int): Cursor {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id + ";"
        return db.rawQuery(sql, null, null)
    }

    fun removeFav(id: Int) {
        val db = this.writableDatabase
        val sql =
            "UPDATE " + TABLE_NAME + " SET " + FAVORITE_STATUS + "='0' WHERE " + KEY_ID + "=" + id + ";"
        db.execSQL(sql)
    }

    fun selectAllFav(): Cursor {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVORITE_STATUS + " ='1'"
        return db.rawQuery(sql, null, null)
    }

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "ListDB"
        private const val TABLE_NAME = "favoriteTable"
        var KEY_ID = "id"

        //    public static String ITEM_TITLE = "itemTitle";
        //    public static String ITEM_DESCRIPTION = "itemDescription";
        var ITEM_IMAGE = "itemImage"
        var FAVORITE_STATUS = "fStatus"
        var CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " TEXT, "
                + ITEM_IMAGE + " TEXT, "
                + FAVORITE_STATUS + " TEXT);")
    }
}
