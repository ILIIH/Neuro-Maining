package com.example.neuro_maining.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neuro_maining.screens.earningsScreen.EarningScreen
import com.example.neuro_maining.screens.homeScreen.HomeScreen
import com.example.neuro_maining.screens.outcomeScreen.OutcomeScreen

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
