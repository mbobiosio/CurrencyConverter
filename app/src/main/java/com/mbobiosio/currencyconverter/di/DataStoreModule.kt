package com.mbobiosio.currencyconverter.di

import com.mbobiosio.currencyconverter.repository.DataStoreRepoImpl
import com.mbobiosio.currencyconverter.repository.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindDataStore(dataStoreRepoImpl: DataStoreRepoImpl): DataStoreRepository
}
