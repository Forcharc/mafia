package com.forcharc.mafia.ui.viewModels

import androidx.lifecycle.ViewModel
import com.forcharc.mafia.repository.PlayersRepository
import com.forcharc.mafia.ui.screens.PlayerName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "PlayersViewModel"

@HiltViewModel
class PlayersViewModel @Inject constructor(private val repo: PlayersRepository) : ViewModel() {
    private val _currentPlayersFlow: MutableStateFlow<MutableList<PlayerName>> = MutableStateFlow(
        mutableListOf(
            PlayerName(0, "", false),
            PlayerName(1, "", false)
        )
    )
    val currentPlayersFlow: StateFlow<List<PlayerName>> = _currentPlayersFlow.asStateFlow()

    fun changePlayerName(id: Long, playerName: String) {
        _currentPlayersFlow.value = mutableListOf<PlayerName>().apply {
            addAll(_currentPlayersFlow.value)
            find { it.id == id }?.name = playerName
        }
    }

    fun addPlayerName(playerName: String) {
        _currentPlayersFlow.value = mutableListOf<PlayerName>().apply {
            addAll(_currentPlayersFlow.value)
            add(PlayerName(System.currentTimeMillis(), playerName, false))
        }
    }

    fun addPlayerName(playerName: PlayerName) {
        _currentPlayersFlow.value = mutableListOf<PlayerName>().apply {
            addAll(_currentPlayersFlow.value)
            add(playerName)
        }
    }

    fun deletePlayerName(id: Long) {
        _currentPlayersFlow.value = mutableListOf<PlayerName>().apply {
            addAll(_currentPlayersFlow.value)
            removeAll { it.id == id }
        }
    }
}