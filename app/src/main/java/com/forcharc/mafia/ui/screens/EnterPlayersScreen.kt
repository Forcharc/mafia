package com.forcharc.mafia.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.forcharc.mafia.NavDests
import com.forcharc.mafia.R
import com.forcharc.mafia.ui.anim.EnterExitAnimation
import com.forcharc.mafia.ui.viewModels.PlayersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PlayerName(val id: Long, val name: String, var isAnimationShown: Boolean = false)

@ExperimentalAnimationApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EnterPlayersScreen(
    navController: NavHostController? = null,
    exit: ((onExit: () -> Unit) -> Unit)? = null,
    viewModel: PlayersViewModel? = null
) {
    val playerNames =
        remember {
            mutableStateListOf(
                PlayerName(0, ""),
                PlayerName(1, ""),
                PlayerName(2, "")
            )
        }

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            EnterPlayersHeader(exit, navController)
        }
        items(playerNames.size, key = { index -> playerNames[index].id }) { position ->
            EnterExitAnimation(!playerNames[position].isAnimationShown) { exit ->
                UserNameField(playerNames, position, exit)
            }
        }
        item {
            Button(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                onClick = {
                    val playerName = PlayerName(System.currentTimeMillis(), "")
                    playerNames.add(playerName)
                    coroutineScope.launch {
                        delay(100)
                        playerName.isAnimationShown = true
                    }
                    coroutineScope.launch {
                        delay(AnimationConstants.DefaultDurationMillis.toLong())
                        listState.animateScrollBy(300f, tween())
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    }

}

@Composable
private fun EnterPlayersHeader(
    exit: ((onExit: () -> Unit) -> Unit)?,
    navController: NavHostController?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.enter_players),
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                exit?.invoke { navController?.navigate(NavDests.getRolesDestination) }
            },
        ) {
            Text(text = stringResource(R.string.proceed))
        }
    }
}


@ExperimentalAnimationApi
@Composable
private fun UserNameField(
    nameList: SnapshotStateList<PlayerName>,
    position: Int,
    exit: (onExit: () -> Unit) -> Unit
) {
    val delButtonPadding = remember { 16.dp }
    val delButtonWidth = remember { 50.dp }
    val delButtonWidthWithPadding = remember { delButtonPadding + delButtonWidth }

    val rowWidth: MutableState<Dp> = remember { mutableStateOf(1000.dp) }

    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = delButtonWidthWithPadding)
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                LocalDensity.current.apply {
                    rowWidth.value = layoutCoordinates.size.width.toDp()
                }
            },
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            modifier = Modifier.width(
                max(rowWidth.value - delButtonWidthWithPadding, 0.dp)
            ),
            value = nameList[position].name,
            onValueChange = { name ->
                nameList[position] = PlayerName(nameList[position].id, name)
            },
            label = {
                Crossfade(targetState = position + 1) {
                    Text(
                        text = stringResource(
                            R.string.player_number,
                            (it).toString()
                        )
                    )
                }
            }
        )
        AnimatedVisibility(visible = nameList.size > 3) {
            DeleteUserButton(nameList, position, exit, delButtonWidth, delButtonPadding)
        }
    }
}

@Composable
private fun DeleteUserButton(
    nameList: SnapshotStateList<PlayerName>,
    position: Int,
    exit: (() -> Unit) -> Unit,
    width: Dp,
    padding: Dp
) {
    Icon(
        imageVector = Icons.Rounded.Delete,
        contentDescription = stringResource(id = R.string.add),
        modifier = Modifier
            .padding(padding)
            .width(width)
            .clickable {
                val nameToRemoveId = nameList[position].id
                exit {
                    val nameToRemovePosition =
                        nameList.indexOfFirst { it.id == nameToRemoveId }
                    if (nameToRemovePosition != -1) nameList.removeAt(nameToRemovePosition)
                }
            }
    )
}