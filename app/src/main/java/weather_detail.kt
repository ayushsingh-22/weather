

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather_app.KeyValue
import com.example.weather_app.api.WeatherModel

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