package com.mbobiosio.currencyconverter.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class DataStoreRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    companion object {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }

    override fun getFirstLaunch(): Flow<Boolean> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            val firstLaunch = it[FIRST_LAUNCH] ?: true
            firstLaunch
        }
    }

    override suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit {
            it[FIRST_LAUNCH] = isFirstLaunch
        }
    }
}
