package com.example.neuro_maining.ui.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuro_maining.miningHistory
import com.example.neuro_maining.navigation.NavigationRoute
import com.example.neuro_maining.ui.custom_view.PlotView
import com.example.neuro_maining.ui.theme.PrimaryColor
import com.example.neuro_maining.ui.theme.SecondaryBackgroundColor

@Composable
fun HomeScreen() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(SecondaryBackgroundColor)) {
        Column {
            Box(
                modifier = Modifier
                    .background(PrimaryColor)
                    .height(300.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Main - GBP",
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "£ 337.03",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 40.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(PrimaryColor)
                    .height(100.dp)
                    .fillMaxWidth(),
            ){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Withdraw",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Add",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Transfer",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Info",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

