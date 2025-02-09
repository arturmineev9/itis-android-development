package com.example.hw6_room.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_2_3 : Migration(2, 3) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE memes_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                url TEXT NOT NULL,
                source TEXT NOT NULL,
                createdAt INTEGER NOT NULL,
                isFavorite INTEGER NOT NULL,
                userId INTEGER NOT NULL
            )
        """)

        db.execSQL("""
            INSERT INTO memes_new (id, title, description, url, source, createdAt, isFavorite, userId)
            SELECT id, title, description, url, source, createdAt, isFavorite, userId FROM memes
        """)

        db.execSQL("DROP TABLE memes")


        db.execSQL("ALTER TABLE memes_new RENAME TO memes")
    }
}