package com.example.wearherreport.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wearherreport.R
import com.example.wearherreport.response.WeatherModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MainCard() {
    Column(
        modifier = Modifier
            .padding(5.dp, top = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp),
                        text = "20 apr 2024 13:00",
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    )
                    AsyncImage(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 8.dp, end = 10.dp),
                        model = "https://cdn.weatherapi.com/weather/64x64/day/353.png",
                        contentDescription = "image"
                    )
                }
                Text(
                    text = "Karakol",
                    style = TextStyle(fontSize = 30.sp)
                )
                Text(
                    text = "18°С",
                    style = TextStyle(fontSize = 65.sp)
                )
                Text(
                    text = "sunny",
                    style = TextStyle(fontSize = 19.sp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search_image"
                        )
                    }
                    Text(
                        text = "18°C/35°C",
                        modifier = Modifier
                            .padding(top = 14.dp)
                    )
                    IconButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "search_image"
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>) {

    val tabList = listOf("HOURS", "DAYS ")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(35.dp))
            .padding(5.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            },
            modifier = Modifier
                .background(Color.White)
        ) {
            tabList.forEachIndexed { index, s ->
                Tab(selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }, text = {
                        Text(
                            text = s,
                            style = TextStyle(color = Color.Black, fontSize = 15.sp)
                        )
                    }
                )
            }
        }
        com.google.accompanist.pager.HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier
                .weight(1.0f)
        ) { index ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    daysList.value
                ) { _, item ->
                    ListItem(item = item)
                }
            }
        }
    }
}

