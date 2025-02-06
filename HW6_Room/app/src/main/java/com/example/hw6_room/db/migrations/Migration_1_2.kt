package com.example.hw6_room.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_1_2 : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS memes (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, userId INTEGER NOT NULL, name TEXT NOT NULL, url TEXT NOT NULL, description TEXT, timestamp INTEGER NOT NULL)")
    }
}