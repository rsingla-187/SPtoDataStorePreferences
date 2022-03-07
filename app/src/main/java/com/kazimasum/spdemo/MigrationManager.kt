package com.kazimasum.spdemo

//import androidx.datastore.DataStore
import android.content.Context
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlin.coroutines.Continuation

public object MigrationManager {
    private const val USER_PREFERENCES_NAME = "credentials"

 private   val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME,
        produceMigrations = { context ->
            // Since we're migrating from SharedPreferences, add a migration based on the
            // SharedPreferences name
            listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
        }
    )


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


    @JvmStatic
    fun setUserValue(
        username: String,
        value: String,
        continuation: Continuation<Unit>
    )  {
        CoroutineScope(Dispatchers.IO).launch{
          return@launch setValue(username, value)
        }
     }
    suspend fun getValueFlow(key: String): String? =withContext(Dispatchers.IO) {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return@withContext preferences[dataStoreKey]
    }
    @JvmStatic
    suspend fun setValue(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
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


   // const val USER_PREFERENCES_NAME = "credentials"

    const val USER_NAME = "username"
    const val PASS_WORD = "password"
object PreferencesKeys {
}
}