package com.example.wearherreport.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wearherreport.R

@Preview(showSystemUi = true)
@Composable
fun MainScreen(){
    Image(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.9f),
        contentScale = ContentScale.FillBounds,
        painter = painterResource(id =  R.drawable.main_phone),
        contentDescription = "img"
    )
}