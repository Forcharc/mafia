package com.forcharc.mafia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.forcharc.mafia.ui.theme.MafiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MafiaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavDests.welcomeDestination
                    ) {
                        composable(NavDests.welcomeDestination) {
                            WelcomeScreen(navController)
                        }
                        composable(NavDests.enterPlayersDestination) {
                            EnterPlayersScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.welcome_to_the_mafia),
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController?.navigate(NavDests.enterPlayersDestination)
        }) {
            Text(text = stringResource(R.string.get_roles))
        }
    }
}

@Composable
fun EnterPlayersScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.enter_players),
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { }) {
            Text(text = stringResource(R.string.proceed))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MafiaTheme {
        WelcomeScreen(null)
    }
}