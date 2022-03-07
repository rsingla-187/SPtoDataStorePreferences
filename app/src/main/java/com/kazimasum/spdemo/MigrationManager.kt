package com.kazimasum.spdemo

//import androidx.datastore.DataStore
import android.content.Context
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

object MigrationManager {

    val USER_PREFERENCES_NAME = "credentials"

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME,
        produceMigrations = { context ->
            // Since we're migrating from SharedPreferences, add a migration based on the
            // SharedPreferences name
            listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
        }
    )

    fun getStringPreferenceValueForKey(context: Context, key: String, callback: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.collect { preferences ->
                    callback(preferences[stringPreferencesKey(key)])
                }
        }
    }

    @JvmStatic
    fun setUserValue(
        context: Context,
        username: String,
        value: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            return@launch setValue(context, username, value)
        }
    }

    @JvmStatic
    private suspend fun setValue(context: Context, key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    const val USER_NAME = "username"
    const val PASS_WORD = "password"
}
