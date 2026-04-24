package com.example.personallifelogger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.personallifelogger.LifeLoggerApp
import com.example.personallifelogger.data.Entry
import com.example.personallifelogger.data.EntryRepository
import com.example.personallifelogger.sync.FirebaseSync
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EntryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EntryRepository =
        (application as LifeLoggerApp).repository

    val entries: StateFlow<List<Entry>> = repository.allEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addEntry(title: String, description: String, imageUri: String?, audioPath: String?) {
        viewModelScope.launch {
            val entry = Entry(
                title = title,
                description = description,
                imageUri = imageUri,
                audioPath = audioPath,
                date = System.currentTimeMillis()
            )
            repository.insert(entry)
        }
    }

    fun syncToCloud(onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            FirebaseSync.uploadEntries(entries.value, onResult)
        }
    }

    suspend fun getById(id: Long): Entry? = repository.getById(id)
}

class EntryViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}