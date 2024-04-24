package com.example.wearherreport

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting("Karakol")
        }
    }
}

@Composable
fun Greeting(name: String) {
    val state = remember {
        mutableStateOf("Unknown")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            Text(text = "Temp in $name \n ${state.value}")
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    getResult(name, state,  context = Context)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Refresh")
            }
        }
    }
}


private fun getResult(city: String, state: MutableState<String>, context: Context) {

}

@Preview(showSystemUi = true)
@Composable
fun greetinPreview() {
    Greeting(name = "Karakol")
}