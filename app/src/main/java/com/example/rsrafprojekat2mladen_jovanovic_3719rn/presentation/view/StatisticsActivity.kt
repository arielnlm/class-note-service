package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.activities.MainActivity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.ui.theme.Rsrafprojekat2mladen_jovanovic_3719rnTheme
import timber.log.Timber

class StatisticsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val daysArray = intent.getIntArrayExtra("days")!!
        val maximum = daysArray.maxOrNull() ?: 1

        val normalizedDays = listOf(
            daysArray[0].toFloat() / maximum,
            daysArray[1].toFloat() / maximum,
            daysArray[2].toFloat() / maximum,
            daysArray[3].toFloat() / maximum,
            daysArray[4].toFloat() / maximum
        )
        Timber.e("Days " + daysArray[0] + " " + daysArray[1] + " " + daysArray[2] + " " + daysArray[3] + " " + daysArray[4] + " ")
        setContent {
            Rsrafprojekat2mladen_jovanovic_3719rnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    statisticsScreen(normalizedDays, this)
                }
            }
        }
    }
}

@Composable
fun statisticsScreen(days: List<Float>, context: ComponentActivity) {
   Box(modifier = Modifier
       .fillMaxSize()
       .background(Color.White), contentAlignment = Alignment.Center){
           Box(modifier = Modifier
               .width(50.dp)
               .height(20.dp)
               .align(Alignment.TopStart)
               ){
               Button(
                   onClick = {
                    context.finish()
                   },
                   shape = RoundedCornerShape(50.dp),
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(160.dp)
                       .width(160.dp)
                       , contentPadding = PaddingValues(0.dp)

               ) {
                   Text(text = "Back", fontSize = 12.sp)
               }
           }
           Row(modifier = Modifier
               .fillMaxSize()
               .fillMaxWidth()
               , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
               for (item: Float in days){
                   Rectangle(color = Color.Red, normalizedValue = item)
               }
           }
       //}

   }

}
@Composable
fun Rectangle(color: Color,
              normalizedValue: Float) {
    if(normalizedValue == 0f){
        Box(modifier = Modifier
            .padding(0.dp, 0.dp, 2.dp, 0.dp)
            .height( 100.dp * .05f)
            .offset(x = 0.dp, y = ( 100 * (1f - .025f)).dp)
            .width(30.dp)
            .background(color)
        ) {

        }
    }
    else{
        Box(modifier = Modifier
            .padding(0.dp, 0.dp, 2.dp, 0.dp)
            .height( 100.dp * normalizedValue)
            .offset(x = 0.dp, y = ( 100 * (1f - normalizedValue/2)).dp)
            .width(30.dp)
            .background(color)
        ) {

        }
    }




}
@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Rsrafprojekat2mladen_jovanovic_3719rnTheme {
        //statisticsScreen(listOf(1f,.2f,.4f,.5f,.7f,.2f), )
    }
}