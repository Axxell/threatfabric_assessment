package com.theatfabric.wordsperminute.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute

@Serializable
data object StartScreenRoute : NavigationRoute()

@Serializable
data class GameScreenRoute(
    val userName: String,
    val gameId: String
) : NavigationRoute()