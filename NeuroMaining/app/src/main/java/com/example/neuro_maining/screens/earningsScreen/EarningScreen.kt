package com.example.neuroMaining.screens.earningsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.neuroMaining.domain.miningTasks
import com.example.neuroMaining.ui.customView.PlotView
import com.example.neuroMaining.ui.theme.PrimaryColor
import com.example.neuroMaining.ui.theme.SecondaryBackgroundColor

@Composable
fun EarningScreen() {
    Box(modifier = Modifier.fillMaxSize().background(SecondaryBackgroundColor)) {
        Column {
            Box(modifier = Modifier.background(PrimaryColor)) {
                PlotView(
                    miningTasks,
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth(1f)
                        .padding(top = 30.dp, bottom = 60.dp)
                        .padding(horizontal = 40.dp)
                )
            }

            EarningsList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(horizontal = 40.dp)
            )
        }
    }
}
