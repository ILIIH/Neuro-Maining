package com.example.neuro_maining.screens.outcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.neuro_maining.data.getEarningSum
import kotlin.math.round

@Composable
fun ClosedTasksWidget(miningHistory: List<MiningTask>) {
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
            else (100+ ((miningHistory.size+1)*50)).dp
        )
        .fillMaxWidth()
        .padding(20.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Closed tasks", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "223.8P", fontWeight = FontWeight.Light, fontSize = 10.sp)
                }
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
            ClosedTasksList(isExpanded, miningHistory)

        }
    }
}

@Composable
fun ClosedTasksList(isExpanded: MutableState<Boolean>, miningHistory: List<MiningTask>){
    LazyColumn (modifier = Modifier.padding(top = 20.dp)){
        items(
            count = if(isExpanded.value) miningHistory.size else 1,
            itemContent = { index ->
                ClosedTasksItem(miningHistory[index])
            }
        )
    }
}

@Composable
fun ClosedTasksItem(historyItem: MiningTask){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.LightGray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Column {
                Text(
                    text = historyItem.miningSource,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "12 Jun 2028",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.ExtraLight
                    )
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "${round(historyItem.getEarningSum() * historyItem.earningMultiplier)}P",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}