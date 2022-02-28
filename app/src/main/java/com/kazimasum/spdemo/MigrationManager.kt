package com.kazimasum.spdemo

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MigrationManager(context: Context) {
    val dataStore = context.createDataStore(
        name = USER_PREFERENCES_NAME,
        migrations = listOf(
            SharedPreferencesMigration(context,
            USER_PREFERENCES_NAME
        )
        )
    )
    fun getUserValueFlow(
        defaultValue: String
    ): Flow<String>{
        return dataStore.getValueFlow(USERNAME,defaultValue)
    }
    fun <T> DataStore<Preferences>.getValueFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: defaultValue
            }
    }
    suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
        this.edit { preferences ->
            preferences[key] = value
        }
    }
    companion object{
        const val USER_PREFERENCES_NAME = "credentials"

        const val USER_NAME = "username"
        const val PASS_WORD = "password"

        val USERNAME = preferencesKey<String>(USER_NAME)
        val PASSWORD = preferencesKey<String>(PASS_WORD)
      //  val USERNAME = preferencesKey<String>(username)
    }
}