package com.forcharc.mafia.modules

import com.forcharc.mafia.storage.Database
import com.forcharc.mafia.storage.PlayersSetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlayersModule {

    companion object {
        @Provides
        fun providePlayersSetDao(database: Database): PlayersSetDao {
            return database.playersSetDao()
        }
    }
}