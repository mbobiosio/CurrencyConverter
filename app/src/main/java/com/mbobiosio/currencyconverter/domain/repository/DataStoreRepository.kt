package com.mbobiosio.currencyconverter.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
interface DataStoreRepository {

    fun getFirstLaunch(): Flow<Boolean>

    suspend fun saveFirstLaunch(isFirstLaunch: Boolean)
}
