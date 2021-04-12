package com.forcharc.mafia.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.forcharc.mafia.NavDests
import com.forcharc.mafia.R

@ExperimentalAnimationApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WelcomeScreen(
    navController: NavHostController? = null,
    exit: ((onExit: () -> Unit) -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.welcome_to_the_mafia),
            modifier = Modifier.padding(top = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            exit?.invoke {
                navController?.navigate(NavDests.enterPlayersDestination)
            }
        }) {
            Text(text = stringResource(R.string.get_roles))
        }
    }
}
