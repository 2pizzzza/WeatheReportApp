package com.example.wearherreport.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wearherreport.response.WeatherModel

@Composable
fun ListItem(item: WeatherModel, currentDay: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .clickable {
                if (item.hours.isEmpty()) return@clickable

                currentDay.value = item

            }
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = item.time,
                    modifier = Modifier.padding(bottom = 3.dp),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Text(text = item.condition)
            }
            Text(
                text = item.currentTemp.ifEmpty {
                    "${item.minTemp}°/${item.maxTemp}°"
                },
                modifier = Modifier,
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = "https:${item.icon}",
                contentDescription = "image",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 5.dp)
            )
        }
    }
}

@Composable
fun MainList(list: List<WeatherModel>, currentDay: MutableState<WeatherModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            list
        ) { _, item ->
            ListItem(item = item, currentDay)
        }
    }
}

@Composable
fun DialogSearch(
    dialogState: MutableState<Boolean>,
    onSubmit: (String) -> Unit
) {

    val dialogText = remember {
        mutableStateOf("Karakol")
    }

    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(dialogText.value)
                dialogState.value = false
            }) {
                Text(text = "Ok")
            }
        },

        dismissButton = {
            TextButton(onClick = {
                dialogState.value = false
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Input name city:")
                OutlinedTextField(
                    value = dialogText.value,
                    onValueChange = { dialogText.value = it })
            }
        }
    )
}