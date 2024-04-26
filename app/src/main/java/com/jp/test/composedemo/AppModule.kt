package com.jp.test.composedemo

import android.content.Context
import androidx.room.Room
import com.jp.test.composedemo.databse.CustomerDao
import com.jp.test.composedemo.databse.CustomerDatabase
import com.jp.test.composedemo.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/recipes/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}