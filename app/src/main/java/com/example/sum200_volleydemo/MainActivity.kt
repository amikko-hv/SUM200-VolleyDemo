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

/**
 * Activity for demonstrating making Get-requests with Volley.
 * The activity requests data from the JSONPlaceholder API.
 *
 * See: https://jsonplaceholder.typicode.com/
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a Volley request queue. We create it in onCreate for simplicity.
        // A singleton pattern for the queue is recommended by the documentation.
        // See: https://google.github.io/volley/requestqueue.html
        val queue = Volley.newRequestQueue(application)

        enableEdgeToEdge()
        setContent {
            // Declare mutable state for a Post.
            // Will store data retrieved from the API .
            var post by remember { mutableStateOf<Post?>(null) }

            // Lambda expression for handling send request button click
            val onSendRequestClick: () -> Unit = {
                // Create url for a random post
                val randomPostId = (1..100).random()
                val url = "https://jsonplaceholder.typicode.com/posts/$randomPostId"

                // Construct the Volley json request
                val jsonRequest = JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    { response ->
                        val userId = response.getInt("userId")
                        val title = response.getString("title")
                        val body = response.getString("body")

                        // Assign the result to our mutable Post state. UI will be updated.
                        post = Post(userId, title, body)
                    },
                    { error ->
                        Log.e("VolleyError", error.toString())
                    }
                )

                // Add request to the queue
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
                        // Button for sending requests.
                        Button(
                            onClick = onSendRequestClick,
                            Modifier.padding(all = 16.dp)
                        ) {
                            Text("Send Request")
                        }

                        // Capture state of the current post in a when expression.
                        // Display a PostCard if data is available.
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
