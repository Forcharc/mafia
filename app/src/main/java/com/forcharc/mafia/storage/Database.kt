package com.forcharc.mafia.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.forcharc.mafia.models.room.PlayersSetRoomModel

@Database(entities = [PlayersSetRoomModel::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun playersSetDao(): PlayersSetDao
}