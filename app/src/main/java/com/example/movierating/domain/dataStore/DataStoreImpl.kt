package com.example.movierating.domain.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
        Log.e("TAG", "putString: $preferencesKey")
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            Log.e("TAG", "getString: $preferencesKey - ${preferences[preferencesKey]}")
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun clearDataStorage() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
        Log.e("TAG", "clearDataStorage: clearDataStorage")
    }

    override suspend fun removeInt(key: String) {
        context.dataStore.edit { preferences ->
            val preferencesKey = intPreferencesKey(key)
            preferences.remove(preferencesKey)
        }
    }

    override suspend fun removeString(key: String) {
        context.dataStore.edit { preferences ->
            val preferencesKey = stringPreferencesKey(key)
            preferences.remove(preferencesKey)
            Log.e("TAG", "removeString: $preferencesKey")
        }
    }

    private companion object {
        private const val PREFERENCES_NAME = "my_preferences"
    }
}