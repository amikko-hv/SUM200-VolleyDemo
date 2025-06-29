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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.sum200_volleydemo.composable.PostCard
import com.example.sum200_volleydemo.data.Post
import com.example.sum200_volleydemo.ui.theme.SUM200VolleyDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val queue = Volley.newRequestQueue(this)

        enableEdgeToEdge()
        setContent {
            var post by remember { mutableStateOf<Post?>(null) }

            val onSendRequest: () -> Unit = {
                val randomPostId = (1..100).random()

                val url = "https://jsonplaceholder.typicode.com/posts/$randomPostId"

                val jsonRequest = JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    { response ->
                        val userId = response.getInt("userId")
                        val title = response.getString("title")
                        val body = response.getString("body")

                        post = Post(userId, title, body)
                    },
                    { error ->
                        Log.e("VolleyError", error.toString())
                    }
                )

                queue.add(jsonRequest)
            }

            SUM200VolleyDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = onSendRequest,
                            Modifier.padding(all = 16.dp)
                        ) {
                            Text("Send Request")
                        }
                        when (val postState = post) {
                            null -> Text("Click the button!")
                            else -> PostCard(postState)
                        }
                    }
                }
            }
        }
    }
}
