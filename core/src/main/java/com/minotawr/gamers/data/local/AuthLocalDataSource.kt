package com.minotawr.gamers.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthLocalDataSource (private val dataStore: DataStore<Preferences>) {

    companion object {
        const val DATA_STORE = "auth"
    }

    private val key = stringPreferencesKey("session")

    fun getAuth(): Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[key]
        }

    suspend fun saveAuth(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[key] = sessionId
        }
    }

    suspend fun logout() {
        dataStore.edit { it.clear() }
    }
}