package com.kazimasum.spdemo

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlin.coroutines.Continuation

public object MigrationManager {
    @JvmStatic
    lateinit var dataStore: DataStore<Preferences>

    @JvmStatic
    fun MigrateSp(context: Context) {
        dataStore = context.createDataStore(
            name = USER_PREFERENCES_NAME,
            migrations = listOf(
                SharedPreferencesMigration(
                    context,
                    USER_PREFERENCES_NAME
                )
            )
        )
    }
    @JvmStatic
      fun getUserValueFlow(
        username: String,
        continuation: Continuation<String>
    ):String? {
        var key: String? =null
        CoroutineScope(Dispatchers.IO).launch {
          key= async {
              getValueFlow(username)
          }.toString()
        }
        return key
    }

    /*
    @JvmStatic
    fun setUserValue(
         defaultValue: String
     ): Flow<String>{
         return dataStore.setValue()
     }*/

    suspend fun getValueFlow(key: String): String? =withContext(Dispatchers.IO) {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return@withContext preferences[dataStoreKey]
    }
    /*suspend fun <T> DataStore<Preferences>.getValueFlow(
        key: String
    ): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    *//*this.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[dataStoreKey]
            }*//*
    }*/
   /* @JvmStatic
    suspend fun getValueFlow(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    @JvmStatic
    suspend fun setValue(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value.toString()
        }
    }*/


    const val USER_PREFERENCES_NAME = "credentials"

    const val USER_NAME = "username"
    const val PASS_WORD = "password"

    val USERNAME = preferencesKey<String>(USER_NAME)
    val PASSWORD = preferencesKey<String>(PASS_WORD)
    //  val USERNAME = preferencesKey<String>(username)

}