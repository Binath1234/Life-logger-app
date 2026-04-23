package com.example.personallifelogger

import android.app.Application
import com.example.personallifelogger.data.AppDatabase
import com.example.personallifelogger.data.EntryRepository

/**
 * Application class. Acts as a simple service locator for the Room database
 * and the repository so that ViewModels can access them without DI frameworks.
 */
class LifeLoggerApp : Application() {
    // Lazily created Room database (single instance for the whole app).
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }

    // Repository wraps the DAO and exposes a clean API to the ViewModel layer.
    val repository: EntryRepository by lazy { EntryRepository(database.entryDao()) }
}
