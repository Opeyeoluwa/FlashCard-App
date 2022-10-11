package com.example.flashcard

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flashcard.Flashcard
import com.example.flashcard.FlashcardDao

@Database(entities = [Flashcard::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
}
