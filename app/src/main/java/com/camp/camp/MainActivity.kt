package com.camp.camp

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.camp.camp.ui.theme.Camp2024Theme
import com.camp.camp.ui.theme.PinkIoasys
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://keyclock.cluster-dev.ioasys.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val service = retrofit.create(LoginService::class.java)
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Camp2024Theme {

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(onLoginClick = { email, password ->
                            val call = service.login(
                                "password",
                                "camp-ioasys-2024",
                                "BiKzHxr9ZoZRDlLjx6qG7QfnDhIoQdIf",
                                email,
                                password,
                                "email"
                            )
                            call.enqueue(object : Callback<LoginResponse> {
                                override fun onResponse(
                                    p0: Call<LoginResponse>,
                                    p1: Response<LoginResponse>
                                ) {
                                    navController.navigate("tasklist")
                                }

                                override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {

                                }
                            })
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Cyan)
            .padding(10.dp),
        text = "Hello $name!",
        color = PinkIoasys
    )
}