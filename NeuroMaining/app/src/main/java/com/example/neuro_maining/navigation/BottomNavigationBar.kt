package com.example.neuro_maining.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.neuro_maining.R
import com.example.neuro_maining.ui.theme.PrimaryColor
import com.example.neuro_maining.ui.theme.SecondaryColor

@Composable
fun BottomNavigationBar (navController: NavHostController){
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
                modifier = Modifier.weight(1f).background(PrimaryColor).padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_earnings_tab),
                    contentDescription = "Earnings tab",
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Earnings", color = SecondaryColor)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_home_tab),
                    contentDescription = "Home tab",
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Home", color = SecondaryColor)
            }
            Column(
                modifier = Modifier.weight(1f), // Ensures equal width for each Column
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outcome_tab),
                    contentDescription = "Outcome tab",
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Outcome", color = SecondaryColor)
            }
        }
    }
}