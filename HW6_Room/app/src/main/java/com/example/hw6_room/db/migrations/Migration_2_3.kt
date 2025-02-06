package com.example.hw6_room.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_2_3 : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE memes ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
        database.execSQL("ALTER TABLE memes ADD COLUMN source TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE memes ADD COLUMN title TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE memes ADD COLUMN tags TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE memes ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
    }
}