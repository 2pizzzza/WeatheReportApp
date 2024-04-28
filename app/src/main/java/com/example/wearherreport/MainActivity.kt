package com.example.wearherreport

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.wearherreport.screens.MainCard
import com.example.wearherreport.screens.TabLayout

const val API_KEY = "d057081383a04a0eb0c133440242804"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            getData("Karakol", this)
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.main_phone),
                contentDescription = "img"
            )
            Column {
                MainCard()
                TabLayout()
            }

        }
    }
}

private fun getData(city: String, context: Context) {
    val url = "https://api.weatherapi.com/v1/forecast.json?" +
            "key=$API_KEY&" +
            "q=$city&" +
            "days=3&" +
            "aqi=no&" +
            "alerts=no"

    val queue = Volley.newRequestQueue(context)

    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            Log.d("errorVolley", response)

        },
        {
            Log.d("errorVolley", "$it")
        }
    )

    queue.add(sRequest)
}


