package com.example.neuro_maining.screens.outcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuro_maining.R
import com.example.neuro_maining.data.MiningTask

@Composable
fun TopEarningsWidget(miningTasks: List<MiningTask>) {
    val isExpanded = remember{ mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(30.dp)
        .shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            clip = false
        )
        .border(
            width = 1.dp,
            color = Color.Black,
            shape = RoundedCornerShape(12.dp)
        )
        .background(
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
        .height(
            if(!isExpanded.value)160.dp
            else (100+ ((miningTasks.size+1)*50)).dp
        )
        .fillMaxWidth()
        .padding(20.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Top earnings", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Image(
                    painter = painterResource(id = R.drawable.ic_expand),
                    contentDescription = "Expand icon",
                    modifier = Modifier
                        .size(25.dp)
                        .graphicsLayer(
                            rotationZ = if(!isExpanded.value) 270f
                            else 90f
                        )
                        .clickable {
                            isExpanded.value = !isExpanded.value
                        }
                )
            }
            TopEarningsList(isExpanded, miningTasks)
        }
    }
}

@Composable
fun TopEarningsList(isExpanded: MutableState<Boolean>, miningTasks: List<MiningTask>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(if(isExpanded.value) miningTasks.size else 6) { index ->
            TopEarningsItem(miningTasks[index])
        }
    }
}

@Composable
fun TopEarningsItem(historyItem: MiningTask){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.LightGray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = historyItem.miningSource,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "${historyItem.incomePerHour} P/H",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.ExtraLight
                )
            )
        }
    }
}