package com.example.wearherreport

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.wearherreport.response.WeatherModel
import com.example.wearherreport.screens.MainCard
import com.example.wearherreport.screens.TabLayout
import org.json.JSONObject

const val API_KEY = "d057081383a04a0eb0c133440242804"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val daysList = remember {
                mutableStateOf(listOf<WeatherModel>())
            }
            getData("London", daysList, this)
            Log.d("my log", daysList.value.toString())
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
                TabLayout(daysList)
            }

        }
    }
}

private fun getData(city: String, daysList: MutableState<List<WeatherModel>> , context: Context) {
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
        { response ->
            Log.d("my log", "$response")
            val list = getWeatherByDay(response)
            daysList.value = list

        },
        {
            Log.d("errorVolley", "$it")
        }
    )

    queue.add(sRequest)
}

private fun getWeatherByDay(response: String): List<WeatherModel> {
    Log.d("my log", "d $response")
    if (response.isEmpty()) {
        return listOf()
    }
    val mainObj = JSONObject(response)

    val tempList = ArrayList<WeatherModel>()

    val city = mainObj.getJSONObject("location").getString("name")
    Log.d("my log", "d $city")


    val days = mainObj.getJSONObject("forecast").getJSONArray("forecastday")
    Log.d("my log", "d $days")
    Log.d("my log", "temp list before $tempList")


    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject

        tempList.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                ""
            )
        )
    }

    Log.d("my log", "temp list after v1 $tempList")

    tempList[0] = tempList[0].copy(
        time = mainObj.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObj.getJSONObject("current").getString("temp_c")
    )
    Log.d("my log", "temp list after v2 $tempList")

    return tempList
}