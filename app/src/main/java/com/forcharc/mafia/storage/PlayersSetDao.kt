package com.forcharc.mafia.storage

import androidx.room.*
import com.forcharc.mafia.models.room.PlayersSetRoomModel

@Dao
interface PlayersSetDao {
    @Query("SELECT * FROM players_set ")
    fun getAll(): List<PlayersSetRoomModel>

    @Query("SELECT * FROM players_set WHERE id = :id LIMIT 1")
    fun getById(id: Int): PlayersSetRoomModel

    @Query("DELETE FROM players_set WHERE id = :id")
    fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlayersSet(playersSetRoomModel: PlayersSetRoomModel)

    @Update
    fun updatePlayersSet(playersSetRoomModel: PlayersSetRoomModel): Int
}