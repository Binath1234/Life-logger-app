package com.example.personallifelogger.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository abstracts the data source from ViewModels.
 */
class EntryRepository(private val dao: EntryDao) {
    val allEntries: Flow<List<Entry>> = dao.getAllEntries()
    suspend fun insert(entry: Entry): Long = dao.insert(entry)
    suspend fun getById(id: Long): Entry? = dao.getById(id)
}
