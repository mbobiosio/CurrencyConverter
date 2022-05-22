package com.mbobiosio.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.mbobiosio.currencyconverter.data.local.AppDatabase
import com.mbobiosio.currencyconverter.data.local.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "converter_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(appDatabase: AppDatabase): CurrencyDao = appDatabase.currencyDao
}
