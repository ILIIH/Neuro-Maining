package com.example.neuroMaining.screens.authScreen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuroMaining.ui.theme.PrimaryColor

@Composable
fun RegisterAccountScreen(registerPasscode: (String) -> Unit) {
    var input by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("Register passcode:") }
    var firstPasscode by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val dialPad = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "⌫")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TitleText(title)

        PasscodeText(input) { enteredPasscode ->
            if (firstPasscode == null) {
                firstPasscode = enteredPasscode
                title = "Repeat passcode:"
                input = ""
            } else if (firstPasscode == enteredPasscode) {
                registerPasscode(enteredPasscode)
            } else {
                Toast.makeText(context, "Passcodes do not match", Toast.LENGTH_SHORT).show()
                firstPasscode = null
                input = ""
                title = "Enter passcode:"
            }
        }

        DialPad(
            numbers = dialPad,
            input = input,
            onInputChange = { input = it }
        )
    }
}

@Composable
fun TitleText(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            color = PrimaryColor
        )
    }
}

@Composable
fun DialPad(
    numbers: List<List<String>>,
    input: String,
    onInputChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
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
                                "⌫" -> if (input.isNotEmpty()) onInputChange(input.dropLast(1))
                                else -> if (input.length < 4 && label.all { it.isDigit() }) {
                                    onInputChange(input + label)
                                }
                            }
                        },
                        modifier = Modifier.size(80.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                    ) {
                        Text(text = label, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}
