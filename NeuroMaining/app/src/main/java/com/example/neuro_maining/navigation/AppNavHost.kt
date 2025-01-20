package com.example.neuro_maining.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neuro_maining.ui.earnings_screen.EarningScreen
import com.example.neuro_maining.ui.home_screen.HomeScreen
import com.example.neuro_maining.ui.outcome_screen.OutcomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.EARNINGS_TAB.route) {
        composable(NavigationRoute.EARNINGS_TAB.route) {
            EarningScreen()
        }
        composable(NavigationRoute.HOME_TAB.route) {
            HomeScreen()
        }
        composable(NavigationRoute.OUTCOME_TAB.route) {
            OutcomeScreen()
        }
    }
}