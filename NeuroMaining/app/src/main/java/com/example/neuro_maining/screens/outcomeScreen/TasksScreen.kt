package com.example.neuro_maining.screens.outcomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuro_maining.domain.miningTasks
import com.example.neuro_maining.ui.theme.PrimaryColor
import com.example.neuro_maining.ui.theme.SecondaryBackgroundColor

@Composable
fun OutcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        Column ( modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
        {
            TopBar("Tasks library")
            SearchingWidget()
            ClosedTasksWidget(miningTasks.filter { it.isClosed })
            TopEarningsWidget(miningTasks.sortedBy { it.incomePerHour })
        }
    }
}

@Composable
fun TopBar( screenTitle:String){
    Box(
        modifier = Modifier
            .background(PrimaryColor)
            .height(100.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = screenTitle ,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

