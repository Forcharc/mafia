package com.forcharc.mafia.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_set")
data class PlayersSetRoomModel(
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "player_names")
    val playerNames: List<String>,
    @ColumnInfo(name = "date_in_millis")
    var dateInMillis: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
) {
}
