package com.example.neuro_maining.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neuro_maining.ui.earnings_screen.EarningScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "earnings_screen") {
        composable("earnings_screen") {
            EarningScreen()
        }
    }
}