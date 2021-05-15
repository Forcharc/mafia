package com.forcharc.mafia.modules

import android.content.Context
import androidx.room.Room
import com.forcharc.mafia.storage.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    companion object {
        @Provides
        fun bindDatabase(@ApplicationContext context: Context): Database {
            return Room.databaseBuilder(
                context,
                Database::class.java, "mafia-database"
            ).build()
        }
    }
}