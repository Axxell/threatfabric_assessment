package com.theatfabric.wordsperminute.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.theatfabric.wordsperminute.feature.gamescreen.GameScreen
import com.theatfabric.wordsperminute.feature.initialscreen.InitialScreen

@Composable
fun ApplicationNavHost(
    navController: NavHostController
) {
    NavHost(navController, startDestination = StartScreenRoute) {
        composable<StartScreenRoute> {
            InitialScreen(
                onNavigateToGameScreen = { userName, gameId ->
                    navController.navigate(
                        GameScreenRoute(
                            userName = userName,
                            gameId = gameId
                        )
                    )
                }
            )
        }
        composable<GameScreenRoute> {
            GameScreen()
        }
    }
}