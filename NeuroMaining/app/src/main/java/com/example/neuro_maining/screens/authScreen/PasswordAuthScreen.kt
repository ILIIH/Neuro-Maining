package com.example.neuroMaining.screens.authScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuroMaining.R
import com.example.neuroMaining.ui.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun PasswordAuthScreen(returnToBiometric: () -> Unit, validatePasscode: (passcode: String) -> Unit) {
    var input by remember { mutableStateOf("") }
    // Dial pad
    val numbers = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("biometry", "0", "⌫")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Enter passcode :",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                color = PrimaryColor
            )
        }
        PasscodeText(input, validatePasscode)
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            numbers.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "⌫" -> if (input.isNotEmpty()) input = input.dropLast(1)
                                    "biometry" -> returnToBiometric()
                                    else -> { if (input.length < 4) input += label }
                                }
                            },
                            modifier = Modifier
                                .size(80.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryColor
                            )
                        ) {
                            if (label == "biometry") {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_fingerprint_white),
                                    contentDescription = "Biometry auth icon",
                                    modifier = Modifier.size(25.dp)
                                )
                            } else {
                                Text(text = label, fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PasscodeText(passCode: String, validatePasscode: (passcode: String) -> Unit) {
    if (passCode.length == 4) {
        validatePasscode(passCode)
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (codeDigit in passCode) {
            PasscodeDigit(codeDigit)
        }
    }
}

@Composable
fun PasscodeDigit(digit: Char) {
    var displayText by remember { mutableStateOf(digit.toString()) }

    LaunchedEffect(Unit) {
        delay(2_000)
        displayText = "*"
    }

    Text(text = displayText, fontWeight = FontWeight.Bold, fontSize = 40.sp)
}
