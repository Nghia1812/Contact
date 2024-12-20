package com.example.contactappcompose.hiltmodule

import android.content.Context
import com.example.contactappcompose.datasource.ContactDao
import com.example.contactappcompose.datasource.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideContactDatabase(@ApplicationContext context: Context): ContactDatabase{
        return ContactDatabase.getDatabase(context)
    }

    @Provides
    fun provideContactDao(database: ContactDatabase): ContactDao {
        return database.contactDao()
    }
}