package com.example.neuroMaining.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.neuroMaining.ui.theme.SecondaryBackgroundColor

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryBackgroundColor)
    ) {
        Column {
            HomeTopbar()
            TransactionsList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(horizontal = 40.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF101010)
fun HomeScreenPreview() {
    HomeScreen()
}
