package com.jp.test.composedemo

import android.content.Context
import androidx.room.Room
import com.jp.test.composedemo.databse.CustomerDao
import com.jp.test.composedemo.databse.CustomerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideQuoteDatabase(@ApplicationContext context: Context): CustomerDatabase {
        return Room.databaseBuilder(context, CustomerDatabase::class.java, "customerDatabase")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(quoteDatabase: CustomerDatabase): CustomerDao {
        return quoteDatabase.customerDao()
    }
}