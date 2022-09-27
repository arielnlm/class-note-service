package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.activities.MainActivity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.ui.theme.Purple700
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.ui.theme.Rsrafprojekat2mladen_jovanovic_3719rnTheme
import timber.log.Timber

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        //pref.edit().clear().commit();
        val username = pref.getString("username", "null") ?: "null"
        val password = pref.getString("password", "null") ?: "null"

        if(username != "null" && password != "null"){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        setContent {
            Rsrafprojekat2mladen_jovanovic_3719rnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    loginScreen()
                }
            }
        }

    }

}

@Composable
fun loginScreen(){

    val context = LocalContext.current

    val sharedPref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    Box(modifier = Modifier.fillMaxSize()) {
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Monospace))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Username") },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if(username.value.text.isBlank() || password.value.text.isBlank()){
                        Toast.makeText(context, "Please fill every field", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        sharedPref
                            .edit()
                            .putString("username", username.toString())
                            .putString("password", password.toString())
                            .apply()
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as Activity).finish()
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Rsrafprojekat2mladen_jovanovic_3719rnTheme {
        loginScreen()
    }
}