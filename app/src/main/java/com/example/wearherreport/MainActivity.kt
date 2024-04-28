package com.example.wearherreport

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.wearherreport.screens.MainCard
import com.example.wearherreport.screens.TabLayout


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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

private fun getData(city: String, context:Context){

}


