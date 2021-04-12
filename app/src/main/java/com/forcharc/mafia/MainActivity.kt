package com.forcharc.mafia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forcharc.mafia.ui.anim.EnterExitAnimation
import com.forcharc.mafia.ui.screens.EnterPlayersScreen
import com.forcharc.mafia.ui.screens.GetRolesScreen
import com.forcharc.mafia.ui.screens.WelcomeScreen
import com.forcharc.mafia.ui.theme.MafiaTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MafiaTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column() {
                        val navController = rememberNavController()
                        Scaffold(
                            topBar = { TopAppBar(title = { Text(text = "Privet") }) }
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = NavDests.welcomeDestination
                            ) {
                                composable(NavDests.welcomeDestination) {
                                    EnterExitAnimation { exit ->
                                        WelcomeScreen(navController, exit)
                                    }
                                }
                                composable(NavDests.enterPlayersDestination) {
                                    EnterExitAnimation(exitTransition = fadeOut()) { exit ->
                                        EnterPlayersScreen(navController, exit)
                                    }
                                }
                                composable(NavDests.getRolesDestination) {
                                    EnterExitAnimation() { exit ->
                                        GetRolesScreen(navController, exit)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@ExperimentalAnimationApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DefaultPreview() {
    WelcomeScreen(navController = null) {}
}




