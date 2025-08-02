package com.example.neuroMaining.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neuroMaining.screens.earningsScreen.EarningScreen
import com.example.neuroMaining.screens.homeScreen.HomeScreen
import com.example.neuroMaining.screens.outcomeScreen.OutcomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.HOME_TAB.route) {
        composable(NavigationRoute.EARNINGS_TAB.route) {
            EarningScreen()
        }
        composable(NavigationRoute.HOME_TAB.route) {
            HomeScreen()
        }
        composable(NavigationRoute.TASKS_TAB.route) {
            OutcomeScreen()
        }
    }
}
