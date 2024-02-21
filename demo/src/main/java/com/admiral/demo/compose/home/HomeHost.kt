package com.admiral.demo.compose.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
internal fun HomeHost() {
    val homeController = rememberNavController()

    NavHost(
        navController = homeController,
        startDestination = HOME_GRAPH_ROUTE,
    ) {
        homeGraph(
            navController = homeController,
        )
    }
}
