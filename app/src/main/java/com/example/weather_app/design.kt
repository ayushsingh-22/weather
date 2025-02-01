package com.example.weather_app

import WeatherDetail
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
fun KeyValue(key: String, value: String){

    Column(Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(text = key,fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }

}