package com.example.weather_app

import android.media.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather_app.api.NetrworkResponse
import com.example.weather_app.api.WeatherModel

@Composable
fun Design(viewmodel: Weatherviewmodel){

    var city by remember {
        mutableStateOf("")
    }

    val WeatherResult = viewmodel.weatherResult.observeAsState()
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp), ) {

            OutlinedTextField(modifier = Modifier.weight(1f),
                value = city,
                singleLine = true,
                maxLines = 1,
                onValueChange = {city = it},

            label = { Text(text = "Enter your city")},
                )

            Spacer(modifier = Modifier.padding(8.dp))

            IconButton(onClick = {
                focusManager.clearFocus()
                viewmodel.getData(city)
            }) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription ="Search for location",
                    Modifier.size(50.dp))
            }
        }

        when(val result = WeatherResult.value){
            is NetrworkResponse.Error ->{
                Text(text = result.message)
            }
            is NetrworkResponse.Success -> {
                WeatherDetail(data = result.data)
            }
            NetrworkResponse.loading ->{
                CircularProgressIndicator()
            }
            null -> {}
        }

    }
}

@Composable
fun WeatherDetail(data: WeatherModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = data.location.name + ",", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = data.location.country, fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${data.current.temp_c}°C",
            fontSize = if (screenWidth < 600.dp) 60.sp else 80.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = "http:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Condition Icon",
            modifier = Modifier.size(if (screenWidth < 600.dp) 120.dp else 160.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = data.current.condition.text,
            fontSize = if (screenWidth < 600.dp) 16.sp else 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    KeyValue("Wind Speed", data.current.wind_kph.toString())
                    KeyValue("Humidity", data.current.humidity.toString())
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    KeyValue("UV Index", data.current.uv.toString())
                    KeyValue("Precipitation", data.current.precip_mm.toString())
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    KeyValue("Local Time", data.location.localtime.split(" ")[1])
                    KeyValue("Local Date", data.location.localtime.split(" ")[0])
                }
            }
        }
    }
}

@Composable
fun KeyValue(key: String, value: String){

    Column(Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(text = key,fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }

}