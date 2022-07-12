package com.mbobiosio.currencyconverter.data.local

import androidx.room.withTransaction
import javax.inject.Singleton

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Singleton
class DatabaseTransactionProvider(
    private val db: AppDatabase
) {
    suspend fun <T> runAsTransaction(block: suspend () -> T): T {
        return db.withTransaction(block)
    }
}
