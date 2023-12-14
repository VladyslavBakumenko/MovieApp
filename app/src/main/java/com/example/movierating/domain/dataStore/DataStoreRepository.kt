package com.example.movierating.domain.dataStore

interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?
    suspend fun clearDataStorage()
    suspend fun removeString(key: String)
    suspend fun removeInt(key: String)
}