package com.example.sum200_volleydemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.sum200_volleydemo.ui.theme.SUM200VolleyDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var queue: RequestQueue
    private val url = "https://jsonplaceholder.typicode.com/posts/1"

    private val stringRequest = JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        { response ->
            val userId = response.getInt("userId")
            val title = response.getString("title")
            val body = response.getString("body")
            Log.v("VolleyResponse", userId.toString())
            Log.v("VolleyResponse", title)
            Log.v("VolleyResponse", body)
        },
        { error ->
            Log.e("VolleyError", error.toString())
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(this)

        enableEdgeToEdge()
        setContent {
            SUM200VolleyDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(
                            name = "Android",
                        )
                        Button(
                            onClick = { queue.add(stringRequest) }
                        ) {
                            Text("Send Request")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SUM200VolleyDemoTheme {
        Greeting("Android")
    }
}