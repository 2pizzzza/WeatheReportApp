package com.example.wearherreport

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

val API_KEY = "3626d7d560134f48935185758242404"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting(this)
        }
    }
}

@Composable
fun Greeting(context: Context) {
    val state = remember {
        mutableStateOf("Unknown")
    }

    var city = remember {
        mutableStateOf("Karakol")
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
            Column {
                Text(text = "Temp in ${city.value} \n ${state.value} C")
                OutlinedTextField(
                    value = city.value, onValueChange = { city.value = it }
                )
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    getResult(city.value, state, context)
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
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("current").getString("temp_c")
        },
        { error ->
            Log.d("error ", "$error")
        }
    )

    queue.add(stringRequest)
}
