package com.example.neuro_maining.navigation

import android.graphics.Path
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.neuro_maining.R
import com.example.neuro_maining.ui.theme.PrimaryColor
import com.example.neuro_maining.ui.theme.SecondaryColor
import com.example.neuro_maining.ui.theme.SecondaryNotSelectedColor

@Composable
fun BottomNavigationBar (navController: NavHostController){

    val selectedTab = remember { mutableStateOf(NavigationRoute.EARNINGS_TAB) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(PrimaryColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(10.dp).clickable {
                    selectedTab.value = NavigationRoute.EARNINGS_TAB
                    navController.navigate(NavigationRoute.EARNINGS_TAB.route)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = if(selectedTab.value == NavigationRoute.EARNINGS_TAB) R.drawable.ic_earnings_selected_tab
                        else R.drawable.ic_earnings_not_selected_tab),
                    contentDescription = "Earnings tab",
                    modifier = Modifier.size(25.dp)
                )
                Text(text = "Earnings", color =  if(selectedTab.value == NavigationRoute.EARNINGS_TAB) SecondaryColor else SecondaryNotSelectedColor)
            }
            Column(
                modifier = Modifier.weight(1f)
                    .clickable {
                        selectedTab.value = NavigationRoute.HOME_TAB
                        navController.navigate(NavigationRoute.HOME_TAB.route)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id =  if(selectedTab.value == NavigationRoute.HOME_TAB)  R.drawable.ic_home_selected_tab
                        else R.drawable.ic_home_not_selected_tab),
                    contentDescription = "Home tab",
                    modifier = Modifier.size(25.dp)
                )
                Text(text = "Home", color = if(selectedTab.value == NavigationRoute.HOME_TAB) SecondaryColor else SecondaryNotSelectedColor)
            }
            Column(
                modifier = Modifier.weight(1f)
                    .clickable {
                        selectedTab.value = NavigationRoute.OUTCOME_TAB
                        navController.navigate(NavigationRoute.OUTCOME_TAB.route)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = if(selectedTab.value == NavigationRoute.OUTCOME_TAB) R.drawable.ic_outcome_selected_tab
                        else R.drawable.ic_outcome_not_selected_tab),
                    contentDescription = "Outcome tab",
                    modifier = Modifier.size(25.dp)
                )
                Text(text = "Outcome", color = if(selectedTab.value == NavigationRoute.OUTCOME_TAB) SecondaryColor else SecondaryNotSelectedColor)
            }
        }
    }
}