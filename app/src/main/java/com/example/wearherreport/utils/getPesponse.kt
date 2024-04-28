package com.example.wearherreport.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.wearherreport.API_KEY
import com.example.wearherreport.response.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

fun getData(
    city: String,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>,
    context: Context
) {
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
            val list = getWeatherByDay(response)
            daysList.value = list

            currentDay.value = list[0]
        },
        {
            Log.d("errorVolley", "$it")
        }
    )

    queue.add(sRequest)
}

fun getWeatherByDay(response: String): List<WeatherModel> {
    if (response.isEmpty()) {
        return listOf()
    }
    val mainObj = JSONObject(response)

    val tempList = ArrayList<WeatherModel>()

    val city = mainObj.getJSONObject("location").getString("name")


    val days = mainObj.getJSONObject("forecast").getJSONArray("forecastday")


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
                item.getJSONArray("hour").toString()
            )
        )
    }


    tempList[0] = tempList[0].copy(
        time = mainObj.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObj.getJSONObject("current").getString("temp_c")
    )

    return tempList
}


fun getWeatherByHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) {
        return listOf()
    }

    val hoursArray = JSONArray(hours)

    val tempList = ArrayList<WeatherModel>()

    for (i in 0 until hoursArray.length()) {
        val item = hoursArray[i] as JSONObject

        tempList.add(
            WeatherModel(
                "",
                item.getString("time"),
                item.getString("temp_c").toFloat().toInt().toString() + "°",
                item.getJSONObject("condition").getString("text"),
                item.getJSONObject("condition").getString("icon"),
                "",
                "",
                ""
            )
        )
    }
    return tempList
}