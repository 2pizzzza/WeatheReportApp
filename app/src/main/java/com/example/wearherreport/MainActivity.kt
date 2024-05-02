package com.example.wearherreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.wearherreport.response.WeatherModel
import com.example.wearherreport.screens.DialogSearch
import com.example.wearherreport.screens.MainCard
import com.example.wearherreport.screens.TabLayout
import com.example.wearherreport.utils.getData

const val API_KEY = "d057081383a04a0eb0c133440242804"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val daysList = remember {
                mutableStateOf(listOf<WeatherModel>())
            }

            val dialogState = remember {
                mutableStateOf(false)
            }

            val city = remember {
                mutableStateOf("Karakol")
            }

            val currentDay = remember {
                mutableStateOf(
                    WeatherModel(
                        "",
                        "",
                        "0.0",
                        "",
                        "",
                        "0.0",
                        "0.0",
                        ""
                    )
                )
            }

            if(dialogState.value){
                DialogSearch(dialogState,  onSubmit = {
                    getData(it, daysList, currentDay, this)
                })
            }
            getData(city.value, daysList, currentDay, this)
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.main_phone),
                contentDescription = "img"
            )
            Column {
                MainCard(currentDay, onClickSync = {
                    getData(city.value, daysList, currentDay, this@MainActivity)
                },
                    onClickSearch = {
                        dialogState.value = true
                    }
                )
                TabLayout(daysList, currentDay)
            }

        }
    }
}

