package com.example.neuro_maining.ui.earnings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neuro_maining.EARNING_MULTIPLIER
import com.example.neuro_maining.data.MiningHistory
import com.example.neuro_maining.data.util.getEarningSum
import com.example.neuro_maining.miningHistory
import com.example.neuro_maining.ui.custom_view.PlotView
import com.example.neuro_maining.ui.theme.PrimaryColor
import com.example.neuro_maining.ui.theme.SecondaryBackgroundColor

@Composable
fun EarningScreen() {
    Box(modifier = Modifier.fillMaxSize().background(SecondaryBackgroundColor)) {
        Column {
            Box (modifier = Modifier.background(PrimaryColor)){
                PlotView(
                    miningHistory,
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth(1f)
                        .padding(top = 30.dp, bottom = 60.dp)
                        .padding(horizontal = 40.dp)
                )
            }

            ListOfEarnings(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(horizontal = 40.dp)
            )
        }
    }
}

